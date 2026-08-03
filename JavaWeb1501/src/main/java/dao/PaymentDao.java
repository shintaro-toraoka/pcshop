package dao;

import java.time.LocalDateTime;
import java.util.List;

import model.Payment;

public interface PaymentDao {
	int insertPayment(
			String userId,
			String userName,
			String productId,
			String productName,
			int quantity,
			int price,
			LocalDateTime purchaseDate,
			String historyId
			);
	
	List<Payment> getPaymentList(String userId);

}
