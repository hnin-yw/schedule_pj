<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="utf-8" />
<title>Edit Group</title>
</head>
<body>
	<div align="center">
		<h1>Edit Group</h1>
		<br />
		<form action='/schedule/groups/update' method='post'>

			<table class='table table-hover table-responsive table-bordered'>
				<tr>
					<td>Group Name:</td>
					<td><input type='text' name='groupName' id='groupName'
						class='form-control' value="${group.getGroupName()}" /></td>
				</tr>
				<tr>
					<td><input type='hidden' id='id' class='form-control'
						name='id' value="${group.id}" /></td>
					<td>
						<button type="submit" class="btn btn-primary">Update</button> <a
						href="/schedule/groups">
							<button type="button" class="btn btn-primary">Cancel</button>
					</a>
					</td>
				</tr>

			</table>
		</form>
	</div>
</body>
</html>