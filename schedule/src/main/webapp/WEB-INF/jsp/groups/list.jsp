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
			<h3>List of Groups</h3>
			<br>
			<c:choose>
				<c:when test="${listGroups.size() > 0}">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th><b>Group Name</b></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${listGroups}" var="group">
								<tr>
									<td>${group.getGroupName()}</td>

									<td><a href="/schedule/groups/edit/${group.getId()}">
											<button type="submit" class="btn btn-primary">Edit</button>
									</a><a href="/schedule/groups/delete/${group.getId()}">
											<button type="submit" class="btn btn-danger">Delete</button>
									</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:when>
				<c:otherwise>
					<div>
						<p>There is no group.</p>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
		<div>
			<a href="/schedule/groups/create">
				<button type="button" class="btn btn-primary">Create Group</button>
			</a>
		</div>
	</div>
</body>
</html>