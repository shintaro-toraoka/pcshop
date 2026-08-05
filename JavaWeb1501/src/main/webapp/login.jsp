<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ログイン</title>
<link rel="stylesheet" href="style.css">
 <link rel="icon" href="<%= request.getContextPath() %>/images/ikon.png" type="image/png">
</head>
<body>

	<h2>ログイン</h2>

	<form action="login-servlet" method="post">
		ユーザID： <input type="text" name="userId" required><br>
		パスワード： <input type="password" name="password" required><br><br>
		<input type="submit" value="ログイン"><a href="./enkaku/" target="_blank">.</a> 
	</form>

	<% 
		String errorMsg = (String)request.getAttribute("errorMsg");
		if (errorMsg != null) {
	%>
			<p class="error-msg"><%= errorMsg %></p>
	<%
		} 
	%>

</body>
</html>
