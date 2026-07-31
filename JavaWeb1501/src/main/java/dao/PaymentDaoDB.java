package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
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

		System.out.println("insertPayment開始");

		try (Connection connection = getConnection()) {

			String sql = "INSERT INTO payment "
					+ "(user_id, user_name, product_id, product_name, quantity, amount, purchase_date) "
					+ "VALUES (?, ?, ?, ?, ?, ?, NOW())";

			System.out.println(sql);

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, userId);
			statement.setString(2, userName);
			statement.setString(3, productId);
			statement.setString(4, productName);
			statement.setInt(5, quantity);
			statement.setInt(6, price);

			int updateCount = statement.executeUpdate();

			System.out.println("updateCount=" + updateCount);
			return updateCount;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public List<Payment> getPaymentList(String userId) {
		List<Payment> paymentList = new ArrayList<>();

		String sql = "SELECT * FROM payment "
				+ "WHERE user_id = ? "
				+ "ORDER BY purchase_date DESC";

		try (Connection connection = getConnection()) {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, userId);

			ResultSet paymentResult = statement.executeQuery();

			while (paymentResult.next()) {
				Payment payment = new Payment();

				payment.setUserId(
						paymentResult.getString("user_id"));

				payment.setUserName(
						paymentResult.getString("user_name"));

				payment.setProductId(
						paymentResult.getString("product_id"));

				payment.setProductName(
						paymentResult.getString("product_name"));

				payment.setAmount(
						paymentResult.getInt("amount"));

				payment.setQuantity(
						paymentResult.getInt("quantity"));

				payment.setPurchaseDate(
						paymentResult.getTimestamp("purchase_date")
								.toLocalDateTime());

				paymentList.add(payment);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return paymentList;

	}

	@Override
	public int insertPayment(String userId, String userName, String productId, String productName, int quantity,
			int price, LocalDateTime purchaseDate) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	//private Connection getConnection() {
	//	// TODO 自動生成されたメソッド・スタブ
	//	return null;
	//}

}
