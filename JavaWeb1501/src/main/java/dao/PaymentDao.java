package dao;

public interface PaymentDao {
	int insertPayment(
			String userId,
			String userName,
			String productId,
			String productName,
			int price);

}
