package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

public class UserDaoDB implements UserDao {
	private String host;
	private String port;
	private String database;
	private String id;
	private String password;

	public UserDaoDB(String host, String port, String database, String id, String password) {
		super();
		this.host = host;
		this.port = port;
		this.database = database;
		this.id = id;
		this.password = password;
	}

	@Override
	public User getUser(String userId) {
		//データベース接続
		try (Connection connection = getConnection()) {
			String sql = "SELECT * FROM users WHERE id = ?"; //TODO：ユーザを取得するSQLを書く

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, id); //ユーザIDをSQLパラメータに設定する
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return new User(resultSet.getString("id"),
							resultSet.getString("password"),
							resultSet.getString("name")); //TODO：ユーザを生成する
				}
			}
		} catch (Exception e) { //例外発生時の処理
			e.printStackTrace();
		}
		return null;
	}

	// DB接続処理	
	private Connection getConnection() throws ClassNotFoundException, SQLException {
		// MySQL JDBCドライバを読み込む	
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = String.format("jdbc:mysql://%s:%s/%s?allowPublicKeyRetrieval=true&useSSL=false", host, port,
				database);
		return null;
	}

}
