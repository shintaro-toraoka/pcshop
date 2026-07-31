package model;

/**
 * 商品クラス
 * @author M.Takahashi
 */
public class Product {
	/******** フィールド ******************************************/
	/**
	 * 商品ID
	 */
	private String id;
	
	/**
	 * 商品名
	 */
	private String name;
	
	private String image_path;
	
	private int quantity;
	public void setQuantity(int value) {
		this.quantity = value;
	}
	
	/**
	 * 価格
	 */
	private int price;
	
	//在庫数
	private int stock;

	//商品情報詳細フィールド名
	private String calories;
	private String nutrients;
	private String recommendation;

	
	
	
	/******** コンストラクタ **************************************/
	/**
	 * フィールド初期化コンストラクタ
	 * @param id
	 * @param name
	 * @param price
	 */
	/*これがもともとのもの商品情報詳細表示のためコメントアウト
	 * public Product(String id, String name, String image_path,int price, int stock) {
		this.id = id;
		this.name = name;
		this.image_path = image_path;
		this.price = price;
		this.stock = stock;
	}*/
	
	//コンストラクタ
	public Product(
	        String id,
	        String name,
	        String image_path,
	        int price,
	        int stock,
	        String calories,
	        String nutrients,
	        String recommendation) {
	    this.id = id;
	    this.name = name;
	    this.image_path = image_path;
	    this.price = price;
	    this.stock = stock;

	    this.calories = calories;
	    this.nutrients = nutrients;
	    this.recommendation = recommendation;
	}


	/******** メソッド ******************************************/
	/*--------------------getter/setter--------------------*/
	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	public String getImagePath() {
		return image_path;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * @return price
	 */
	public int getPrice() {
		return price;
	}
	
	public int getStock() {
		return stock;
	}
	
	//税額のみ取得（予備）
	/*
	public int getTaxAmount() {
		Calculator calculator = new Calculator();
		int amount = 0;
		amount = calculator.taxAmount(price);
		return amount;
	}
	*/

	/**
	 * 税込価格を文字列で返す（３桁カンマ区切り＋"円"）
	 * @return 税込価格 + 円
	 */
	public String getPriceIncludingTaxString() {
		//改修
		//商品価格を税込表示（税率10％）
		Calculator calculator = new Calculator();
		int amount = 0;
		amount = calculator.addTax(price);
	
		return String.format("%,d", amount) + "円";
		//return String.format("%,d", price) + "円";
	}
	//税込価格をintで返す
	public int getPriceIncludingTax() {
		Calculator calculator = new Calculator();
		int amount = 0;
		amount = calculator.addTax(price);
		return amount;
	}
	
<<<<<<< HEAD
	//商品情報詳細ゲッターセッター
	public String getCalories() {
	    return calories;
	}

	public void setCalories(String calories) {
	    this.calories = calories;
	}

	public String getNutrients() {
	    return nutrients;
	}

	public void setNutrients(String nutrients) {
	    this.nutrients = nutrients;
	}

	public String getRecommendation() {
	    return recommendation;
	}

	public void setRecommendation(String recommendation) {
	    this.recommendation = recommendation;
	}
	
=======
	public int getboughtPrice () {
		Calculator calculator = new Calculator();
		int amount = 0;
		amount = calculator.addTax(price) * quantity;
		return amount;

	}
>>>>>>> 3f6742ad24ba0334daca5380b64e7d224ddeebe9
}
