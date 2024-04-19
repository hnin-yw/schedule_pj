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
											value="${schedule.getStartDateTimeString()}"
											placeholder="YYYY-MM-DD HH:mm:ss" />
									</div>
									<div class="col-sm-4">
										<label for="endDateTimeString"> スケジュールの終了日時 :</label> <input
											type="text" class="form-control" id="endDateTimeString"
											name="endDateTimeString"
											value="${schedule.getEndDateTimeString()}"
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
									<div class="col-sm-3" style="margin-top: 10px;">
										<label for="repeatType"> 繰り返しの種類 :</label> <select
											name="repeatType" class="form-control">
											<option value="01"
												<c:if test="${schedule.getRepeatType() == '01'}">selected</c:if>>リピートなし</option>
											<option value="02"
												<c:if test="${schedule.getRepeatType() == '02'}">selected</c:if>>毎日</option>
											<option value="03"
												<c:if test="${schedule.getRepeatType() == '03'}">selected</c:if>>毎週</option>
											<option value="04"
												<c:if test="${schedule.getRepeatType() == '04'}">selected</c:if>>毎月</option>
											<option value="05"
												<c:if test="${schedule.getRepeatType() == '05'}">selected</c:if>>毎年</option>
										</select>
									</div>
									<div class="col-sm-2" style="margin-top: 30px;">
										<input type="hidden" id="allDayFlg" name="allDayFlg"
											value="${schedule.getAllDayFlg() ? 1 : 0}"> <input
											type="checkbox" class="custom-checkbox" id="allDayFlgChk"
											onchange="onAllDayFlgChange(this)"
											<c:if test="${schedule.getAllDayFlg()}">checked</c:if>>
										<label for="allDayFlgChk">一日中</label>


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
						<div id="repeateDetail">
							<div class="form-group" style="margin-top: 30px;">
								<div class="col-sm-12">
									<label for="scheduleName"> 繰り返し間隔 :</label> <input
										type="number" id="repeatInterval" name="repeatInterval"
										value="${schedule.getRepeatInterval()}" min="1" max="10"
										value="1">
								</div>
								<div class="col-sm-3">
									<label for="scheduleName"> 繰り返し間隔タイプ :</label> <select
										name="repeatIntervalType" class="form-control">
										<option value="01"
											<c:if test="${schedule.getRepeatType() == '01'}">selected</c:if>>日</option>
										<option value="02"
											<c:if test="${schedule.getRepeatType() == '02'}">selected</c:if>>週</option>
										<option value="03"
											<c:if test="${schedule.getRepeatType() == '03'}">selected</c:if>>月</option>
										<option value="04"
											<c:if test="${schedule.getRepeatType() == '04'}">selected</c:if>>年</option>
									</select>
								</div>
								<div class="col-sm-3" id="repeatDay">
									<label for="repeatDayOfWeek"> 週の繰り返し日 :</label> <select
										name="repeatDayOfWeek" class="form-control">
										<option value="01"
											<c:if test="${schedule.getRepeatType() == '01'}">selected</c:if>>日曜日</option>
										<option value="02"
											<c:if test="${schedule.getRepeatType() == '02'}">selected</c:if>>月曜日</option>
										<option value="03"
											<c:if test="${schedule.getRepeatType() == '03'}">selected</c:if>>火曜日</option>
										<option value="04"
											<c:if test="${schedule.getRepeatType() == '04'}">selected</c:if>>水曜日</option>
										<option value="05"
											<c:if test="${schedule.getRepeatType() == '05'}">selected</c:if>>木曜日</option>
										<option value="06"
											<c:if test="${schedule.getRepeatType() == '06'}">selected</c:if>>金曜日</option>
										<option value="07"
											<c:if test="${schedule.getRepeatType() == '07'}">selected</c:if>>土曜日</option>
									</select>
								</div>
								<div class="col-sm-2" id="repeatMonth">
									<label for="repeatDayOfMonth"> 月の繰り返し日 :</label> <input
										type="number" id="repeatDayOfMonth" name="repeatInterval"
										value="${schedule.getRepeatInterval()}" min="1" max="28"
										value="1" class="form-control">
								</div>
								<div class="col-sm-2" id="repeatYear">
									<label for="repeatDayOfMonth"> 年の繰り返し月 :</label> <input
										type="number" id="repeatDayOfMonth" name="repeatDayOfMonth"
										value="${schedule.getRepeatDayOfMonth()}" min="1" max="28"
										value="1" class="form-control">
								</div>
								<div class="col-sm-2" id="repeatYear">
									<label for="repeatMonth"> 年の繰り返し月 :</label> <input
										type="number" id="repeatMonth" name="repeatMonth" min="1"
										value="${schedule.getRepeatMonth()}" max="12" value="1"
										class="form-control">
								</div>
								<div class="col-sm-3">
									<label for="repeatUntilDateTimeString"> 繰り返す終了日付 :</label> <input
										type="text" id="repeatUntilDateTimeString"
										name="repeatUntilDateTimeString"
										value="${schedule.getRepeatUntilDateTimeString()}"
										placeholder="YYYY-MM-DD HH:mm:ss" class="form-control ">
								</div>
							</div>
						</div>
						<div class="form-group col-sm-12">
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
								<div class="col-sm-12"></div>
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
								placeholder="ケジュールの説明"
								value="${schedule.getScheduleDescription()}"
								class="form-control"></textarea>
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
		$('#repeatUntilDateTimeString').datetimepicker({
			format : 'YYYY-MM-DD HH:mm:ss'
		});
	});
	function onAllDayFlgChange(checkbox) {
		if (checkbox.checked) {
			$('#allDayFlg').val("1");
		} else {
			$('#allDayFlg').val("0");
		}
	}
</script>
</html>