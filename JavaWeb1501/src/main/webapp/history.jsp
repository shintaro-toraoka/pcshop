<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Payment"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>購入履歴</title>
<link rel="stylesheet" href="style.css">
<!-- ブラウザアイコンの設定 -->
<link rel="icon" href="<%=request.getContextPath()%>/images/ikon.png"
	type="image/png">
</head>
<body>
	<%@include file="header-navi.jsp"%>
	<!-- 画面上部の装飾 -->
	<img src="images/deco1.png" class="main-image1">
	<img src="images/deco2.png" class="main-image2">

	<!-- 購入履歴のリストを作成 -->
	<%
	List<Payment> paymentList = (List<Payment>) request.getAttribute("paymentList");
	%>

	<h2>購入履歴</h2>


	<div class="history-count">
		購入件数：<%=paymentList.size()%></h3>
	</div>

	<!-- 購入履歴テーブルのヘッダー -->
	<table class="payment-list">
		<tr>
			<th>商品ID</th>
			<th>商品名</th>
			<th>数量</th>
			<th>価格（税込）</th>
			<th>購入日時</th>
		</tr>

		<%
		for (Payment payment : paymentList) {
		%>
		<!-- 購入履歴テーブルの中身を表示 -->
		<tr>
			<td><%=payment.getProductId()%></td>
			<td><%=payment.getProductName()%></td>
			<td><%=payment.getQuantity()%>個</td>
			<td><%=payment.getAmount()%>円</td>
			<td><%=payment.getPurchaseDate().toLocalDate()%></td>
		</tr>
		<%
		}
		%>
	</table>
</body>
</html>