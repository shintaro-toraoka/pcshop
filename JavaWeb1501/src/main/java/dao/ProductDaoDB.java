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
		
		//カテゴリがNullでない商品を取得する
			StringBuilder sql = new StringBuilder("SELECT * FROM products WHERE category IS NOT NULL");
			
		//検索バーにある単語の判定
			String[]keywords = null;
				
			//nullでない・空文字でない・スペースだけでない
				if(keyword != null && !keyword.trim().isEmpty()){
					
					keywords =keyword.trim().split("[\\s　]+"); //単語を空白で分割する
				 	for(String word : keywords){
				 		sql.append(" AND product_name LIKE ?");//キーワードの数だけ追加する
				 		System.out.println(keywords);
				 		}
				}
					//完成したSQLをセット
				 	PreparedStatement statement = connection.prepareStatement(sql.toString());
				 	
				 	//?に単語をセット　実際に入力した単語をいれていく
				 	if(keywords !=null){
				 			for(int i =0; i<keywords.length; i++){
				 			statement.setString( i+1,
				 			"%" + keywords[i] + "%"
				 			);
				 		}
		
				 			
				 		System.out.println(statement); //SQLでの確認用
				 		
				 	}else { System.out.println("検索キーワードが入力されていません。");
				}
	        
			
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					///TODO：ユーザを生成する
					
					
					productList.add(
						    new Product(
						        resultSet.getString("product_id"),
						        resultSet.getString("product_name"),
						        resultSet.getString("image_path"),
						        resultSet.getInt("price"),
						        resultSet.getInt("stock"),
						        resultSet.getString("calories"),
						        resultSet.getString("nutrients"),
						        resultSet.getString("recommendation")
						    )//商品1件の情報を取得しリストに追加
						);
				}
			}
		} catch (Exception e) { //例外発生時の処理
			e.printStackTrace();
		}
		return productList;
		
		}



	public void reduceStock(
			//減らしたい数とその商品ID 
			int quantity, String productId) {
	
			//DBに接続
			try (Connection connection = getConnection()) {
			
			//SQLで在庫数を減らすための更新処理
			String sql = "UPDATE products SET stock = stock - ?  WHERE product_id = ?";
			
			//SQLを実行するための準備
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, quantity);//quantityを？にセット
			statement.setString(2, productId); //在庫数をSQLパラメータに設定する

			System.out.println(statement);//実行をSQLに表示
			statement.executeUpdate();//DBを更新

		} catch (Exception e) {	//例外発生時の処理
			e.printStackTrace();
		}
	}


}
