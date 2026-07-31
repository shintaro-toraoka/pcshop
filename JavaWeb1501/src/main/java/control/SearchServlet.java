package control;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Operation;
import model.Product;

/**
 * Servlet implementation class Search
 */
@WebServlet("/search")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("UTF-8");
		String keyword = request.getParameter("query");
		
		if(keyword != null) {
			keyword = keyword.replace("　", " ");
		}
		
		//検索処理
		Operation op = new Operation();
		List<Product> listProd = op.searchProduct(keyword);
		
		//商品が見つからない場合
		if (listProd.isEmpty()) {
			request.setAttribute("message","該当商品が見つかりませんでした。");
		}


		// JSPへ渡す
		request.setAttribute("query",keyword);
		request.setAttribute("listProd", listProd);
		
		// 商品一覧画面へ戻す
		request.getRequestDispatcher("/select.jsp")
		       .forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
