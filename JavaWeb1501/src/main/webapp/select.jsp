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
<link rel="icon" href="<%=request.getContextPath()%>/images/ikon.png"
	type="image/png">
</head>
<body>
	<%@include file="header-navi.jsp"%>
	<img src="images/deco1.png" class="main-image1">
	<img src="images/deco2.png" class="main-image2">

	<h2>商品選択</h2>
	<form action="search" method="get">
		<input type="text" name="query" placeholder="キーワードを1つ入力">
		<button type="submit">をさがす</button>
	</form>

	<%
	//	List<Product> listProd;
	List<Product> listProd = (List<Product>) request.getAttribute("listProd");

	if (listProd == null) {

		Store store = (Store) session.getAttribute("store");
		if (store == null) {
			listProd = new ArrayList<Product>();
		} else {
			listProd = store.getListProd();
		}
	}

	if (listProd.size() > 0) {
	%>


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
			String imagePath = prod.getImagePath();
			String formId = "addProdForm" + idx;
		%>

		<tr>
			<td>
				<form id="<%=formId%>" action="add-prod-servlet" method="POST">

					<input type="hidden" name="productId" value="<%=prod.getId()%>">

					<%
					if (prod.getStock() > 0) {
					%>
					<input type="submit" value="選択">
					<%

					} else {
					%>
					<input type="submit" value="選択" disabled>
					<%
					}
					%>


				</form>
			</td>

			<td><%=prod.getId()%></td>


			<!-- <td><%=prod.getName()%></td>-->

			<!-- ここから商品リンク化 -->
			<td><span class="prod-name" data-id="<%=prod.getId()%>"
				data-name="<%=prod.getName()%>"
				data-price="<%=prod.getPriceIncludingTaxString()%>"
				data-stock="<%=prod.getStock()%>" data-image="<%=imagePath%>"
				data-calories="<%=prod.getCalories()%>"
				data-nutrients="<%=prod.getNutrients()%>"
				data-recommendation="<%=prod.getRecommendation()%>">
				 <%=prod.getName()%>

			</span></td>
			<!-- ここまで -->

			


			<td>
				<%
				if (imagePath == null || imagePath.isBlank()) {
				%>
				<p>No Image</p> <%
 } else {
 String imageUrl = "images/" + imagePath;
 %> <img src="<%=imageUrl%>" class="zoom" width="60" height="50"
				alt="<%=prod.getName()%>"
				onerror="this.onerror=null; this.src='images/Error.png';"> <%
 }
 %>
			</td>

			<td>
				<%
				if (prod.getStock() > 0) {
				%> <input type="number" name="quantity" value="1" min="1"
				max="<%=Math.min(prod.getStock(), 10)%>" required class="quanti"
				form="<%=formId%>"> <%
 } else {
 %> - <%
 }
 %>
			</td>

			<td><%=prod.getPriceIncludingTaxString()%></td>

			<td>
				<%
				if (prod.getStock() > 0) {
				%> <%=prod.getStock()%> <%
 } else {
 %> 在庫切れ <%
 }
 %>
			</td>
		</tr>

		<%
		}
		%>
	</table>

	<!-- 商品名クリックしたら情報表示 -->
	<div id="detailModal" class="modal">
		<div class="modal-content">
			<span id="closeDetailModal">&times;</span>

			<h3 id="modalName"></h3>

			<p>
				<img id="modalImage" src="">
			</p>
			<p>
				<b>カロリー</b><br> <span id="modalCalories"></span>
			</p>

			<p>
				<b>栄養素</b><br> <span id="modalNutrients"></span>
			</p>

			<p>
				<b>おすすめポイント</b><br> <span id="modalRecommendation"></span>
			</p>


		</div>

		<!--ここまで -->

	</div>
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

			//ここから商品リンク化
			const modal = document.getElementById("detailModal");
const closeModal = document.getElementById("closeDetailModal");

document.querySelectorAll(".prod-name").forEach(item => {

    item.addEventListener("click", function() {

        document.getElementById("modalName").textContent =
            this.textContent;
        
        document.getElementById("modalCalories").textContent =
            this.dataset.calories;

        document.getElementById("modalNutrients").textContent =
            this.dataset.nutrients;

        document.getElementById("modalRecommendation").textContent =
            this.dataset.recommendation;

        document.getElementById("modalImage").src =
            "images/" + this.dataset.image;

        modal.style.display = "block";
        
        
    });

});

closeModal.addEventListener("click", function() {
    modal.style.display = "none";
});
			
		</script>
		<%
		}
		%>
	
</body>
</html>