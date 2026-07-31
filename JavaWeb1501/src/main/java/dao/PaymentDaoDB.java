package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PaymentDaoDB extends DaoDB implements PaymentDao {

@Override
public int insertPayment(
        String userId,
        String userName,
        String productId,
        String productName,
        int quantity,
        int price) {

    try (Connection connection = getConnection()) {

        String sql =
            "INSERT INTO payment "
          + "(user_id, user_name, product_id, product_name, quantity, amount, purchase_date) "
          + "VALUES (?, ?, ?, ?, ?, ?, NOW())";

        PreparedStatement statement =
                connection.prepareStatement(sql);

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

//private Connection getConnection() {
//	// TODO 自動生成されたメソッド・スタブ
//	return null;
//}


}
