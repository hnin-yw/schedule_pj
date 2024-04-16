<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="utf-8" />
<title>List</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<div align="center">
			<h3>List of Users</h3>
			<br>
			<c:choose>
				<c:when test="${listUsers.getTotalElements() > 0}">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th><b>User Name</b></th>
								<th><b>User First Name</b></th>
								<th><b>User Last Name</b></th>
								<th><b>Group Name</b></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${listUsers.content}" var="user">
								<tr>
									<td>${user.getUserName()}</td>
									<td>${user.getUserFirstName()}</td>
									<td>${user.getUserLastName()}</td>
									<td>${user.getGroup().getGroupName()}</td>

									<td>
										<a href="/schedule/users/edit/${user.getId()}">
											<button type="submit" class="btn btn-primary">Edit</button>
										</a>
										<a href="/schedule/users/delete/${user.getId()}">
											<button type="submit" class="btn btn-danger">Delete</button>
										</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<%@ include file="/WEB-INF/jsp/pagination.jsp" %>
				</c:when>
				<c:otherwise>
					<div>
						<p>There is no group.</p>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
		<div>
			<a href="/schedule/users/create">
				<button type="button" class="btn btn-primary">Create User</button>
			</a>
		</div>
	</div>
</body>
</html>