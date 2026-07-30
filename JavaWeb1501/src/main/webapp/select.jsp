<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Store"%>
<%@ page import="model.Product"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品選択</title>
<link rel="stylesheet" href="style.css">
 <link rel="icon" href="<%= request.getContextPath() %>/images/ikon.png" type="image/png">
</head>
<body>
	<%@include file="header-navi.jsp"%>

	<%
//	List<Product> listProd;
	List<Product> listProd = (List<Product>)request.getAttribute("listProd");
	
	if (listProd == null){
		
		Store store = (Store) session.getAttribute("store");
		if (store == null) {
			listProd = new ArrayList<Product>();
		} else {
			listProd = store.getListProd();
		}		
	}
	

	if (listProd.size() > 0) {
	%>

	<h2>商品選択</h2>
	<form action="search" method="get">
	<input type="text" name="query" placeholder="キーワードを1つ入力">
	<button type="submit">をさがす</button>
	</form>
	
	<br>

	<table class="select-list">
		<tr>
			<th></th>
			<th>商品ID</th>
			<th>商品名</th>
			<th>商品画像</th>
			<th>数量</th>
			<th>価格（税込）</th>
			<th>在庫数</th>
		</tr>

		<%
		for (int idx = 0; idx < listProd.size(); idx++) {
			Product prod = listProd.get(idx);
		%>
		<tr>
		<form action="add-prod-servlet" method="POST">
			<td>
				
					<input type="hidden" name="idx" value="<%=idx%>"> <input
						type="submit" value="選択">
			</td>
			<td><%=prod.getId()%></td>
<%
String imagePath = prod.getImagePath();
%>

<td>
	<%-- <a href="#"
		class="product-link"
		data-id-price="<%=prod.getPriceIncludingTax()%>"
		data-stock="<%=prod.getStock()%>">
		<%=prod.getName()%>
	</a>--%>
	<%=prod.getName()%>
</td>

<td>
	<%
	if (imagePath == null || imagePath.isBlank()) {
	%>
		<p>No Image</p>
	<%
	} else {
		String imageUrl = "images/" + imagePath;
	%>
		<%-- <a href="<%=imageUrl%>"> --%>
			<img
				src="<%=imageUrl%>"
				class="zoom"
				width="60"
				height="50"
				alt="<%=prod.getName()%>"
				onerror="this.onerror=null; this.src='images/Error.png';">
		</a>
	<%
	}
	%>
</td>

<td>
	<input
		type="number"
		name="quantity"
		value="1"
		min="1"
		max="<%=Math.min(prod.getStock(), 10)%>"
		class="quanti">
</td>

<td><%=prod.getPriceIncludingTaxString()%></td>

<td><%=prod.getStock()%></td>
		</form>
		</tr>
		<%
		}
		%>
	</table>
	<div id="productModal" class="modal">
    <div class="modal-content">
        <span id="closeModal"></span>
    
</div>
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
	<%
	}
	%>

</body>
</html>