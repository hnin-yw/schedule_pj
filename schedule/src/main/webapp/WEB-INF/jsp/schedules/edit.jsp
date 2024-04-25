<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
request.setAttribute("title", "Edit schedule");
%>
<c:set var="allDayFlg" value="${schedule.getAllDayFlg()}" />
<c:set var="repeatType" value="${schedule.getRepeatType()}" />
<%@ include file="/WEB-INF/jsp/content.jsp"%>
<div class="container-fluid">
	<div class="row content">
		<%@ include file="/WEB-INF/jsp/header_menu.jsp"%>
		<%@ include file="/WEB-INF/jsp/nav_bar.jsp"%>
		<div class="col-sm-10 content_body">
			<h2 class="text-center">スケジュールの編集</h2>
			<div class="card">
				<form:form action='/schedule/schedules/update' method='post'
					modelAttribute="schedule">
					<div class="card-body">
						<div class="row">
							<div class="col-sm-9">
								<div class="form-group col-sm-12">
									<label for="scheduleTitle"> スケジュールタイトル :</label> <input
										type="text" id="scheduleTitle" name="scheduleTitle"
										placeholder="スケジュールタイトル"
										value="${schedule.getScheduleTitle()}" class="form-control "><span><form:errors
											path="scheduleTitle" style="color:red" /></span>
								</div>
								<div class="form-group">
									<div class="col-sm-4">
										<label for="startDateTimeString"> スケジュールの開始日時 :</label> <input
											type="text" class="form-control" id="startDateTimeString"
											name="startDateTimeString"
											value="${schedule.getStartDateTimeString()}"
											placeholder="YYYY-MM-DD HH:mm:ss" /><span><form:errors
												path="startDateTimeString" style="color:red" /></span>
									</div>
									<div class="col-sm-4">
										<label for="endDateTimeString"> スケジュールの終了日時 :</label> <input
											type="text" class="form-control" id="endDateTimeString"
											name="endDateTimeString"
											value="${schedule.getEndDateTimeString()}"
											placeholder="YYYY-MM-DD HH:mm:ss" /><span><form:errors
												path="endDateTimeString" style="color:red" /></span>
									</div>
									<div class="col-sm-12">
									    <span style="color:red; display: none;" id="dateCompareError">スケジュールの開始日時は、終了日時よりも前の日時にする必要があります。</span>
									</div>
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
									<div class="col-sm-3">
										<label for="repeatType">繰り返しの種類:</label> <select id="repeatType"
											name="repeatType" onchange="onRepeatTypeChange(this)"
											class="form-control">
											<option value="01"
												<c:if test="${schedule.getRepeatType() == '01'}">selected</c:if>>リピートなし</option>
											<option value="02"
												<c:if test="${schedule.getRepeatType() == '02'}">selected</c:if>>毎日</option>
											<option value="03"
												<c:if test="${schedule.getRepeatType() == '03'}">selected</c:if>>毎月</option>
											<option value="04"
												<c:if test="${schedule.getRepeatType() == '04'}">selected</c:if>>毎年</option>
										</select>
									</div>

									<div class="col-sm-4" id="repeateDetail">
										<label for="repeatUntilDateTimeString"> 繰り返す終了日付 :</label> <input
												type="text" id="repeatUntilDateTimeString"
												name="repeatUntilDateTimeString"
												value="${schedule.getRepeatUntilDateTimeString()}"
												placeholder="YYYY-MM-DD HH:mm:ss" class="form-control " readonly>
									</div>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="panel cus_left-panel">
									<label for="eventFlg"> スケジュールタイプ</label> <input type="hidden"
										name="eventFlg" value="${schedule.getEventFlg()}"> <select
										name="eventFlg" class="form-control"
										onchange="onEventChange(this)" disabled="disabled">
										<option value="1"
											<c:if test="${schedule.getEventFlg()}">selected</c:if>>イベント</option>
										<option value="0"
											<c:if test="${schedule.getEventFlg()}">selected</c:if>>タスク</option>
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
						<div class="form-group col-sm-12 mt-link">
							<label for="meetLink"> ミーティングのリンク :</label> <input type="text"
								id="meetLink" name="meetLink" placeholder="ミーティングのリンク"
								value="${schedule.getMeetLink()}" class="form-control "><span><form:errors
									path="meetLink" style="color:red" /></span>
						</div>
						<div class="form-group col-sm-12">
							<label for="location"> ロケーション :</label> <input type="text"
								id="location" name="location" value="${schedule.getLocation()}"
								placeholder="ロケーション" class="form-control "><span><form:errors
									path="location" style="color:red" /></span>
						</div>
						<div class="form-group" id="divReminder">
							<div class="col-sm-12">
								<label for="scheduleReminders"> 通知の種類:</label>
							</div>
							<c:choose>
								<c:when test="${schedule.getScheduleReminders().size() > 0}">
									<c:forEach var="reminder" items="${schedule.getScheduleReminders()}" varStatus="loop">
										<div class="col-sm-12">
											<input type="hidden" id="id${loop.index}"
												name="scheduleReminders[${loop.index}].id"
												value="${reminder.getId()}" class="form-control">
										</div>
										<div class="col-sm-2">
											<select name="scheduleReminders[${loop.index}].notiMethodFlg"
												class="form-control">
												<option value="0"
													<c:if test="${reminder.getNotiMethodFlg()}">selected</c:if>>メール</option>
												<option value="1"
													<c:if test="${reminder.getNotiMethodFlg()}">selected</c:if>>通知</option>
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
								</c:when>
								<c:otherwise>
									<c:forEach var="i" begin="0" end="1">
										<div class="col-sm-12"></div>
										<div class="col-sm-2">
											<select name="scheduleReminders[${i}].notiMethodFlg"
												class="form-control">
												<option value="0">メール</option>
												<option value="1">通知</option>
											</select>
										</div>
										<div class="col-sm-2">
											<input type="number" id="scheduleReminderTime${i}"
												name="scheduleReminders[${i}].scheduleReminderTime" min="1"
												value="1" class="form-control">
										</div>
										<div class="col-sm-2">
											<select name="scheduleReminders[${i}].scheduleReminderType"
												class="form-control">
												<option value="01">分間</option>
												<option value="02">時間</option>
											</select>
										</div>
										<div class="col-sm-12 mrg_form"></div>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</div>
						<div class="form-group col-sm-12">
							<label for="scheduleDescription"> スケジュールの説明 :</label>
							<textarea id="scheduleDescription" name="scheduleDescription"
								placeholder="ケジュールの説明" class="form-control">${schedule.getScheduleDescription()}</textarea>
							<span><form:errors path="scheduleDescription"
									style="color:red" /></span>

						</div>
						<div class="up-btn-gp col-sm-12">
							<input type='hidden' id='id' class='form-control' name='id'
								value="${schedule.getId()}" /> <a href="/schedule/schedules">
								<button type="button" class="btn btn-Light">キャンセル</button>
							</a>
							<button type="submit" class="btn btn-primary" id="btnEditSchedule">編集</button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
