<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
	<script>
	$(document).ready(function () {
	    $("#btnUserDelete").click(function (e) {
	        e.preventDefault();
	        var groupId = $(this).data('id');
	        window.alert(groupId);
	        $("#deleteGroupId").val(groupId);
	    }); 
	});
	</script>
</head>
<body>
	<div class="container">
		<div align="center">
			<h3>ユーザ一覧</h3>
			<br>
			<c:choose>
				<c:when test="${listUsers.getTotalElements() > 0}">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th><b>ユーザー名</b></th>
								<th><b>ユーザーの名</b></th>
								<th><b>ユーザーの姓</b></th>
								<th><b>グループ名</b></th>
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
											<button type="submit" class="btn btn-primary">編集</button>
										</a>
										<button type="button" data-id="${user.getId()}" id="btnUserDelete" class="btn btn-danger" data-toggle="modal" data-target="#deleteConfirmModel">
										   削除
										</button>
										<%-- <a href="/schedule/users/delete/${user.getId()}">
											<button type="submit" class="btn btn-danger">削除</button>
										</a> --%>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<%@ include file="/WEB-INF/jsp/pagination.jsp" %>
				</c:when>
				<c:otherwise>
					<div>
						<p>ユーザーがいません。</p>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
		<div>
			<a href="/schedule/users/create">
				<button type="button" class="btn btn-primary">ユーザー登録</button>
			</a>
			<a href="/schedule/main_menu">
				<button type="button" class="btn btn-primary">戻す</button>
			</a>
		</div>

		<!-- Delete Confirm Modal -->
		<div class="modal fade" id="deleteConfirmModel" tabindex="-1" role="dialog" aria-labelledby="deleteConfirmModelLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
		    	<div class="modal-content">
			      	<div class="modal-header">
			        	<h5 class="modal-title" id="exampleModalLabel">確認</h5>
			        	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
			          		<span aria-hidden="true">&times;</span>
			        	</button>
			      	</div>
			      	<div class="modal-body">削除してもよろしいですか?</div>
			      	<div class="modal-footer">
			      		<input type="text" class="form-control" id="deleteGroupId" name="deleteGroupId" />
			        	<button type="button" class="btn btn-primary">削除</button>
			        	<button type="button" class="btn btn-secondary" data-dismiss="modal">キャンセル</button>
			      	</div>
		    	</div>
		  	</div>
		</div>
		
	</div>
</body>
</html>