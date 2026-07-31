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
<link rel="icon" href="<%=request.getContextPath()%>/images/ikon.png"
	type="image/png">
</head>
<body>
	<%@include file="header-navi.jsp"%>
	<img src="images/deco1.png" class="main-image1">
	<img src="images/deco2.png" class="main-image2">
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
			<th class="selectColumn"></th><!-- 削除列 -->
			<th class="proIdColumn">商品ID</th>
			<th class="proNameColumn">商品名</th>
			<th class="proImageColumn">商品画像</th>
			<th class="quantityColumn">数量</th>
			<th class="priceColumn">小計（税込）</th>
			<th class="stockColumn">在庫数</th>
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
			<%
			String imagePath = prod.getImagePath();
			%>

			<td>
				<%
				if (imagePath == null || imagePath.isBlank()) {
				%>
				<p>No Image</p> <%
 } else {
 String imageUrl = "images/" + imagePath;
 %> <%-- <a href="<%=imageUrl%>"> --%> <img src="<%=imageUrl%>"
				class="zoom" width="60" height="50" alt="<%=prod.getName()%>"
				onerror="this.onerror=null; this.src='images/Error.png';"> </a> <%
 }
 %>
			</td>

			<td><%=prod.getQuantity()%>個</td>
			<td><%=String.format("%,d", prod.getPriceIncludingTax() * prod.getQuantity())%>円</td>
			<td><%=prod.getStock()%></td>
		</tr>
		<%
		}
		%>
	</table>

	<div id="zoomback">
		<img id="zoomimg" src="">

	</div>
	<script>
		// 要素を取得　..①
		const zoom = document.querySelectorAll(".zoom");
		const zoomback = document.getElementById("zoomback");
		const zoomimg = document.getElementById("zoomimg");

		// 一括でイベントリスナ　..②
		zoom.forEach(function(value) {
			value.addEventListener("click", kakudai);
		});

		function kakudai(e) {

			// 拡大領域を表示　..③
			zoomback.style.display = "flex";
			// 押された画像のリンクを渡す　..④
			zoomimg.setAttribute("src", e.target.getAttribute("src"));
		}

		// 元に戻すイベントリスナを指定　..⑤
		zoomback.addEventListener("click", modosu);

		// 拡大領域を無きものに　..⑥
		function modosu() {

			zoomback.style.display = "none";
		}
	</script>

	<br>

	<p>
		合計（税込）：<%=cart.getTotalPriceIncludingTaxString()%>
		円になります。
	</p>

	<form action="pay-servlet" method="post" onSubmit="PayCheck()">
		<input type="submit" value="精算"> <br>

	</form>

	<!--精算ボタンを押すと確認のポップアップを表示-->
	</form>
	<script>
		function PayCheck() {
			if (confirm('カート内の商品を精算してよろしいですか？')) {
				return true;
			} else {
				alert('キャンセルされました');
				return false;
			}
		}
	</script>

	<%
	} else {
	%>
	<p>カートの中は空です。</p>
	<%
	}
	%>
</body>
</html>