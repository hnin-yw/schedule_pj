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
		<form action='/demo/groups/update' method='post'>

			<table class='table table-hover table-responsive table-bordered'>
				<tr>
					<td>Group Code:</td>
					<td><input type='text' name='group_code' class='form-control'
						value="${group.getGroupCode()}" /></td>
				</tr>
				<tr>
					<td>Group Name:</td>
					<td><input type='text' name='group_name' class='form-control'
						value="${group.getGroupName()}" /></td>
				</tr>


				<input type='hidden' id='id' rows='5' class='form-control' name='id'
					value="${group.id}" />
				<tr>
					<td></td>
					<td>
						<button type="submit" class="btn btn-primary">Update</button>
					</td>
				</tr>

			</table>
		</form>
	</div>
</body>
</html>