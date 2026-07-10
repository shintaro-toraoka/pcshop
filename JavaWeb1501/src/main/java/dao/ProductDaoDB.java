package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Product;
import model.Store;

public class ProductDaoDB implements ProductDao {
	private String host;
	private String port;
	private String database;
	private String product_id;
	private String password;

	public ProductDaoDB(String host, String port, String database, String id, String password) {
		super();
		this.host = host;
		this.port = port;
		this.database = database;
		this.product_id = id;
		this.password = password;
	}

	@Override
	public List<Product> getProductList() {
		List<Product> productList = new ArrayList<>(); 
		//データベース接続
		try (Connection connection = getConnection()) {
			String sql = "SELECT * FROM product"; //TODO：ユーザを取得するSQLを書く

			PreparedStatement statement = connection.prepareStatement(sql);
			/*statement.setString(1, product_id); *///ユーザIDをSQLパラメータに設定する
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					productList.add (new Product(resultSet.getString("id"),
							resultSet.getString("name"),
							resultSet.getInt("price"))); //TODO：ユーザを生成する
				}
			}
		} catch (Exception e) { //例外発生時の処理
			e.printStackTrace();
		}
		return productList;
	}

	// DB接続処理	
	private Connection getConnection() throws ClassNotFoundException, SQLException {
		// MySQL JDBCドライバを読み込む	
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = String.format("jdbc:mysql://%s:%s/%s?allowPublicKeyRetrieval=true&useSSL=false", host, port,
				database);
		return DriverManager.getConnection(url, product_id, password);
	}
	
	private Store makeStore() {
		//Productテーブルから商品を検索して取得する
		List<Product> productList = getProductList();
		//店舗情報作成
		Store store = new Store("速水PC販売",new ArrayList<Product>());
		//
		for(Product product:productList) {
			//store.add(new.Product(・・・ここで商品情報を渡す...));
			store.add(product);
		}
		return store;

	}

}
