<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="utf-8" />
<title>Create User</title>
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
			<h1>Create User</h1>
			<br />
			<form action='/schedule/users/save' method='post'>
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>Group</td>
						<td>
							<select name="groupId" class="form-control">
							<c:choose>
							    <c:when test="${gpLists.size() > 0}">
							        <c:forEach items="${gpLists}" var="group">
							            <option value="${group.getId()}">${group.getGroupName()}</option>
							        </c:forEach>
							    </c:when>
							    <c:otherwise>
							        <option value="0">Choose Group</option>
							    </c:otherwise>
							</c:choose>
					        </select>
						</td>
					</tr>
					<tr>
						<td>User Name or Login Name:</td>
						<td>
							<input type="text" id="userName" name="userName"
							placeholder="User Name or Login Name" class="form-control "> 
						</td>
					</tr>
					<tr>
						<td>User First Name:</td>
						<td>
							<input type="text" id="userFirstName" name="userFirstName"
							placeholder="User First Name" class="form-control "> 
						</td>
					</tr>
					<tr>
						<td>User Last Name:</td>
						<td>
							<input type="text" id="userLastName" name="userLastName"
							placeholder="User Last Name" class="form-control "> 
						</td>
					</tr>
					<tr>
						<td>Post Code:</td>
						<td>
							<input type="text" id="postCode" name="postCode"
							placeholder="Post Code" class="form-control "> 
						</td>
					</tr>
					<tr>
						<td>Address:</td>
						<td>
							<input type="text" id="address" name="address"
							placeholder="Address" class="form-control "> 
						</td>
					</tr>
					<tr>
						<td>Phone Number:</td>
						<td>
							<input type="text" id="telNumber" name="telNumber"
							placeholder="Phone Number" class="form-control "> 
						</td>
					</tr>
					<tr>
						<td>Email:</td>
						<td>
							<input type="text" id="email" name="email"
							placeholder="Email" class="form-control "> 
						</td>
					</tr>
					<tr>
						<td>Password:</td>
						<td>
							<input type="password" id="password" name="password"
							placeholder="Password" class="form-control "> 
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<button type="submit" class="btn btn-primary">Save User</button>
							<a href="/schedule/users">
								<button type="button" class="btn btn-primary">Cancel</button>
						</a>
						</td>
					</tr>
	
				</table>
			</form>
		</div>
	</div>
</body>
</html>