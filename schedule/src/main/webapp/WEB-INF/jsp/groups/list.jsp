<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<meta charset="utf-8" />
<title>List</title>
</head>
<body>
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
						<td><c:out value="${group.group_code}"></c:out></td>
						<td><c:out value="${group.group_name}"></c:out></td>

						<td><a href="/demo/groups/edit/${group.id()}">
								<button type="submit" class="btn btn-primary">Edit User</button>
						</a></td>
						<td><a href="/demo/groups/delete/${group.id()}">
								<button type="submit" class="btn btn-primary">Delete
									User</button>
						</a></td>
					</tr>

				</c:forEach>
			</tbody>
		</table>
	</div>

</body>
</html>