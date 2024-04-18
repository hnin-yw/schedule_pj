<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
request.setAttribute("title", "List User");
%>
<script>
	$(document).ready(function() {
		$("#btnUserDelete").click(function(e) {
			e.preventDefault();
			var groupId = $(this).data('id');
			window.alert(groupId);
			$("#deleteGroupId").val(groupId);
		});
	});
</script>
<%@ include file="/WEB-INF/jsp/content.jsp"%>
<div class="container-fluid">
	<div class="row content">
		<%@ include file="/WEB-INF/jsp/header_menu.jsp"%>
		<%@ include file="/WEB-INF/jsp/nav_bar.jsp"%>
		<div class="col-sm-10 content_body">
			<h2 class="text-center">ユーザ一覧</h2>
			<c:if test="${not empty message}">
				<div class="alert alert-success" role="alert">
					<strong>${message}</strong>
				</div>
			</c:if>

			<div class="col-sm-12">
				<div class="up-btn-gp">
					<a href="/schedule/users/create">
						<button type="button" class="btn btn-primary">ユーザ登録</button>
					</a>
				</div>

				<table class="table table-bordered" style="maring-top: 10px;">
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
						<c:choose>
							<c:when test="${listUsers.getTotalElements() > 0}">
								<c:forEach items="${listUsers.content}" var="user">
									<tr>
										<td>${user.getUserName()}</td>
										<td>${user.getUserFirstName()}</td>
										<td>${user.getUserLastName()}</td>
										<td>${user.getGroup().getGroupName()}</td>

										<td><a href="/schedule/users/edit/${user.getId()}">
												<button type="submit" class="btn btn-primary">編集</button>
										</a>
											<button type="button" data-id="${user.getId()}"
												id="btnUserDelete" class="btn btn-danger"
												data-toggle="modal" data-target="#deleteConfirmModel">
												削除</button> <%-- <a href="/schedule/users/delete/${user.getId()}">
											<button type="submit" class="btn btn-danger">削除</button>
										</a> --%></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="5">ユーザレコードはありません.</td>
								</tr>

							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
				<c:if test="${listUsers.getTotalElements() > 0}">
					<%@ include file="/WEB-INF/jsp/pagination.jsp"%>
				</c:if>
			</div>
		</div>
		<!-- Delete Confirm Modal -->
		<div class="modal fade" id="deleteConfirmModel" tabindex="-1"
			role="dialog" aria-labelledby="deleteConfirmModelLabel"
			aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">情報削除の確認</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">ユーザ情報を削除してもよろしいですか?</div>
					<div class="modal-footer">
						<input type="text" class="form-control" id="deleteGroupId"
							name="deleteGroupId" />
						<button type="button" class="btn btn-primary">削除</button>
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">キャンセル</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>