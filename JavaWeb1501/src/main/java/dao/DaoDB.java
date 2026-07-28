package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DaoDB {

	private static final String CONFIG_FILE = "/db.properties.xml";

	private static final Properties DB_PROPERTIES = loadProperties();

	/**
	 * XMLファイルからDB接続情報を読み込みます。
	 */
	private static Properties loadProperties() {

		Properties properties = new Properties();

		try (InputStream inputStream =DaoDB.class.getResourceAsStream(CONFIG_FILE)) {

			if (inputStream == null) {
				throw new IllegalStateException("DB設定ファイルが見つかりません: " + CONFIG_FILE);
			}

			properties.loadFromXML(inputStream);

			return properties;

		} catch (IOException e) {
			throw new IllegalStateException("DB設定ファイルの読み込みに失敗しました: " + CONFIG_FILE, e);
		}
	}

	/**
	 * DB接続を取得します。
	 */
	protected Connection getConnection()
			throws ClassNotFoundException, SQLException {

		String dbName =
				DB_PROPERTIES.getProperty("db.name");

		String hostname =
				DB_PROPERTIES.getProperty("db.hostname");

		String port =
				DB_PROPERTIES.getProperty("db.port");

		String dbUser =
				DB_PROPERTIES.getProperty("db.user");

		String dbPassword =
				DB_PROPERTIES.getProperty("db.password");

		Class.forName("com.mysql.cj.jdbc.Driver");

		String url = String.format(
				"jdbc:mysql://%s:%s/%s"
						+ "?allowPublicKeyRetrieval=true"
						+ "&useSSL=false",
				hostname,
				port,
				dbName);

		return DriverManager.getConnection(
				url,
				dbUser,
				dbPassword);
	}
}