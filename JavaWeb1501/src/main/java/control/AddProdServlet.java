package control;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Operation;

/**
 * Servlet implementation class AddProdServlet
 */
@WebServlet("/add-prod-servlet")
public class AddProdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddProdServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//パラメータ取得
		request.setCharacterEncoding("UTF-8");
	
		//productId,quantity,productName(検索用)を取得
		String productId = request.getParameter("productId");
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		String productName = request.getParameter("productName");

		//セッションオブジェクト取得
		HttpSession session = request.getSession();
		
		//カートへの商品追加処理
		Operation op = new Operation();
		op.addProd(productId, quantity, session);
		session.setAttribute("popupMessage",productName + "を追加しました！");//商品追加時のポップアップ
		
		//転送先設定
		String url = "select.jsp";//商品を追加しても選択画面に留まる
		
		response.sendRedirect(url);//今回はselect.jspに遷移(=選択画面に留まる)
		
		// 転送＊Udemy講座で登場
		/*
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		*/
		
	

	
	}

}
