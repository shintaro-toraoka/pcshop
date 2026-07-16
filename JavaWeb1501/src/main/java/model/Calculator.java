package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * 税込み計算・カートに追加された商品分の税込み合計金額
 */
public class Calculator {
	//消費税率10％
	private final static BigDecimal TAX_RATE = new BigDecimal("0.1");

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		List<Product> productList = new ArrayList<Product>();
		//商品追加
		productList.add(new Product("A110", "無線マウス", 2000));
		//productList.add(new Product("A120", "薄型キーボード", 3600));

		//Calcuratorのcalcuratorメソッドを呼び出す
		Calculator calculator = new Calculator();
		int total = calculator.totalPriceIncludingTax(productList);

		//合計金額を出力する
		System.out.println(total);
		System.out.println(calculator.taxAmount(111));
	}

	/**
	 * 税額のみ算出
	 * @param productPrice：税抜商品価格
	 * @return 
	 */
	//税抜価格×税率＝税額
	public int taxAmount(int productPrice) {
		//int型 productPriceをBigDecimal型 priceにキャスト
		BigDecimal price = BigDecimal.valueOf(productPrice);
		return price.multiply(TAX_RATE).setScale(0, RoundingMode.DOWN).intValue();
	}

	/**
	 * 税込価格を算出
	 * @param productPrice：税抜商品価格
	 * @return
	 */
	//税抜価格＋税額＝税込価格
	public int addTax(int productPrice) {
		int total = 0;
		total = productPrice + taxAmount(productPrice);
		return total;
	}

	/**
	 * カートに追加された分の税込み合計金額
	 * @param listProd:精算完了の商品リスト
	 * @return
	 */
	public int totalPriceIncludingTax(List<Product> listProd) {
		int amount = 0;
		for (int i = 0; i < listProd.size(); i++) {
			//Product prod = listProd.get(i);
			amount += addTax(listProd.get(i).getPrice());
		}
		return amount;

	}

}
