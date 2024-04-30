<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
request.setAttribute("title", "Create schedule");
%>
<c:set var="allDayFlg" value="${schedule.getAllDayFlg()}" />
<c:set var="repeatType" value="${schedule.getRepeatType()}" />
<c:set var="startDateTimeString" value="${schedule.getStartDateTimeString()}" />
<c:set var="startDateTimeString" value="${schedule.getStartDateTimeString()}" />
<c:set var="repeatTypeOfMonth" value="${schedule.getRepeatTypeOfMonth()}" />
<c:set var="userCode" value="${userCode}" />
<%@ include file="/WEB-INF/jsp/content.jsp"%>
<div class="container-fluid">
	<div class="row content">
		<%@ include file="/WEB-INF/jsp/header_menu.jsp"%>
		<%@ include file="/WEB-INF/jsp/nav_bar.jsp"%>
		<div class="col-sm-10 content_body">
			<h2 class="text-center">スケジュールの登録</h2>
			<div class="card">
				<form:form action='/schedule/schedules/save' method='post'
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
											type="text" id="startDateTimeString"
											name="startDateTimeString"
											value="${schedule.getStartDateTimeString()}"
											class="form-control" placeholder="YYYY-MM-DD HH:mm:ss" />
											<span id="startDateTimeStringError" style="color:red;display:none;">スケジュール開始日時は必須です。</span>
									</div>
									<div class="col-sm-4">
										<label for="endDateTimeString"> スケジュールの終了日時 :</label> <input
											type="text" id="endDateTimeString" name="endDateTimeString"
											value="${schedule.getEndDateTimeString()}"
											class="form-control" placeholder="YYYY-MM-DD HH:mm:ss" />
											<span id="endDateTimeStringError" style="color:red;display:none;">スケジュール終了日時は必須です。</span>
									</div>
									<div class="col-sm-12">
										<span style="color: red; display: none;" id="dateCompareError">スケジュールの開始日時は、終了日時よりも前の日時にする必要があります。</span>
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
										<input type="hidden" id="allDayFlg" name="allDayFlg" value="${schedule.getAllDayFlg() ? 1 : 0}">
										<input type="checkbox" class="custom-checkbox"
											id="allDayFlgChk" onchange="onAllDayFlgChange(this)"
											<c:if test="${schedule.getAllDayFlg()}">checked</c:if>>
										<label for="male">一日中</label>
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
												<c:if test="${schedule.getRepeatType() == '03'}">selected</c:if>>毎週</option>
											<option value="04"
												<c:if test="${schedule.getRepeatType() == '04'}">selected</c:if>>毎月</option>
											<option value="05"
												<c:if test="${schedule.getRepeatType() == '05'}">selected</c:if>>毎年</option>
										</select>
									</div>
									<div class="form-group" id="repeateDetail">
										<div class="col-sm-3" id="repeatDayDiv" style="display: none">
											<label for="repeatDayOfWeek">週の繰り返し日:</label> <select
														id="repeatDayOfWeek" name="repeatDayOfWeek"
														class="form-control">
														<option value="01" <c:if test="${schedule.getRepeatDayOfWeek() == '01'}">selected</c:if>>日曜日</option>
														<option value="02" <c:if test="${schedule.getRepeatDayOfWeek() == '02'}">selected</c:if>>月曜日</option>
														<option value="03" <c:if test="${schedule.getRepeatDayOfWeek() == '03'}">selected</c:if>>火曜日</option>
														<option value="04" <c:if test="${schedule.getRepeatDayOfWeek() == '04'}">selected</c:if>>水曜日</option>
														<option value="05" <c:if test="${schedule.getRepeatDayOfWeek() == '05'}">selected</c:if>>木曜日</option>
														<option value="06" <c:if test="${schedule.getRepeatDayOfWeek() == '06'}">selected</c:if>>金曜日</option>
														<option value="07" <c:if test="${schedule.getRepeatDayOfWeek() == '07'}">selected</c:if>>土曜日</option>
													</select>
										</div>
										<div class="col-sm-3" id="repeatMonthDiv" style="display: none">
											<label for="repeatTypeOfMonth">月の繰り返し日:</label> <select
														id="repeatTypeOfMonth" name="repeatTypeOfMonth"
														class="form-control"></select>
										</div>
										<div class="col-sm-4">
											<label for="repeatUntilDateTimeString"> 繰り返す終了日付 :</label> <input
													type="text" id="repeatUntilDateTimeString"
													name="repeatUntilDateTimeString"
													value="${schedule.getRepeatUntilDateTimeString()}"
													placeholder="YYYY-MM-DD HH:mm:ss" class="form-control ">
													<span id="repeatUntilDateTimeStringError" style="color:red;display:none;">繰り返す終了日付は必須です。</span>
										</div>
										<div class="col-sm-12" style="margin-top:5px;">
											<span style="color: red; display: none;" id="repeatUntilError">スケジュールの終了日時は、繰り返す終了日時よりも前の日時にする必要があります。</span>
										</div>
										<!-- <div class="col-sm-12" style="margin-bottom:15px;"></div> -->
									</div>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="panel cus_left-panel">
									<label for="otherVisibilityFlg"> スケジュールタイプ</label> <select
										name="eventFlg" class="form-control"
										onchange="onEventChange(this)">
										<option value="1"
											<c:if test="${schedule.getEventFlg()}">selected</c:if>>イベント</option>
										<option value="0"
											<c:if test="${!schedule.getEventFlg()}">selected</c:if>>タスク</option>
									</select> <br> <label for="otherVisibilityFlg"> 公開・非公開の表示</label> <select
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
								id="meetLink" name="meetLink" value="${schedule.getMeetLink()}"
								placeholder="ミーティングのリンク" class="form-control "><span><form:errors
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
								<label for="reminder"> 通知の種類:</label>
							</div>
							<c:choose>
								<c:when test="${not empty schedule.getScheduleReminders()}">
									<c:forEach var="reminder"
										items="${schedule.getScheduleReminders()}" varStatus="loop">
										<div class="col-sm-12">
											<input type="hidden" id="id${loop.index}"
												name="scheduleReminders[${loop.index}].id"
												value="${reminder.getId()}" class="form-control">
										</div>
										<div class="col-sm-2">
											<select name="scheduleReminders[${loop.index}].notiMethodFlg" class="form-control">
											    <option value="0" <c:if test="${!reminder.notiMethodFlg}">selected</c:if>>メール</option>
											    <option value="1" <c:if test="${reminder.notiMethodFlg}">selected</c:if>>通知</option>
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
						<div class="form-group col-sm-12">
						         <span style="color:red">このスケジュールには 5 人のゲストのみを招待できます。</span>
						</div>
						<div class="form-group">
							<%-- <div class="col-sm-4">
							    <input type ="text" id="selectCount" value="${not empty schedule.getAttendees() && schedule.getAttendees().size() > 0 ? schedule.getAttendees().size() : 0}" class="form-control" />
						        <label for="attendees">ゲスト :</label>
						    </div>
						    <div class="col-sm-8">
						        <c:choose>
						            <c:when test="${userLists.size() > 0}">
						                <c:forEach var="user" items="${userLists}" varStatus="loop">
						                    <c:choose>
						                        <c:when test="${schedule.getAttendees().size() > 0}">
						                            <c:set var="checked" value="false"/>
						                            <c:forEach items="${schedule.getAttendees()}" var="attendee">
						                                <c:if test="${attendee.getUserCode() == user.getUserCode()}">
														    <c:set var="checked" value="true"/>
														</c:if>
						                            </c:forEach>
						                        </c:when>
						                        <c:otherwise>
						                            <c:set var="checked" value="false"/>
						                        </c:otherwise>
						                    </c:choose>
						                    <input type="checkbox" class="custom-checkbox" id="attendees${loop.index}" name="attendees[${loop.index}].userCode" value="${ user.getUserCode() }" <c:if test="${checked}">checked</c:if> />
						                    <label for="attendees${loop.index}">${user.getUserName()} (${user.getEmail()})</label>
						                    <c:if test="${userCode == user.getUserCode()}">
											    <span style="color:red">*Organizer</span>
											</c:if><br>
						                </c:forEach>
						            </c:when>
						            <c:otherwise>
						                <!-- Handle case when userLists is empty -->
						            </c:otherwise>
						        </c:choose>
						    </div> --%>
						     <div class="col-sm-4">
							    <input type ="text" id="selectCount" value="${not empty schedule.getAttendees() && schedule.getAttendees().size() > 0 ? schedule.getAttendees().size() : 0}" class="form-control" />
						        <label for="attendees">ゲスト :</label>
								<select id="attendeesSelect" class="form-control">
							        <c:choose>
										<c:when test="${userLists.size() > 0}">
										    <option value="">-- ユーザを選択してください --</option>
										    <c:forEach var="user" items="${userLists}">
										        <option value="${user.getUserCode()}" data-email="${user.getEmail()}">
										            ${user.getEmail()}
										        </option>
										    </c:forEach>
										</c:when>
										<c:otherwise>
											<option value="">-- ユーザを選択してください --</option>
										</c:otherwise>
									</c:choose>
								</select>
						    </div>
						   <div class="col-sm-8" id="attendeesDiv1">
						    	<div id="attendeesDivList" class="list-group">
								<c:choose>
									<c:when test="${not empty schedule.getAttendees() && schedule.getAttendees().size() > 0}">
								    	<c:forEach var="attendee" items="${schedule.getAttendees()}" varStatus="loop">
								    		<a href="#" class="list-group-item list-group-item-action col-sm-6" style="margin-left:3px;margin-bottom:3px;">
								            	${attendee.getUser().getEmail()}
									        	<input type="hidden" name="attendees[${loop.index}].user.email" value="${attendee.getUser().getEmail()}" class="form-control">
									        	<input type="hidden" name="attendees[${loop.index}].userCode" value="${attendee.getUserCode()}" class="form-control">
									        	<button type="button" class="close" aria-label="Close"></button>
									        </a>
								    	</c:forEach>
							    	</c:when>
						    	</c:choose>
						    	</div>
							</div>
						</div>	
						<div class="up-btn-gp col-sm-12">
							<a href="/schedule/schedules">
								<button type="button" class="btn btn-Light">キャンセル</button>
							</a>
							<button type="submit" class="btn btn-primary"
								id="btnCreateSchedule">登録</button>
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
		$('#repeatDayDiv').css('display', 'none');
		$('#repeateDetail').css('display', 'none');
		$('#repeatMonthDiv').css('display', 'none');
		if (repeatType !== '01') {
			$('#repeateDetail').css('display', 'block');
			if (repeatType === '03') {
				$('#repeatDayDiv').css('display', 'block');
			}　else if (repeatType === '04') {
				$('#repeatMonthDiv').css('display', 'block');
				bindRepeartMonthDiv();
			}
		} else{
			$('#repeatUntilDateTimeString').val("");
		}
	});

	$(document).ready(function() {
		$('#startDateTimeString').datetimepicker({
			format : 'YYYY-MM-DD HH:mm:ss'
		});
		$('#startDateTimeString').on('dp.change',function(e) {
			var selectedDateTime = moment(e.date);
			if (!selectedDateTime.isValid()) {
				$('#startDateTimeStringError').show();
				document.getElementById('btnCreateSchedule').disabled = true;
			} else {
				$('#startDateTimeStringError').hide();
				selectedDateTime.add(1, 'hours');
				var formattedDateTime = selectedDateTime.format('YYYY-MM-DD HH:mm:ss');
				$('#endDateTimeString').val(formattedDateTime);
				
				bindMdRepeatUntilDateTimeString();
			}
		});
		$('#endDateTimeString').datetimepicker({
			format : 'YYYY-MM-DD HH:mm:ss'
		});
		$('#endDateTimeString').on('dp.change',function(e) {
			var startDate = $('#startDateTimeString').data("DateTimePicker").date();
			var endDate = $('#endDateTimeString').data("DateTimePicker").date();
			if (endDate == null) {
				$('#endDateTimeStringError').show();
				document.getElementById('btnCreateSchedule').disabled = true;
			} else {
				$('#endDateTimeStringError').hide();
				if (endDate && startDate && endDate.isBefore(startDate)) {
					$('#dateCompareError').show();
					document.getElementById('btnCreateSchedule').disabled = true;
				} else {
					$('#dateCompareError').hide();
					document.getElementById('btnCreateSchedule').disabled = false;
					
					bindMdRepeatUntilDateTimeString();
				}
			}
		});
		$('#repeatUntilDateTimeString').datetimepicker({
			format : 'YYYY-MM-DD HH:mm:ss'
		});
		$('#repeatUntilDateTimeString').on('dp.change',function(e) {
			var endDate = $('#endDateTimeString').data("DateTimePicker").date();
			var repeatUntilDate = $('#repeatUntilDateTimeString').data("DateTimePicker").date();
			if (repeatUntilDate == null) {
				$('#repeatUntilDateTimeStringError').show();
				document.getElementById('btnCreateSchedule').disabled = true;
			} else {
				$('#repeatUntilDateTimeStringError').hide();
				if (repeatUntilDate && endDate && repeatUntilDate.isBefore(endDate)) {
					$('#repeatUntilError').show();
					document.getElementById('btnCreateSchedule').disabled = true;
				} else {
					$('#repeatUntilError').hide();
					document.getElementById('btnCreateSchedule').disabled = false;
				}
			}
		});
		$("#attendeesSelect").change(function() {
		    var selectCount = parseInt($("#selectCount").val());
		    selectCount = selectCount + 1;
		    $("#selectCount").val(selectCount);
		    
		    if(selectCount >= 5){
		        $('#attendeesSelect').prop('disabled', true);
		    }
		    var selectedOption = $(this).find("option:selected");
		    var selectedValue = selectedOption.val();
		    var selectedEmail = selectedOption.data('email');
		    
		    selectedOption.prop('disabled', true);
		    $('#attendeesSelect').val("");
	       
		    var indexCount = selectCount - 1;
		    var html = '<a href="#" class="list-group-item list-group-item-action col-sm-6" style="margin-left:3px;margin-bottom:3px;">';
		    html = html + selectedEmail;
		    html = html + '<input type="hidden" name="attendees[' + indexCount + '].user.email" value="' + selectedEmail + '" class="form-control">';
		    html = html + '<input type="hidden" name="attendees[' + indexCount + '].userCode" value="' + selectedValue + '" class="form-control"><button type="button" class="close" aria-label="Close">';
		    //html = html + '<span aria-hidden="true">&times;</span>';
		    html = html + '</button></a>';
		    var fragment = document.createRange().createContextualFragment(html);
			document.getElementById("attendeesDivList").appendChild(fragment);
		});
		$('#attendeesDivList').on('click', '.close', function() {
		    var selectCount = parseInt($("#selectCount").val());
		    selectCount = selectCount - 1;
		    $("#selectCount").val(selectCount);
	        $('#attendeesSelect').prop('disabled', false);
		    
	        var id = $(this).parent().find('input[type=hidden]').attr('id');
	        $("#attendeesSelect option[value='" + id + "']").prop('disabled', false);
		    $(this).parent().remove();
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
		$('#repeatUntilDateTimeString').val("");
		$('#repeatDayDiv').css('display', 'none');
		$('#repeateDetail').css('display', 'none');
		removeOption();
		$('#repeatMonthDiv').css('display', 'none');
		if (selectedValue != '01') {
			bindMdRepeatUntilDateTimeString();
			$('#repeateDetail').css('display', 'block');
			if (selectedValue == '03') {
				$('#repeatDayDiv').css('display', 'block');
			} else if (selectedValue == '04') {
				$('#repeatMonthDiv').css('display', 'block');
			}
		}
	}
	function bindMdRepeatUntilDateTimeString(){
		var repeatType = $('#repeatType').val();
		var startDate = $('#startDateTimeString').data("DateTimePicker").date();
		if (repeatType == '02') {
			startDate.add(1, 'months');
		} else if(repeatType == '03'){
			var dayOfStartDate = startDate.toDate().getDay() + 1;
			var paddedDay = ("0" + dayOfStartDate).slice(-2);
			$('#repeatDayOfWeek').val(paddedDay);
			startDate.add(1, 'months');
		} else if (repeatType == '04') {
			removeOption();
			var dateOfStartDate = startDate.toDate().getDate();
			var select = document.getElementById("repeatTypeOfMonth");
		    var option1 = document.createElement("option");
		    option1.value = "01";
		    option1.text = "毎月" + startDate.toDate().getDate() + "日";
		    select.add(option1);
		    var dayOfStartDate = startDate.toDate().getDay() + 1;
			var paddedDay = ("0" + dayOfStartDate).slice(-2);
			var dayName = getDayName(paddedDay);
			var option2 = document.createElement("option");
			option2.value = "02";
			option2.text = "毎月第4" + dayName;
		    select.add(option2);
		    var option3 = document.createElement("option");
		    option3.value = "03";
		    option3.text = "毎月最後の" + dayName;
		    select.add(option3);
			startDate.add(3, 'months');
		} else if (repeatType == '05') {
			startDate.add(3, 'years');
		}
		var formattedDateTime = startDate.format('YYYY-MM-DD HH:mm:ss');
		$('#repeatUntilDateTimeString').val(formattedDateTime);
	}
	function bindRepeartMonthDiv(){
		var startDateTimeString = "${startDateTimeString}";
		var repeatTypeOfMonth = "${repeatTypeOfMonth}";
		console.log(repeatTypeOfMonth);
		var startDate = moment(startDateTimeString, "yyyy-MM-DD HH:mm:ss").toDate();
		var dateOfStartDate = startDate.getDate();
		var select = document.getElementById("repeatTypeOfMonth");
	    var option1 = document.createElement("option");
	    option1.value = "01";
	    option1.text = "毎月" + startDate.getDate() + "日";
	    select.add(option1);
	    var dayOfStartDate = startDate.getDay() + 1;
		var paddedDay = ("0" + dayOfStartDate).slice(-2);
		var dayName = getDayName(paddedDay);
		var option2 = document.createElement("option");
		option2.value = "02";
		option2.text = "毎月第4" + dayName;
	    if (repeatTypeOfMonth === '02') {
	    	option2.selected = true;
	    }
	    select.add(option2);
	    var option3 = document.createElement("option");
	    option3.value = "03";
	    option3.text = "毎月最後の" + dayName;
	    if (repeatTypeOfMonth === '03') {
	    	option3.selected = true;
	    }
	    select.add(option3);
	}
	function removeOption() {
		var select = document.getElementById("repeatTypeOfMonth");
		select.innerHTML = '';
	}
	function getDayName(type) {
	  var dayName = "日曜日";
	  if(type == '02'){
		  dayName = "月曜日";
	  } else if(type == '03'){
		  dayName = "火曜日";
	  } else if(type == '04'){
		  dayName = "水曜日";
	  } else if(type == '05'){
		  dayName = "木曜日";
	  } else if(type == '06'){
		  dayName = "金曜日";
	  } else if(type == '07'){
		  dayName = "土曜日";
	  }
	  return dayName;
	}  
    function addSpan() {
    }
</script>
</html>