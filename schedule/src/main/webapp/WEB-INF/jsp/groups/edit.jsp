<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
request.setAttribute("title", "Edit Group");
%>
<%@ include file="/WEB-INF/jsp/content.jsp"%>
<div class="container-fluid">
	<div class="row content">
		<%@ include file="/WEB-INF/jsp/header_menu.jsp"%>
		<%@ include file="/WEB-INF/jsp/nav_bar.jsp"%>
		<div class="col-sm-10 content_body">
			<h2 class="text-center">グループの編集</h2>
			<div class="card">
				<c:if test="${not empty error}">
					<div class="alert alert-danger" role="alert">
						<strong>${error}</strong>
					</div>
				</c:if>
				<div class="card-body">
					<form action='/schedule/groups/update' method='post'>
						<div class="form-group col-sm-12">
							<label for="groupName"> グループ名 :</label> <input type='text'
								name='groupName' id='groupName' class='form-control'
								value="${group.getGroupName()}" />
						</div>
						<input type='hidden' id='id' class='form-control' name='id'
							value="${group.getId()}" />
						<div class="up-btn-gp col-sm-12">
							<a href="/schedule/groups">
								<button type="button" class="btn btn-Light">キャンセル</button>
							</a>
							<button type="submit"
								class="btn btn-primary">編集</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>