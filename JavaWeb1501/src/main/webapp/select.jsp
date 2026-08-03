<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Store"%>
<%@ page import="model.Product"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html>
	<head>
	<!-- 基本設定 -->
		<meta charset="UTF-8">
		<title>商品選択</title>
		
		<link rel="stylesheet" href="style.css">
		<link rel="icon" href="<%=request.getContextPath()%>/images/ikon.png"
		type="image/png">
	</head>
<body>

	<!-- ヘッダーナビ -->
	<%@include file="header-navi.jsp"%>
	
	<!-- 装飾画像 -->
	<img src="images/deco1.png" class="main-image1">
	<img src="images/deco2.png" class="main-image2">

	<!-- 商品検索の処理 -->
	<h2>商品選択</h2>
	
	<form action="search" method="get">
		<input type="text" value="${query}" name="query"
			placeholder="キーワードを入力">
		<button type="submit">をさがす</button>
	</form>
	
	<!-- 検索メッセージ表示 -->
	<%
	String keyword = request.getParameter("query");
	%>
	<%
	String message = (String) request.getAttribute("message");

	if (message != null) {
	%>

	<p style="color: red;">
		<%=message%>
	</p>
	<%
	}
	%>

<!-- 商品一覧データ取得 -->
	<%
	//	List<Product> listProd;
	List<Product> listProd = (List<Product>) request.getAttribute("listProd");

	if (listProd == null) {

		//セッションから店舗情報取得
		Store store = (Store) session.getAttribute("store");
		if (store == null) {
			listProd = new ArrayList<Product>();
		} else {
			listProd = store.getListProd();
		}
	}
	int pageSize = 10;

	if (listProd.size() > 0) {
	%>

	<br>

	<!-- 商品一覧テーブル -->
	<table class="select-list">
		
	<!-- テーブルヘッダー -->
		<tr>
			<th class="selectColumn"></th>
			<!-- 選択列 -->
			<th class="proIdColumn">商品ID</th>
			<th class="proNameColumn">商品名</th>
			<th class="proImageColumn">商品画像</th>
			<th class="quantityColumn">数量</th>
			<th class="priceColumn">価格（税込）</th>
			<th class="stockColumn">在庫数</th>
		</tr>

		<!-- ページング設定 -->
		<%
			int pageNo = 1;
			String pageParam = request.getParameter("page");
			if (pageParam != null) {
			pageNo = Integer.parseInt(pageParam);
			}
			int start = (pageNo - 1) * pageSize;
			int end = pageNo * pageSize;

		for (int idx = start; idx < end && idx < listProd.size(); idx++) {
			Product prod = listProd.get(idx);
			String imagePath = prod.getImagePath();
			String formId = "addProdForm" + idx;
		%>

		<tr>
			<td>
				<form id="<%=formId%>" action="add-prod-servlet" method="POST">

					<input type="hidden" name="productId" value="<%=prod.getId()%>">
					<input type="hidden" name="productName" value="<%=prod.getName()%>">

					<!-- 選択ボタン -->



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

			
			<!-- 商品選択完了メッセージ -->
		<%
			String addedMessage = (String)session.getAttribute("addedMessage");
			if(addedMessage !=null){
		%>
		<div id="select-popup" class="pp-popup pp-active">
			<p><%=addedMessage %></p>
		</div>
		<%
			session.removeAttribute("addedMessage");
			}
		%>
		
		<td><%=prod.getId()%></td>

			<!-- ここから商品名クリックで詳細モーダル表示 -->


			<!-- 選択時のポップアップ表示 -->
			<%
	String popupMessage = (String)session.getAttribute("popupMessage");
	if(popupMessage !=null){


			%>
			<div id="select-popup" class="pp-popup pp-active">
				<p><%=popupMessage %></p>
			</div>
			<%
		session.removeAttribute("popupMessage");
			}
	%>

			<!-- ここから商品リンク化 -->

			<td><span class="prod-name" data-id="<%=prod.getId()%>"
				data-name="<%=prod.getName()%>"
				data-price="<%=prod.getPriceIncludingTaxString()%>"
				data-stock="<%=prod.getStock()%>" data-image="<%=imagePath%>"
				data-calories="<%=prod.getCalories()%>"
				data-nutrients="<%=prod.getNutrients()%>"
				data-recommendation="<%=prod.getRecommendation()%>"> <%=prod.getName()%>
			</span></td>

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

		<!-- 数量選択 -->
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
		
		<!-- 在庫切れ表示 -->
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
	<%
	}
	%>

	<%
		int totalPage = (listProd.size() + pageSize - 1) / pageSize;
		for(int i = 1; i <= totalPage; i++){
	%>
		<a href="select.jsp?page=<%=i%>"><%=i%></a>
	<%
	}
	%>

	<!-- 商品名クリックしたら情報表示 商品詳細モーダル -->
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
	</div>
	
	
	<div id="productModal" class="modal">
		<div class="modal-content">
			<span id="closeModal"></span>
		</div>
	</div>
	
	<!-- 商品画像拡大モーダル -->


	

	<div id="zoomback">
		<img id="zoomimg" src="">
	</div>




	<script>
			// 要素を取得　..①
			//商品画像拡大処理
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

//商品詳細モーダル表示 
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
<!-- 選択・削除のポップアップ表示 -->
<script>
const ppBtn = document.querySelector('#pp-btn');
const ppPopup = document.querySelector('#pp-popup');

	if (ppBtn && ppPopup) {
 		 ppBtn.addEventListener('click', function() {
    ppPopup.className = 'pp-popup pp-active';
 	 });
 	 ppPopup.addEventListener('animationend', function(e) {
    if (e.animationName === 'ppAnim') {
      ppPopup.className = 'pp-popup';
    }
  });
}
</script>
</body>
</html>