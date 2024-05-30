<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" />
<title>Schedule</title>
</head>
<body>
	<div class="container pt-5">
		<h1 class="text-center">Login Form</h1>
		<div class="container mt-5">
			<div class="row justify-content-center">
				<div class="col-md-6">
					<div class="card">
						<div class="card-body">
							<c:if test="${not empty error}">
								<div class="alert alert-danger">${error}</div>
							</c:if>

							<form action="<c:url value='/doLogin' />" method="post">
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />

								<div class="form-group">
									<label for="userName">ユーザ名 :</label>
									<!-- corrected the input id to "userName" -->
									<input type="text" class="form-control" id="userName"
										name="username" placeholder="ユーザ名" />
								</div>
								<div class="form-group">
									<label for="password">パスワード :</label> <input type="password"
										class="form-control" id="password" name="password"
										placeholder="パスワード" />
								</div>
								<button type="submit" class="btn btn-primary">ログイン</button>
							</form>
							<div>
								<a href="<c:url value="/oauth2/authorization/line" />">Login
									with LINE Account</a>
							</div>
							<div>
								<a href="<c:url value="/oauth2/authorization/google" />">Login
									with Google</a>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>