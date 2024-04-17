<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="UTF-8">
<title>List</title>
<link rel="stylesheet" href="../static/css/static.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container pt-5">
		<div class="content">
			<h3>グループ一覧</h3>
			<br>
			<div>
				<a href="/schedule/groups/create">
					<button type="button" class="btn btn-primary">グループを登録</button>
				</a> <a href="/schedule/main_menu">
					<button type="button" class="btn btn-primary">戻す</button>
				</a>
			</div>
			<c:if test="${not empty message}">
				<div class="alert alert-success">${message}</div>
			</c:if>
			<c:choose>
				<c:when test="${listGroups.getTotalElements() > 0}">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th><b> グループ名 </b></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${listGroups.content}" var="group">
								<tr>
									<td>${group.getGroupName()}</td>

									<td><a href="/schedule/groups/edit/${group.getId()}">
											<button type="submit" class="btn btn-primary">編集</button>
									</a> <a href="/schedule/groups/delete/${group.getId()}">
											<button type="submit" class="btn btn-danger">削除</button>
									</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<%@ include file="/WEB-INF/jsp/pagination.jsp"%>
				</c:when>
				<c:otherwise>
					<div>
						<p>グループはありません。</p>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>