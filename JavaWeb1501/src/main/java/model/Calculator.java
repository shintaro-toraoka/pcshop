package model;

import java.util.ArrayList;
import java.util.List;
/**
 * 税込み計算・カートに追加された商品分の税込み合計金額
 */
public class Calculator {
	//消費税10％
	final double tax = 1.1;

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		List<Product> productList = new ArrayList<Product>();
		//商品追加
		productList.add(new Product("A110", "無線マウス", 2000));
		productList.add(new Product("A120", "薄型キーボード", 3600));
		
		//Calcuratorのcalcuratorメソッドを呼び出す
		Calculator calculator = new Calculator();
		int total = calculator.getTotalAmount(productList);
		
		//合計金額を出力する
		System.out.println(total);
		

	}
	/**
	 * 税込み価格を出しちゃいました
	 * @param productPrice：税抜き商品価格
	 * @return
	 */
	public int addTax(int productPrice) {
		
		int total = 0;
		total = (int)(productPrice * tax);
		return total;
		
	}

	/**
	 * カートに追加された分の税込み合計金額
	 * @param listProd:清算完了の商品リスト
	 * @return
	 */
	public int getTotalAmount(List<Product> listProd) {
		int amount = 0;
		for (int i = 0; i < listProd.size(); i++) {
			Product prod = listProd.get(i);
			amount = amount + addTax(prod.getPrice());
		}
		return amount;
		
	}


}
