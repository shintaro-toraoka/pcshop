package model;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import dao.ProductDaoDB;
import dao.UserDaoDB;


/**
 * 店内オペレーションクラス
 * @author M.Takahashi
 */

public class Operation {
	
	
	private UserDaoDB userDao;
	private ProductDaoDB productDao;
	//private PaymentDaoDB paymentDao;

	public Operation() {
	userDao = new UserDaoDB("localhost","3306","cscdb","RyanGosling","DBcsc26Newbie");
	productDao = new ProductDaoDB("localhost","3306","cscdb","RyanGosling","DBcsc26Newbie");
	//paymentDao = new PaymentDaoDB("localhost","3306","cscdb","root","mysql2026");
	}
	/**
	 * ログイン時の処理
	 * @param userId リクエストパラメータ
	 * @param password リクエストパラメータ
	 * @param session セッションオブジェクト
	 * @return true .. 正常、false .. ID／パスワード誤り
	 */
	public boolean loginProc(String userId, String password, HttpSession session) {

		// ログイン認証
		boolean result = authenticate(userId, password);

		if (result) {
			// 店舗データの作成⇒セッションに格納
			User user = userDao.getUser(userId);
			session.setAttribute("userName", user.getName());
			Store store = makeStore();
			session.setAttribute("store", store);

			// カート情報の作成（userId設定・商品リストは空）⇒セッションに格納
			Cart cart = new Cart(userId, new ArrayList<Product>());
			session.setAttribute("cart", cart);
		}

		return result;
	}

	/**
	 * 認証する
	 * @param userId ユーザID
	 * @param password パスワード
	 * @return 結果 (true / false)
	 */
	private boolean authenticate(String userId, String password) {

		// ★ここでは password = "pass" であれば true とする
		boolean result = false;//password.equals("pass");
		//userdaoを使ってUserを検索して、Userを取得
		User user = userDao.getUser(userId);
		//Userがある場合
		//パスワードを照合（入力値と登録されているパスワードが一致するか確認）
		if(user != null) {
		result = password.equals(user.getPassword());
		}
		//Userがない場合
		//false（認証NG）
		else {
			return false;
		}

		return result;
	}

	/**
	 * 店舗情報（店舗名＋選択データ（リスト））を作成する
	 * @return 店舗情報
	 */
	private Store makeStore() {
		List<Product> productList = productDao.getProductList();
		

		// 店舗情報作成
		Store store = new Store("ToraoCurry", productList);

		// 商品追加
		/*store.add(new Product("A110", "無線マウス", 2000));
		store.add(new Product("A120", "薄型キーボード", 3600));
		store.add(new Product("A130", "Webカメラ", 3900));
		store.add(new Product("A140", "トラックボールマウス", 2900));
		store.add(new Product("A150", "USB接続HDD（外付け）", 9800));
		store.add(new Product("A160", "2m電源タップ５口", 1900));
		store.add(new Product("A170", "USB接続マイク", 3500));
		store.add(new Product("A180", "小型ディスプレイ", 11000));
		store.add(new Product("A190", "LED照明", 4200));
		store.add(new Product("A200", "骨伝導イヤホン", 7800));
*/
		return store;
	}

	/**
	 * ログアウト時の処理
	 * @param session
	 */
	public void logoutProc(HttpSession session) {

		session.invalidate();

	}

	public void addProd(int idx, int quantity, HttpSession session) {

		//店舗情報・カート情報の取得
		Store store = (Store) session.getAttribute("store");
		Cart cart  = (Cart) session.getAttribute("cart");

		if((store != null) && (cart != null)) {
			String newProd = store.getListProd().get(idx).getId();		
			for(Product prod : cart.getListProd()) {
				if(prod.getId().equals(newProd)) {
//					System.out.println("一致");
//					System.out.println(store.getListProd().get(idx).getQuantity());//元
//					System.out.println(prod.getQuantity());//新
					int total = prod.getQuantity() + quantity;
					quantity = total;
				}
				
			}
			cart.oldRemoveProd(newProd);

			store.getListProd().get(idx).setQuantity(quantity);
			//カートに指定の商品を追加
			cart.add(store.getListProd().get(idx));


			

			//セッションに再度格納
			session.setAttribute("cart", cart);
		}
	}

	public void removeProd(int idx, HttpSession session) {
		//店舗情報・カート情報の取得
		Store store = (Store) session.getAttribute("store");
		Cart cart  = (Cart) session.getAttribute("cart");

		if((store != null) && (cart != null)) {
			//カートに追加された商品を除去
			cart.remove(idx);

			//セッションに再度格納
			session.setAttribute("cart", cart);

		}
	}

	public void pay(HttpSession session) {
		//店舗情報・カート情報の取得
		Cart cart  = (Cart) session.getAttribute("cart");
		
		if(cart != null) {
			//セッションに格納（精算済みデータ）
			session.setAttribute("pay", cart);
			//cartの内容をpaymentテーブルに登録する
			//カート内の商品リスト List<Product> listProd の件数分 paymentテーブルに登録する
			List<Product> listProd = cart.getListProd();
//
for (Product product : listProd) {
	
	productDao.reduceStock(product.getId());
//
//            paymentDao.insertPayment(
//                cart.getUserId(),     // ユーザID
//                cart.getUserName(),//ユーザ名
//                product.getId(),      // 商品ID
//                product.getName(),    // 商品名
//                product.getPrice()    // 金額
//            );
	
			System.out.println("User;"+ cart.getUserId());
			System.out.println("商品;" + product.getId());
			
			
        }

			//カート情報の新規作成→セッションに格納
			Cart newCart = new Cart(cart.getUserId(), new ArrayList<Product>());
			session.setAttribute("cart", newCart);
			
		}
		
	}
}
