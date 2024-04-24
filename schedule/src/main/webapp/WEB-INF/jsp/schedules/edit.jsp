<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
request.setAttribute("title", "Edit schedule");
%>
<%@ include file="/WEB-INF/jsp/content.jsp"%>
<div class="container-fluid">
	<div class="row content">
		<%@ include file="/WEB-INF/jsp/header_menu.jsp"%>
		<%@ include file="/WEB-INF/jsp/nav_bar.jsp"%>
		<div class="col-sm-10 content_body">
			<h2 class="text-center">スケジュールの編集</h2>
			<div class="card">
				<c:if test="${not empty error}">
					<div class="alert alert-danger" role="alert">
						<strong>${error}</strong>
					</div>
				</c:if>
				<form action='/schedule/schedules/update' method='post'>
					<div class="card-body">
						<div class="row">
							<div class="col-sm-9">
								<div class="form-group col-sm-12">
									<label for="scheduleTitle"> スケジュールタイトル :</label> <input
										type="text" id="scheduleTitle" name="scheduleTitle"
										placeholder="Schedule Title"
										value="${schedule.getScheduleTitle()}" class="form-control ">
								</div>
								<div class="form-group">
									<div class="col-sm-4">
										<label for="startDateTimeString"> スケジュールの開始日時 :</label> <input
											type="text" class="form-control" id="startDateTimeString"
											name="startDateTimeString"
											value="${schedule.getScheduleStartDateTime()}"
											placeholder="YYYY-MM-DD HH:mm:ss" />
									</div>
									<div class="col-sm-4">
										<label for="endDateTimeString"> スケジュールの終了日時 :</label> <input
											type="text" class="form-control" id="endDateTimeString"
											name="endDateTimeString"
											value="${schedule.getScheduleEndDateTime()}"
											placeholder="YYYY-MM-DD HH:mm:ss" />
									</div>
									<div class="col-sm-12"></div>
								</div>
								<div class="form-group col-sm-12" style="margin-top: 10px;">
									<label for="scheduleThemeColor">スケジュールテーマカラー </label><input
										type="color" class="custom-color_box" id="scheduleThemeColor"
										name="scheduleThemeColor"
										value="${schedule.getScheduleThemeColor()}"
										class="form-control ">
								</div>

								<div class="form-group">
									<div class="col-sm-2" style="margin-top: 20px;">
										<input type="hidden" id="allDayFlg" name="allDayFlg"
											value="${schedule.getAllDayFlg() ? 1 : 0}"> <input
											type="checkbox" class="custom-checkbox" id="allDayFlgChk"
											onchange="onAllDayFlgChange(this)"
											<c:if test="${schedule.getAllDayFlg()}">checked</c:if>>
										<label for="allDayFlgChk">一日中</label>
									</div>
									<div class="col-sm-3" style="margin-top: 5px;">
										<label for="repeatType"> 繰り返しの種類 :</label> <select
											name="repeatType" onchange="onRepeatTypeChange(this)"
											class="form-control">
											<option value="01"
												<c:if test="${schedule.getRepeatType() == '01'}">selected</c:if>>リピートなし</option>
											<option value="02"
												<c:if test="${schedule.getRepeatType() == '02'}">selected</c:if>>カスタム</option>
										</select>
									</div>
									<div class="col-sm-12"></div>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="panel cus_left-panel">
									<label for="eventFlg"> スケジュールタイプ</label> <input type="hidden"
										name="eventFlg" value="${schedule.getEventFlg()}"> <select
										name="eventFlg" class="form-control"
										onchange="onEventChange(this)" disabled="disabled">
										<option value="1"
											<c:if test="${schedule.getEventFlg() == '1'}">selected</c:if>>イベント</option>
										<option value="0"
											<c:if test="${schedule.getEventFlg() == '0'}">selected</c:if>>タスク</option>
									</select> <br> ${reminder.getOtherVisibilityFlg()} <label
										for="otherVisibilityFlg"> 公開・非公開の表示</label> <select
										name="otherVisibilityFlg" class="form-control">
										<option value="0"
											<c:if test="${!schedule.getOtherVisibilityFlg()}">selected</c:if>>公開</option>
										<option value="1"
											<c:if test="${schedule.getOtherVisibilityFlg()}">selected</c:if>>非公開</option>
									</select> <br> <label for="scheduleDisplayFlg"> スケジュール表示 </label> <select
										name="scheduleDisplayFlg" class="form-control">
										<option value="0"
											<c:if test="${!schedule.getScheduleDisplayFlg()}">selected</c:if>>無料</option>
										<option value="1"
											<c:if test="${schedule.getScheduleDisplayFlg()}">selected</c:if>>忙しい</option>
									</select>
								</div>
							</div>
						</div>

						<input type="hidden" id="repeatInterval" name="repeatInterval"
							value="${schedule.getRepeatInterval()}" /> <input type="hidden"
							id="repeatIntervalType" name="repeatIntervalType"
							value="${schedule.getRepeatIntervalType()}" /> <input
							type="hidden" id="repeatDayOfWeek" name="repeatDayOfWeek"
							value="${schedule.getRepeatDayOfWeek()}" /> <input type="hidden"
							id="repeatDayOfMonth" name="repeatDayOfMonth"
							value="${schedule.getRepeatDayOfMonth()}" /> <input
							type="hidden" id="repeatMonth" name="repeatMonth"
							value="${schedule.getRepeatMonth()}" /> <input type="hidden"
							id="repeatUntilDateTimeString" name="repeatUntilDateTimeString"
							value="${schedule.getRepeatUntil()}" />

						<!-- Repeat Confirm Modal -->
						<div class="modal fade" id="repeatConfirmModel" tabindex="-1"
							role="dialog" aria-labelledby="deleteConfirmModelLabel"
							aria-hidden="true">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<h5 class="modal-title" id="exampleModalLabel">繰り返の確認</h5>
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</div>
									<div class="modal-body">
										<div class="form-group">
											<label for="scheduleName">繰り返し間隔:</label> <input
												type="number" id="md_repeatInterval"
												name="md_repeatInterval" min="1" max="10" value="1"
												class="form-control">
										</div>
										<div class="form-group">
											<label for="scheduleName">繰り返し間隔タイプ:</label> <select
												id="md_repeatIntervalType" name="md_repeatIntervalType"
												onchange="repeatIntervalTypeChange(this)"
												class="form-control">
												<option value="01">日</option>
												<option value="02">週</option>
												<option value="03">月</option>
												<option value="04">年</option>
											</select>
										</div>
										<div id="repeatDayDiv" style="display: none">
											<div class="form-group">
												<label for="repeatDayOfWeek">週の繰り返し日:</label> <select
													id="md_repeatDayOfWeek" name="md_repeatDayOfWeek"
													class="form-control">
													<option value="01">日曜日</option>
													<option value="02">月曜日</option>
													<option value="03">火曜日</option>
													<option value="04">水曜日</option>
													<option value="05">木曜日</option>
													<option value="06">金曜日</option>
													<option value="07">土曜日</option>
												</select>
											</div>
										</div>
										<div id="repeatMonthDiv" style="display: none">
											<div class="form-group">
												<label for="scheduleName"> 月の繰り返し日 :</label> <input
													type="number" id="md_repeatDayOfMonth"
													name="md_repeatDayOfMonth" min="1" max="28" value="1"
													class="form-control">
											</div>
										</div>
										<div id="repeatYearDiv" style="display: none">
											<div class="form-group">
												<label for="scheduleName"> 年の繰り返し日 :</label> <input
													type="number" id="md_repeatDayOfMonth"
													name="md_repeatDayOfMonth" min="1" max="28" value="1"
													class="form-control">
											</div>
											<div class="form-group">
												<label for="scheduleName"> 年の繰り返し月 :</label> <input
													type="number" id="md_repeatMonth" name="md_repeatMonth"
													min="1" max="12" value="1" class="form-control">
											</div>
										</div>
										<div class="form-group">
											<label for="repeatUntilDateTimeString"> 繰り返す終了日付 :</label> <input
												type="text" id="md_repeatUntilDateTimeString"
												name="md_repeatUntilDateTimeString"
												placeholder="YYYY-MM-DD HH:mm:ss" class="form-control ">
										</div>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-success" id="repeatDone"
											data-dismiss="modal">OK</button>
										<button type="button" class="btn btn-secondary"
											id="btnDeleteCancel" data-dismiss="modal">キャンセル</button>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group col-sm-12 mt-link">
							<label for="meetLink"> ミーティングのリンク :</label> <input type="text"
								id="meetLink" name="meetLink" placeholder="ミーティングのリンク"
								value="${schedule.getMeetLink()}" class="form-control ">
						</div>
						<div class="form-group col-sm-12">
							<label for="location"> ロケーション :</label> <input type="text"
								id="location" name="location" value="${schedule.getLocation()}"
								placeholder="ロケーション" class="form-control ">
						</div>
						<div class="form-group">
							<div class="col-sm-12">
								<label for="scheduleReminders"> 通知の種類:</label>
							</div>
							<c:forEach var="reminder"
								items="${schedule.getScheduleReminders()}" varStatus="loop">
								<div class="col-sm-12">
									<input type="hidden" id="id${loop.index}"
										name="scheduleReminders[${loop.index}].id"
										value="${reminder.getId()}" class="form-control">
								</div>
								<div class="col-sm-2">
									<select name="scheduleReminders[${loop.index}].notiMethodFlg"
										class="form-control">
										<option value="0"
											<c:if test="${reminder.getNotiMethodFlg() == '0'}">selected</c:if>>メール</option>
										<option value="1"
											<c:if test="${reminder.getNotiMethodFlg() == '1'}">selected</c:if>>通知</option>
									</select>
								</div>
								<div class="col-sm-2">
									<input type="number" id="scheduleReminderTime${loop.index}"
										name="scheduleReminders[${loop.index}].scheduleReminderTime"
										value="${reminder.getScheduleReminderTime()}"
										class="form-control">
								</div>
								<div class="col-sm-2">
									<select
										name="scheduleReminders[${loop.index}].scheduleReminderType"
										class="form-control">
										<option value="01"
											<c:if test="${reminder.getScheduleReminderType() == '01'}">selected</c:if>>分間</option>
										<option value="02"
											<c:if test="${reminder.getScheduleReminderType() == '02'}">selected</c:if>>時間</option>
									</select>
								</div>
								<div class="col-sm-12 mrg_form"></div>
							</c:forEach>
						</div>
						<div class="form-group col-sm-12">
							<label for="scheduleDescription"> スケジュールの説明 :</label>
							<textarea id="scheduleDescription" name="scheduleDescription"
								placeholder="ケジュールの説明" class="form-control">${schedule.getScheduleDescription()}</textarea>
						</div>
						<div class="up-btn-gp col-sm-12">
							<input type='hidden' id='id' class='form-control' name='id'
								value="${schedule.getId()}" /> <a href="/schedule/schedules">
								<button type="button" class="btn btn-Light">キャンセル</button>
							</a>
							<button type="submit" class="btn btn-primary">編集</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
