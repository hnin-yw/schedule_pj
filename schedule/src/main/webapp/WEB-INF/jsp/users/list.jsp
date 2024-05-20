<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
request.setAttribute("title", "List User");
%>
<%@ include file="/WEB-INF/jsp/content.jsp"%>
<div class="container-fluid">
	<div class="row content">
		<%@ include file="/WEB-INF/jsp/header_menu.jsp"%>
		<%@ include file="/WEB-INF/jsp/nav_bar.jsp"%>
		<div class="col-sm-10 content_body">
			<h2 class="text-center">ユーザ一覧</h2>
			<%@ include file="/WEB-INF/jsp/message.jsp"%>
			<div class="col-sm-12">
				<div class="up-btn-gp">
					<a href="/schedule/users/create">
						<button type="button" class="btn btn-primary">ユーザ登録</button>
					</a>
				</div>

				<table class="table table-bordered" style="maring-top: 10px;">
					<thead class="tbl-header-ft">
						<tr>
							<th>ユーザ名</th>
							<th>ユーザの名</th>
							<th>ユーザの姓</th>
							<th>グループ名</th>
							<th>メール</th>
							<th>アクション</th>
						</tr>
					</thead>
					<tbody class="tbl-body-ft">
						<c:choose>
							<c:when test="${listUsers.getTotalElements() > 0}">
								<c:forEach items="${listUsers.content}" var="user">
									<tr>
										<td>${user.getUserName()}</td>
										<td>${user.getUserFirstName()}</td>
										<td>${user.getUserLastName()}</td>
										<td>${user.getGroup().getGroupName()}</td>
										<td>${user.getEmail()}</td>

										<td><a href="/schedule/users/edit/${user.getId()}">
												<button type="button" class="btn btn-primary">編集</button>
										</a>
											<button type="button" data-userid="${user.getId()}"
												id="btnUserDelete" class="btn btn-danger"
												data-toggle="modal" data-target="#deleteConfirmModel">削除</button></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="6">ユーザレコードはありません.</td>
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
					<div class="modal-body">ユーザの情報を削除してもよろしいですか?</div>
					<div class="modal-footer">
						<a href="" id="deleteUrl">
							<button type="submit" class="btn btn-danger">削除</button>
						</a>
						<button type="button" class="btn btn-secondary"
							id="btnDeleteCancel" data-dismiss="modal">キャンセル</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
<script>
	$(document).ready(function() {
		$('body').on('click', '#btnUserDelete', function() {
			var groupId = $(this).data('userid');
			var url = '/schedule/users/delete/' + groupId;
			document.getElementById('deleteUrl').setAttribute('href', url);
		});

		$("#btnDeleteCancel").click(function(e) {
			document.getElementById('deleteUrl').setAttribute('href', "");
		});
	});
</script>
</html>