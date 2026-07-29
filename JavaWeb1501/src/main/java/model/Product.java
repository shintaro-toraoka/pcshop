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

	/******** コンストラクタ **************************************/
	/**
	 * フィールド初期化コンストラクタ
	 * @param id
	 * @param name
	 * @param price
	 */
	public Product(String id, String name, String image_path,int price, int stock) {
		this.id = id;
		this.name = name;
		this.image_path = image_path;
		this.price = price;
		this.stock = stock;
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
	public String getPriceIncludingTax() {
		//改修
		//商品価格を税込表示（税率10％）
		Calculator calculator = new Calculator();
		int amount = 0;
		amount = calculator.addTax(price);
	
		return String.format("%,d", amount) + "円";
		//return String.format("%,d", price) + "円";
	}

}
