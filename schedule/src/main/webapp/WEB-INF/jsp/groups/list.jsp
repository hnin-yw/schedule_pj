<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="utf-8" />
<title>List</title>
</head>
<body>
	<div class="container">
		<div align="center">
			<h3>List of Groups</h3>
			<br>
			<table class="table table-hover">
				<thead>
					<tr>
						<th><b>Group Code</b></th>
						<th><b>Group Name</b></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${listGroups}" var="group">
						<tr>
							<td>${group.getGroupName()}</td>

							<td><a href="/demo/groups/edit/${group.getId()}">
									<button type="submit" class="btn btn-primary">Edit
										User</button>
							</a></td>
							<td><a href="/demo/groups/delete/${group.getId()}">
									<button type="submit" class="btn btn-primary">Delete
										User</button>
							</a></td>
						</tr>

					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>