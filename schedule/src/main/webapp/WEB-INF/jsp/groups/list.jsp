<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<%
request.setAttribute("title", "List Group");
%>
<%@ include file="/WEB-INF/jsp/var.jsp"%>
<%@ include file="/WEB-INF/jsp/content.jsp"%>
<div class="container-fluid">
	<div class="row content">
		<%@ include file="/WEB-INF/jsp/header_menu.jsp"%>
		<%@ include file="/WEB-INF/jsp/nav_bar.jsp"%>
		<div class="col-sm-10 content_body">
			<h2 class="text-center">グループ一覧</h2>
			<%@ include file="/WEB-INF/jsp/message.jsp"%>
			<div class="col-sm-12">
				<div class="up-btn-gp">
					<sec:authorize access="hasAnyRole('ADMIN', 'USER')">
						<a href="/schedule/groups/create">
							<button type="button" class="btn btn-primary">グループを登録</button>
						</a>
					</sec:authorize>
				</div>

				<table class="table table-bordered" style="maring-top: 10px;">
					<thead class="tbl-header-ft">
						<tr>
							<th>グループ名</th>
							<sec:authorize access="hasAnyRole('ADMIN', 'USER')">
								<th>アクション</th>
							</sec:authorize>
						</tr>
					</thead>
					<tbody class="tbl-body-ft">
						<c:choose>
							<c:when test="${listGroups.getTotalElements() > 0}">
								<c:forEach items="${listGroups.content}" var="group">
									<tr>
										<td>${group.getGroupName()}</td>

										<sec:authorize access="hasAnyRole('ADMIN', 'USER')">
											<td style="width: 20%;"><c:if
													test="${isAdmin || isUser && group.getCreatedBy() == userCode}">
													<a href="/schedule/groups/edit/${group.getId()}">
														<button type="submit" class="btn btn-primary">編集</button>
													</a>
												</c:if>
												<c:if
													test="${isAdmin || isUser && group.getCreatedBy() == userCode}">
													<button type="button" data-gpid="${group.getId()}"
														id="btnGroupDelete" class="btn btn-danger"
														data-toggle="modal" data-target="#deleteConfirmModel">削除</button>
												</c:if></td>
										</sec:authorize>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="2">グループはありません.</td>
								</tr>

							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
				<c:if test="${listGroups.getTotalElements() > 0}">
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
					<div class="modal-body">グループの情報を削除してもよろしいですか?</div>
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
		$('body').on('click', '#btnGroupDelete', function() {
			var groupId = $(this).data('gpid');
			var url = '/schedule/groups/delete/' + groupId;
			document.getElementById('deleteUrl').setAttribute('href', url);
		});

		$("#btnDeleteCancel").click(function(e) {
			document.getElementById('deleteUrl').setAttribute('href', "");
		});
	});
</script>
</html>