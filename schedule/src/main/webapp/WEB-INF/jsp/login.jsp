<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
	<div align="center">
		<h1>Login Form</h1>
		<br />
		<form action="schedule/login" method="post">

			<table class='table table-hover table-responsive table-bordered'>
				<tr>
					<td>User Name:</td>
					<td><input type="text" id="userName" name="userName" required></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="password" id="password" name="password"
						required></td>
				</tr>
				<tr>
					<td></td>
					<td>
						<button type="submit" class="btn btn-primary">Login</button>
					</td>
				</tr>

			</table>
		</form>
		<c:choose>
		<c:when test="${error}">
			<div style="color: red;">
				Error: <span>${error}</span>
			</div>
		</c:when>
		</c:choose>
	</div>
</body>
</html>
