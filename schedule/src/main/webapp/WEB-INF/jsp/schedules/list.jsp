<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.time.format.DateTimeFormatter"%>

<%
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
request.setAttribute("title", "List Schedule");
String userCode = "";
Cookie[] c = request.getCookies();
if (c != null) {
	for (Cookie cookie : c) {
		if (cookie.getName().equals("userCode")) {
			userCode = cookie.getValue();
			break;
		}
	}
}
%>

<c:set var="userCode" value="<%=userCode%>" />
<c:set var="formatter" value="<%=formatter%>" />
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
				<form action='/schedule/schedules/download' method='post'>
					<table class="table table-bordered" style="maring-top: 10px;">
						<thead>
							<tr>
								<th rowspan="2" style="width: 5px;"></th>
								<th rowspan="2"></th>
								<th rowspan="2"></th>
								<th colspan="2"><b>イベント開始日時 ~ 終了日時</b></th>
								<th><b>繰り返しタイプ</b></th>
								<th rowspan="2"><b>ステータス</b></th>
								<th rowspan="2"><b>オーナー</b></th>
								<th rowspan="2" style="width: 20px;"></th>
							</tr>
							<tr>
								<th><b>スケジュールタイトル</b></th>
								<th><b>説明</b></th>
								<th><b>繰り返す終了日付</b></th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${listSchedules.getTotalElements() > 0}">
									<c:forEach items="${listSchedules.content}" var="schedule">
										<tr
											<c:choose>
											    <c:when test="${!schedule.getEventFlg() && schedule.getScheduleStatusFlg()}">
											        style="background-color:#919191;"
											    </c:when>
											    <c:otherwise>
											        style="background-color:${schedule.getScheduleThemeColor()};"
											    </c:otherwise>
											</c:choose>>

											<td rowspan="2"><c:if
													test="${schedule.getUserCode().equals(userCode) && !schedule.getScheduleStatusFlg()}">
													<input type="checkbox" name="selectedIds"
														class="custom-checkbox" onChange="updateButtonState()"
														value="${schedule.getId()}" />
												</c:if></td>
											<td rowspan="2"><c:if test="${schedule.getAllDayFlg()}">
													一日中
												</c:if></td>
											<td rowspan="2"><c:choose>
													<c:when
														test="${!schedule.getUserCode().equals(userCode) && schedule.getScheduleDisplayFlg()}">
														<i class="bi bi-circle" style="color: red;"></i> Busy
													</c:when>
													<c:when
														test="${!schedule.getUserCode().equals(userCode) && !schedule.getScheduleDisplayFlg()}">
														<i class="bi bi-circle" style="color: green;"></i> Free
													</c:when>
													<c:otherwise></c:otherwise>
												</c:choose></td>
											<td colspan="2">${schedule.getScheduleStartDateTime().format(formatter)}
												~ ${schedule.getScheduleEndDateTime().format(formatter)}</td>

											<td>${schedule.getRepeatType() == '01'? "リピートなし" : "カスタム"}</td>
											<td rowspan="2">${schedule.getScheduleStatusFlg()? "完了" : ""}</td>
											<td rowspan="2">${schedule.getUser().getUserFirstName()}
												${schedule.getUser().getUserLastName()}</td>
											<td rowspan="2" style="width: 20%;"><c:if
													test="${schedule.getUserCode().equals(userCode) && !schedule.getScheduleStatusFlg()}">
													<a href="/schedule/schedules/edit/${schedule.getId()}">
														<button type="button" class="btn btn-primary">編集</button>
													</a>
													<button type="button" data-scheduleid="${schedule.getId()}"
														id="btnScheduleDelete" class="btn btn-danger"
														data-toggle="modal" data-target="#deleteConfirmModel">削除</button>
													<c:if
														test="${!schedule.getEventFlg() && !schedule.getScheduleStatusFlg()}">
														<button type="button"
															data-scheduleid="${schedule.getId()}"
															id="btnScheduleUpdate" class="btn btn-light"
															data-toggle="modal" data-target="#completeConfirmModel">完了</button>
													</c:if>
												</c:if></td>
										</tr>
										<tr
											<c:choose>
											    <c:when test="${!schedule.getEventFlg() && schedule.getScheduleStatusFlg()}">
											        style="background-color:#919191;"
											    </c:when>
											    <c:otherwise>
											        style="background-color:${schedule.getScheduleThemeColor()};"
											    </c:otherwise>
											</c:choose>>
											<td>${schedule.getScheduleTitle()}</td>
											<td>${schedule.getScheduleDescription()}</td>
											<td>${schedule.getRepeatUntil().format(formatter)}</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="3">スケジュールはありません.</td>
									</tr>

								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
					<c:if test="${listSchedules.getTotalElements() > 0}">
						<%@ include file="/WEB-INF/jsp/pagination.jsp"%>
					</c:if>
					<button type="submit" class="btn btn-primary"
						id="btnDownloadSchedule" disabled>
						<i class="bi bi-download"></i> ダウンロード
					</button>
				</form>
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
			role="dialog" aria-labelledby="completeConfirmModelLabel"
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
							<input type="hidden" id="scheduleId" name="id" />
							<button type="submit" class="btn btn-danger">完了</button>
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
	$(document).ready(
			function() {
				$('body').on(
						'click',
						'#btnScheduleDelete',
						function() {
							var scheduleId = $(this).data('scheduleid');
							var url = '/schedule/schedules/delete/'
									+ scheduleId;
							document.getElementById('deleteUrl').setAttribute(
									'href', url);
						});
				$('body').on(
						'click',
						'#btnScheduleUpdate',
						function() {
							var scheduleId = $(this).data('scheduleid');
							var url = '/schedule/schedules/status/update';
							$('#scheduleId').val(scheduleId);
							document.getElementById('completeConfirmAction')
									.setAttribute('action', url);
						});

				$("#btnDeleteCancel").click(
						function(e) {
							document.getElementById('deleteUrl').setAttribute(
									'href', "");
						});
			});

	/* $("#btnDownloadSchedule").click(function(e) {
		var checkboxes = document.querySelectorAll('input[type="checkbox"]');
		checkboxes.forEach(function(checkbox) {
			checkbox.checked = false;
		});
	}); */
	function updateButtonState() {
		var checkboxes = document.querySelectorAll('input[type="checkbox"]');
		var checked = false;
		checkboxes.forEach(function(checkbox) {
			if (checkbox.checked) {
				checked = true;
			}
		});
		if (checked) {
			document.getElementById('btnDownloadSchedule').disabled = false;
		} else {
			document.getElementById('btnDownloadSchedule').disabled = true;
		}
	}
</script>
</html>