</body>
<script>
	document.addEventListener("DOMContentLoaded", function(event) {
		var allDayFlg = "${allDayFlg}";
		if (allDayFlg === 'true') {
			$('#divReminder').css('display', 'none');
		} else {
			$('#divReminder').css('display', 'block');
		}
		var repeatType = "${repeatType}";
		if (repeatType !== '01') {
			$('#repeateDetail').css('display', 'block');
		} else {
			$('#repeateDetail').css('display', 'none');
		}
	});
	$(document).ready(function() {
		$('#startDateTimeString').datetimepicker({
			format : 'YYYY-MM-DD HH:mm:ss'
		});
		$('#startDateTimeString').on('dp.change',function(e) {
			var selectedDateTime = e.date;
			selectedDateTime.add(1, 'hours');
			var formattedDateTime = selectedDateTime.format('YYYY-MM-DD HH:mm:ss');
			$('#endDateTimeString').val(formattedDateTime);
			
			bindMdRepeatUntilDateTimeString();
		});
		$('#endDateTimeString').datetimepicker({
			format : 'YYYY-MM-DD HH:mm:ss'
		});
		$('#endDateTimeString').on('dp.change',function(e) {
			var startDate = $('#startDateTimeString').data("DateTimePicker").date();
			var endDate = $('#endDateTimeString').data("DateTimePicker").date();
			if (endDate && startDate && endDate.isBefore(startDate)) {
				$('#dateCompareError').show();
				document.getElementById('btnEditSchedule').disabled = true;
			} else {
				$('#dateCompareError').hide();
				document.getElementById('btnEditSchedule').disabled = false;
				
				bindMdRepeatUntilDateTimeString();
			}
		});
		$('#repeatUntilDateTimeString').datetimepicker({
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
			$('#divReminder').css('display', 'none');
		} else {
			$('#allDayFlg').val("0");
			$('#divReminder').css('display', 'block');
		}
	}
	function onEventChange(select) {
		var selectedValue = select.value;
		if (selectedValue == '1') {
			$('#scheduleThemeColor').val("#FF4013");
		} else {
			$('#scheduleThemeColor').val("#00C7FC");
		}
	}
	function onRepeatTypeChange(select) {
		var selectedValue = select.value;
		if (selectedValue != '01') {
			bindMdRepeatUntilDateTimeString();
			$('#repeateDetail').css('display', 'block');
		} else {
			$('#repeatUntilDateTimeString').val("");
			$('#repeateDetail').css('display', 'none');
		}
	}
	function bindMdRepeatUntilDateTimeString(){
		var repeatType = $('#repeatType').val();
		
		var startDate = $('#startDateTimeString').data("DateTimePicker").date();
		if (repeatType == '02') {
			startDate.add(1, 'week');
		} else if (repeatType == '03') {
			startDate.add(3, 'months');
		} else if (repeatType == '04') {
			startDate.add(1, 'years');
		}
		var formattedDateTime = startDate.format('YYYY-MM-DD HH:mm:ss');
		$('#repeatUntilDateTimeString').val(formattedDateTime);
	}
</script>
</html>