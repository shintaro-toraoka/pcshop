package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Payment;

public class PaymentDaoDB extends DaoDB implements PaymentDao {

	public int insertPayment(
			String userId,
			String userName,
			String productId,
			String productName,
			int quantity,
			int price) {
		
		//データベース接続
		try (Connection connection = getConnection()) {
			//paymentテーブルに支払い情報を登録するSQL文
			String sql = "INSERT INTO payment "
					+ "(user_id, user_name, product_id, product_name, quantity, amount, purchase_date) "
					+ "VALUES (?, ?, ?, ?, ?, ?, NOW())";

			PreparedStatement statement = connection.prepareStatement(sql);
			System.out.println(statement);
			
			//SQL文のパラメータを設定
			statement.setString(1, userId);
			statement.setString(2, userName);
			statement.setString(3, productId);
			statement.setString(4, productName);
			statement.setInt(5, quantity);
			statement.setInt(6, price);

			int updateCount = statement.executeUpdate();
			return updateCount;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	//指定ユーザの購入履歴を取得
	@Override
	public List<Payment> getPaymentList(String userId) {
		List<Payment> paymentList = new ArrayList<>();

		String sql = "SELECT * FROM payment "
				+ "WHERE user_id = ? "
				+ "ORDER BY purchase_date DESC";
		//データベース接続
		try (Connection connection = getConnection()) {
			PreparedStatement statement = connection.prepareStatement(sql);
			//SQL文のパラメータを設定
			statement.setString(1, userId);
			
			System.out.println(statement);

			ResultSet paymentResult = statement.executeQuery();
			
			//paymentテーブルから取得した値をpaymentオブジェクトに格納
			while (paymentResult.next()) {
				Payment payment = new Payment();

				payment.setUserId(paymentResult.getString("user_id"));

				payment.setUserName(paymentResult.getString("user_name"));

				payment.setProductId(paymentResult.getString("product_id"));

				payment.setProductName(paymentResult.getString("product_name"));

				payment.setAmount(paymentResult.getInt("amount"));

				payment.setQuantity(paymentResult.getInt("quantity"));

				payment.setPurchaseDate(paymentResult.getTimestamp("purchase_date")
						.toLocalDateTime());
				//paymentオブジェクトを購入履歴リストに追加
				paymentList.add(payment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paymentList;
	}
}
