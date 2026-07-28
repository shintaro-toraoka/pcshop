package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoDB {

	private final String DBNAME = "cscdb";
	private final String HOSTNAME = "localhost";
	private final String PORTNO = "3306";
	private final String DBUSER = "RyanGosling";
	private final String DBPASSWD = "DBcsc26Newbie";
	
	// DB接続処理	
	protected Connection getConnection() throws ClassNotFoundException, SQLException {
		// MySQL JDBCドライバを読み込む	
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = String.format("jdbc:mysql://%s:%s/%s?allowPublicKeyRetrieval=true&useSSL=false", HOSTNAME, PORTNO, DBNAME);
		return DriverManager.getConnection(url, DBUSER, DBPASSWD);
	}
	

}
