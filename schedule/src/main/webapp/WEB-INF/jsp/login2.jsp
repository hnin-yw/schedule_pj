<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
	<div align="center">
		<h1>Login Form</h1>
		<br />
		<c:if test="${not empty error}">
		    <div class="alert alert-success">${error}</div>
		</c:if>
		<form action="schedule/login" method="post">

			<table class='table table-hover table-responsive table-bordered'>
				<tr>
					<td>ユーザ名:</td>
					<td><input type="text" id="userName" name="userName" required></td>
				</tr>
				<tr>
					<td>パスワード:</td>
					<td><input type="password" id="password" name="password"
						required></td>
				</tr>
				<tr>
					<td></td>
					<td>
						<button type="submit" class="btn btn-primary">ログイン</button>
					</td>
				</tr>

			</table>
		</form>
	</div>
</body>
</html>
