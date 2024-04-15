<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="utf-8" />
<title>Create Group</title>
</head>
<body>
	<div align="center">
		<h1>Create Group</h1>
		<br />
		<form action='/schedule/groups/save' method='post'>

			<table class='table table-hover table-responsive table-bordered'>
				<tr>
					<td>Group Name:</td>
					<td>
					<input type="text" id="groupName" name="groupName"
						placeholder="Group Name" class="form-control "> <span
						style="color: red;"><c:out
								value="${result.hasFieldErrors('groupName') == false ? result.getFieldError('groupName').getDefaultMessage() : ''}" /></span>

					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<button type="submit" class="btn btn-primary">Save Group</button>
						<a href="/schedule/groups">
							<button type="button" class="btn btn-primary">Cancel</button>
					</a>
					</td>
				</tr>

			</table>
		</form>
	</div>
</body>
</html>