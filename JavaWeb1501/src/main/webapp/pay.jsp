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
<title>精算完了</title>
<link rel="stylesheet" href="style.css">
 <link rel="icon" href="<%= request.getContextPath() %>/images/tiger.png" type="image/png">
</head>
<body>
	<%@include file="header-navi.jsp"%>
	<img src ="images/deco1.png" class="main-image3">
	<img src ="images/deco2.png" class="main-image4">

	<%
List<Product> listProd;
Cart payData = (Cart) session.getAttribute("pay");
if (payData == null) {
	listProd = new ArrayList<Product>();
} else {
	listProd = payData.getListProd();
}
if (listProd.size() > 0) {
%>
	<h2>精算完了</h2>

	<p>お買い上げ ありがとうございました。</p>
		<table class="pay-list">
		<tr>
			<th>商品ID</th>
			<th>商品名</th>
			<th>数量</th>
			<th>小計（税込）</th>
		</tr>

		<%
		for  (Product prod : listProd) {
		%>
		<tr>
			<td><%=prod.getId()%></td>
			<td><%=prod.getName()%></td>
			<td><%=prod.getQuantity()%>個</td>
			<td><%=String.format("%,d",prod.getPriceIncludingTax() * prod.getQuantity())%>円</td>
		</tr>
		<%
		}
		%>
	</table>
	<br>
	<p>
合計（税込）：<%= payData.getTotalPriceIncludingTaxString() %> 円になります。
</p>
	<%
	session.removeAttribute("pay");//精算済情報の削除
	}
	%>
	

</body>
</html>