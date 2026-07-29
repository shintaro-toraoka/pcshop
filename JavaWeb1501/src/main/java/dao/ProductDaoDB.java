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
				
//			}
/// 
 */
			String sql;
			if (keyword != null && !keyword.isEmpty()) {

			    sql = "SELECT * FROM products WHERE product_name LIKE ?";

			} else {

			    sql = "SELECT * FROM products";

			}			
			

			PreparedStatement statement = connection.prepareStatement(sql);
			/*statement.setString(1, product_id); *///ユーザIDをSQLパラメータに設定する

	        if (keyword != null && !keyword.isEmpty()) {

	            statement.setString(1, "%" + keyword + "%");

	        }			
			
	        
	        System.out.println(statement);
	        
			
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
