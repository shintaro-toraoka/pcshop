package control;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Operation;
import model.Payment;

/**
 * Servlet implementation class History
 */
@WebServlet("/History")
public class HistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HistoryServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		//セッションを取得
		HttpSession session = request.getSession();
		
		//Operationオブジェクトを生成
		Operation op = new Operation();
		String userId = (String) session.getAttribute("userId");//セッションからuserIdを取得
		
		//購入履歴を取得
		String date = request.getParameter("date");
		List<Payment> paymentList = op.getPaymentList(userId);
		
		//購入履歴をセット
		request.setAttribute("paymentList", paymentList);
		
		//history.jspへ遷移する
		RequestDispatcher dispatcher = request.getRequestDispatcher("history.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
