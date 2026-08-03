package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.User;

public class UserDaoDB extends DaoDB implements UserDao {

	@Override
	public User getUser(String userId) {
		//データベース接続
		try (Connection connection = super.getConnection()) {
			String sql = "SELECT * FROM users WHERE user_id = ?"; //TODO：ユーザを取得するSQLを書く

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, userId); //ユーザIDをSQLパラメータに設定する
			
			System.out.println(statement);
			

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return new User(resultSet.getString("user_id"),
							resultSet.getString("password"),
							resultSet.getString("user_name")); //TODO：ユーザを生成する
				}
			}
		} catch (Exception e) { //例外発生時の処理
			e.printStackTrace();
		}
		return null;
	}
}
