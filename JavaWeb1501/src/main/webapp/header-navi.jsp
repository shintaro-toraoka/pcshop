<%@ page pageEncoding="UTF-8"%>
<%@ page import="model.Cart"%>
<%@ page import="model.Store"%>
<%@ page import="model.Product"%>
<%-- 店舗名・ユーザIDの表示 --%>
<% 
		Store storeHdr = (Store) session.getAttribute("store");
		Cart cartHdr = (Cart) session.getAttribute("cart");
		if ((storeHdr == null) || cartHdr == null) {
			request.setAttribute("errorMsg", "再ログインをお願いします。");	
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} else {
	%>
	<!-- お店の名前表示 -->
<h1><%=storeHdr.getName() %></h1>
<!-- ログイン中のユーザー名を表示 -->
<hr>
ユーザ名：<%= session.getAttribute("userName") %>
<hr>

<%-- ナビ表示 --%>
｜
<a href="select.jsp">商品選択</a>
<!-- カート内一覧ヘッダーにカート内の商品数を表示 -->
<%
Cart cart = (Cart)session.getAttribute("cart");
int count = 0;
if(cart != null){
	for(Product prod : cart.getListProd()) {
count += prod.getQuantity();
	}
}%>

｜
<a href="cart.jsp">カート内一覧<span class="cartCount">(<%= count %>)
</span></a>
｜
<a href="History">購入履歴</a>
｜
<a href="logout-servlet">ログアウト</a>
｜
<hr>
<%
		}
	%>
