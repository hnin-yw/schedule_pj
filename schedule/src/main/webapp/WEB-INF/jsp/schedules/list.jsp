<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
request.setAttribute("title", "List Schedule");
%>
<%@ include file="/WEB-INF/jsp/content.jsp"%>
<div class="container-fluid">
	<div class="row content">
		<%@ include file="/WEB-INF/jsp/header_menu.jsp"%>
		<%@ include file="/WEB-INF/jsp/nav_bar.jsp"%>
		<div class="col-sm-10 content_body">
			<h2 class="text-center">スケジュール一覧</h2>
			<%@ include file="/WEB-INF/jsp/message.jsp"%>
			<div class="col-sm-12">
				<div class="up-btn-gp">
					<a href="/schedule/schedules/create">
						<button type="button" class="btn btn-primary">スケジュールを登録</button>
					</a>
				</div>

				<table class="table table-bordered" style="maring-top: 10px;">
					<thead>
						<tr>
							<th><b> スケジュールタイトル</b></th>
							<th><b> スケジュール説明 </b></th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${listSchedules.size() > 0}">
								<c:forEach items="${listSchedules}" var="schedule">
									<tr>
										<td>${schedule.getScheduleTitle()}</td>
										<td>${schedule.getScheduleDescription()}</td>

										<td style="width: 20%;"><a
											href="/schedule/schedules/edit/${schedule.getId()}">
												<button type="submit" class="btn btn-primary">編集</button>
										</a>
											<button type="button" data-scheduleid="${schedule.getId()}"
												id="btnScheduleDelete" class="btn btn-danger"
												data-toggle="modal" data-target="#deleteConfirmModel">削除</button>
											<c:if test="${!schedule.getEventFlg()}">
												<button type="button" data-scheduleid="${schedule.getId()}"
													id="btnScheduleUpdate" class="btn btn-light"
													data-toggle="modal" data-target="#completeConfirmModel">完了</button>
											</c:if></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="3">グループはありません.</td>
								</tr>

							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
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
					<div class="modal-body">スケジュールの情報を削除してもよろしいですか?</div>
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
		<!-- Update Confirm Modal -->
		<div class="modal fade" id="completeConfirmModel" tabindex="-1"
			role="dialog" aria-labelledby="deleteConfirmModelLabel"
			aria-hidden="true">
			<form action='' method='post' id="completeConfirmAction">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">完了の確認</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">完了としてもよろしいですか。</div>
					<div class="modal-footer">
							<button type="submit" class="btn btn-danger">完了</button>
						</a>
						<input type="text" id="scheduleId" name="id" />
						<button type="button" class="btn btn-secondary"
							id="btnScheduleUpdate" data-dismiss="modal">キャンセル</button>
					</div>
				</div>
			</div>
			</form>
		</div>
	</div>
</div>
</body>
<script>
	$(document).ready(function() {
		$('body').on('click', '#btnScheduleDelete', function() {
			var scheduleId = $(this).data('scheduleid');
			var url = '/schedule/schedules/delete/' + scheduleId;
			document.getElementById('deleteUrl').setAttribute('href', url);
		});
		$('body').on('click', '#btnScheduleUpdate', function() {
			var scheduleId = $(this).data('scheduleid');
			var url = '/schedule/schedules/status/update';
			$('#scheduleId').val(scheduleId);
			document.getElementById('completeConfirmAction').setAttribute('action', url);
		});

		$("#btnDeleteCancel").click(function(e) {
			document.getElementById('deleteUrl').setAttribute('href', "");
		});
	});
</script>
</html>