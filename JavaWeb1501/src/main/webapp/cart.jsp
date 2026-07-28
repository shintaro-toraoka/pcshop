<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Cart"%>
<%@ page import="model.Product"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>カート内一覧</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
	<%@include file="header-navi.jsp"%>

	<%
	List<Product> listProd;
	Cart cart = (Cart) session.getAttribute("cart");
	if (cart == null) {
		listProd = new ArrayList<Product>();
	} else {
		listProd = cart.getListProd();
	}
	if (listProd.size() > 0) {
	%>
	<h2>カート内一覧</h2>

	<table class="cart-list">
		<tr>
			<th></th>
			<th>商品ID</th>
			<th>商品名</th>
			<th>商品画像</th>
			<th>価格（税込）</th>
			<th>在庫数</th>
		</tr>
		<%
		for (int idx = 0; idx < listProd.size(); idx++) {
			Product prod = listProd.get(idx);
		%>
		<tr>
			<td>
				<form action="remove-prod-servlet" method="POST">
					<input type="hidden" name="idx" value="<%=idx%>"> <input
						type="submit" value="削除">
				</form>
			</td>
			<td><%=prod.getId()%></td>
			<td><%=prod.getName()%></td>
			<td><a href="images/<%=prod.getImagePath()%>"><img
					src="images/<%=prod.getImagePath()%>" width="60" height="50"></a></td>
			<td><%=prod.getPriceIncludingTax()%></td>
			<td><%=prod.getStock()%></td>
		</tr>
		<%
		}
		%>
	</table>
	<br>


	<p>
		合計（税込）：<%=cart.getTotalPriceIncludingTax()%>円になります。
	</p>

	<form action="pay-servlet" method="post">
		<input type="submit" value="精算"><br>

	</form>
	<%
	} else {
	%>
	<p>カートの中は空です。</p>
	<%
	}
	%>
</body>
</html>