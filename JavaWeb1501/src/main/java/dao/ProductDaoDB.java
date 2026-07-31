package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Product;

public class ProductDaoDB extends DaoDB implements ProductDao {

	@Override
	public List<Product> getProductList(String keyword) {
		List<Product> productList = new ArrayList<>(); 
		//データベース接続
		try (Connection connection = super.getConnection()) {
//			String sql = "SELECT * FROM products "; //TODO：ユーザを取得するSQLを書く
			/*if () {
				String sql = "SELECT * FROM products WHERE "; //TODO：ユーザを取得するSQLを書く
				
			} else {
				String sql = "SELECT * FROM products "; //TODO：ユーザを取得するSQLを書く
				
			}
 
 */
			StringBuilder sql = new StringBuilder("SELECT * FROM products WHERE category IS NOT NULL");
				
			String[]keywords = null;

				if(keyword != null && !keyword.trim().isEmpty()){
					
					keywords =keyword.trim().split("[\\s　]+");
				 	for(String word : keywords){
				 		sql.append(" AND product_name LIKE ?");
				 		}
				}
				 	PreparedStatement statement = connection.prepareStatement(sql.toString());
				
				 	if(keywords !=null){
				 			for(int i =0; i<keywords.length; i++){
				 			statement.setString( i+1,
				 			"%" + keywords[i] + "%"
				 			);
				 		}
		
				 			
				 		System.out.println(statement);
				 		
				 	}else { System.out.println("該当商品が見つかりませんでした。");
				}
	        
			
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					productList.add(new Product(resultSet.getString("product_id"),
							resultSet.getString("product_name"),
							resultSet.getString("image_path"),
							resultSet.getInt("price"),
							resultSet.getInt("stock")));//TODO：ユーザを生成する
				}
			}
		} catch (Exception e) { //例外発生時の処理
			e.printStackTrace();
		}
		return productList;
		
		}

	//

	public void reduceStock(
			int quantity, String productId) {
		try (Connection connection = getConnection()) {

			String sql = "UPDATE products SET stock = stock - ?  WHERE product_id = ?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, quantity);
			statement.setString(2, productId); //在庫数をSQLパラメータに設定する

			System.out.println(statement);
			statement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