</body>
<script>
	document.addEventListener("DOMContentLoaded", function(event) {
		//document.getElementById("scheduleThemeColor").value = "#FF4013";
	});

	$(document).ready(function() {
		$('#endDateTimeString').datetimepicker({
			format : 'YYYY-MM-DD HH:mm:ss'
		});
		$('#startDateTimeString').datetimepicker({
			format : 'YYYY-MM-DD HH:mm:ss'
		});
		$('#md_repeatUntilDateTimeString').datetimepicker({
			format : 'YYYY-MM-DD HH:mm:ss'
		});
	});
	function onAllDayFlgChange(checkbox) {
		if (checkbox.checked) {
			$('#allDayFlg').val("1");
			var startDateTimeString = $('#startDateTimeString').val();
			var dateParts = startDateTimeString.split(" ");
			$('#startDateTimeString').val(dateParts[0] + " 00:00:00");
			var endDateTimeString = $('#endDateTimeString').val();
			dateParts = endDateTimeString.split(" ");
			$('#endDateTimeString').val(dateParts[0] + " 00:00:00");
		} else {
			$('#allDayFlg').val("0");
		}
	}
	function onRepeatTypeChange(select) {
		var selectedValue = select.value;
		if (selectedValue != '01') {
			$('#repeateDetail').css('display', 'block');
			$('#repeatConfirmModel').modal('show');
		} else {
			this.valClear();
			$('#repeateDetail').css('display', 'none');
			$('#repeatConfirmModel').modal('hide');
		}
	}
	function repeatIntervalTypeChange(select) {
		var selectedValue = select.value;
		if (selectedValue == '02') {
			$('#repeatDayDiv').css('display', 'block');
			$('#repeatMonthDiv').css('display', 'none');
			$('#repeatYearDiv').css('display', 'none');
		} else if (selectedValue == '03') {
			$('#repeatDayDiv').css('display', 'none');
			$('#repeatMonthDiv').css('display', 'block');
			$('#repeatYearDiv').css('display', 'none');
		} else if (selectedValue == '04') {
			$('#repeatDayDiv').css('display', 'none');
			$('#repeatMonthDiv').css('display', 'none');
			$('#repeatYearDiv').css('display', 'block');
		} else {
			$('#repeatDayDiv').css('display', 'none');
			$('#repeatMonthDiv').css('display', 'none');
			$('#repeatYearDiv').css('display', 'none');
		}
	}
	$('#repeatDone').click(
			function() {
				var repeatInterval = $('#md_repeatInterval').val();
				$('#repeatInterval').val(repeatInterval);

				var repeatIntervalType = $('#md_repeatIntervalType').val();
				$('#repeatIntervalType').val(repeatIntervalType);
				if (repeatIntervalType == '02') {
					var repeatDayOfWeek = $('#md_repeatDayOfWeek').val();
					$('#repeatDayOfWeek').val(repeatDayOfWeek);
					$('#repeatDayOfMonth').val("0");
					$('#repeatMonth').val("0");
				} else if (repeatIntervalType == '03') {
					$('#repeatDayOfWeek').val("0");
					var repeatDayOfMonth = $('#md_repeatDayOfMonth').val();
					$('#repeatDayOfMonth').val(repeatDayOfMonth);
					$('#repeatMonth').val("0");
				} else if (repeatIntervalType == '04') {
					$('#repeatDayOfWeek').val("0");
					var repeatDayOfMonth = $('#md_repeatDayOfMonth').val();
					$('#repeatDayOfMonth').val(repeatDayOfMonth);
					var repeatMonth = $('#md_repeatMonth').val();
					$('#repeatMonth').val(repeatMonth);
				} else {
					$('#repeatDayOfWeek').val("0");
					$('#repeatDayOfMonth').val("0");
					$('#repeatMonth').val("0");
				}
				var repeatUntilDateTimeString = $(
						'#md_repeatUntilDateTimeString').val();
				$('#repeatUntilDateTimeString').val(repeatUntilDateTimeString);
				mdlClear();
			});
	$('#repeatDone').click(function() {
		mdlClear();
	});
	function mdlClear() {
		$('#md_repeatInterval').val("");
		$('#md_repeatIntervalType').val("");
		$('#md_repeatDayOfWeek').val("");
		$('#md_repeatDayOfMonth').val("");
		$('#md_repeatMonth').val("");
		$('#md_repeatUntilDateTimeString').val("");
	}
	function valClear() {
		$('#repeatInterval').val("0");
		$('#repeatIntervalType').val("");
		$('#repeatDayOfWeek').val("0");
		$('#repeatDayOfMonth').val("0");
		$('#repeatMonth').val("0");
		$('#repeatUntilDateTimeString').val("");
	}
</script>
</html>