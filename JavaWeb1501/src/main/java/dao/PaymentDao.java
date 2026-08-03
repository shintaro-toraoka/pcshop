package dao;

import java.util.List;

import model.Payment;

public interface PaymentDao {
	int insertPayment(
			String userId,
			String userName,
			String productId,
			String productName,
			int quantity,
			int price);
	
	//userIdを指定して購入履歴リストを取得
	List<Payment> getPaymentList(String userId);

}
