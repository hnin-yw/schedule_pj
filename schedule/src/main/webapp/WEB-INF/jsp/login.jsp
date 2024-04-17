<%@ taglib prefix="c" uri="jakarta.tags.core"%>
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
						<c:if test="${not empty error}">
							<div class="alert alert-danger" role="alert">
								<strong>${error}</strong>
							</div>
						</c:if>
						<div class="card-body">
							<form action="schedule/login" method="post">
								<div class="form-group">
									<label for="email"> ユーザ名 :</label> <input type="text"
										class="form-control" id="userName" name="userName"
										placeholder="User Name" />
								</div>
								<div class="form-group">
									<label for="password"> パスワード :</label> <input type="password"
										class="form-control" id="password" name="password"
										placeholder="Password" />
								</div>
								<button type="submit" class="btn btn-primary">ログイン</button>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>