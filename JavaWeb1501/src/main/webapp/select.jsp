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
</head>
<body>
	<%@include file="header-navi.jsp"%>

	<form action="/search" method="get">
  	<input type="text" name="query" placeholder="キーワードを１つ入力">
  	<button type="submit">検索</button>
	</form>

	<%
	List<Product> listProd;
	Store store = (Store) session.getAttribute("store");
	if (store == null) {
		listProd = new ArrayList<Product>();
	} else {
		listProd = store.getListProd();
	}
	if (listProd.size() > 0) {
	%>

	<h2>商品選択</h2>
	<form action="/search" method="get">
	<input type="text" name="query" placeholder="キーワードを1つ入力">
	<botton typ="submit">をさがす</botton>
	</form>
	
	<br>

	<table class="select-list">
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
				<form action="add-prod-servlet" method="POST">
					<input type="hidden" name="idx" value="<%=idx%>"> <input
						type="submit" value="選択">
				</form>
			</td>
			<td><%=prod.getId()%></td>
			<td><%=prod.getName()%></td>

			<td><img style=width="60" height="50"" img src="./image/<%=prod.getImagePath()%>.png"class="zoom"

			<%
			if(prod.getImagePath() == null){
%>
			<p>No Image</p>

			<%
			}else{
%>
			alt=<%=prod.getName() %>
			onerror="src='images/Error.png'">
<%
		}
		%></td>

			<td><%=prod.getPriceIncludingTax()%></td>
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
	<%
	}
	%>

</body>
</html>