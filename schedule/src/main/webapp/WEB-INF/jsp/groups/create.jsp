<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="utf-8" />
<title>Create Group</title>
</head>
<body>
	<div align="center">
		<h1>Create New Group</h1>
		<br />
		<form method="POST" action="/demo/groups/save" modelAttribute="group">
			<table>
				<tr>
					<td>Group Code:</td>
					<td><input type='text' id='groupCode' name='groupCode'
						class='form-control' value="" /></td>
				</tr>
				<tr>
					<td>Group Name:</td>
					<td><input type='text' id='groupName' name='groupName'
						class='form-control' value="" /></td>
				</tr>
				<tr>
				<tr>
					<td colspan="2"><button type="submit">Save</button></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
