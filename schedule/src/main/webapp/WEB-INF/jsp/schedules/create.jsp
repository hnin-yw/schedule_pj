<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
request.setAttribute("title", "Create schedule");
%>
<c:set var="startDateTimeString" value="${schedule.getStartDateTimeString()}" />
<c:set var="startDateTimeString" value="${schedule.getStartDateTimeString()}" />
<c:set var="repeatTypeOfMonth" value="${schedule.getRepeatTypeOfMonth()}" />
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
							<input type="hidden" id="userCode" name="userCode" value="${schedule.getUserCode()}">
							<input type="hidden" id="groupCode" name="groupCode" value="${schedule.getGroupCode()}">
							<input type="hidden" id="allDayFlg" name="allDay" value="${schedule.getAllDay() ? 1 : 0}">
							<input type="hidden" id="guestPermissionFlg" name="guestPermissionFlg" value="${schedule.getGuestPermissionFlg() ? 1 : 0}">
							<input type='hidden' id='id' name='id' value="${schedule.getId()}"/>
							<input type='hidden' id='scheduleCode' name='scheduleCode' value="${schedule.getScheduleCode()}"/> 
							<input type='hidden' id='delFlg' name='delFlg' value="${schedule.getDelFlg() ? 1 : 0}"/> 
							<input type='hidden' id='createdBy' name='createdBy' value="${schedule.getCreatedBy()}"/> 
							<input type='hidden' id='createdAt' name='createdAt' value="${schedule.getCreatedAt()}"/> 
							<input type='hidden' id='updatedBy' name='updatedBy' value="${schedule.getUpdatedBy()}"/> 
							<input type='hidden' id='updatedAt' name='updatedAt' value="${schedule.getUpdatedAt()}"/> 
							<input type='hidden' id='indexArray' name='indexArray' value="${schedule.getIndexArray()}"/> 
							<input type='hidden' id='actualIndexArray' name='actualIndexArray' value="0,1,2,3,4"/> 
						</div>
						<div class="row">
							<div class="col-sm-9">
								<div class="form-group col-sm-12">
									<label for="title"> スケジュールタイトル :</label> <input
										type="text" id="title" name="title"
										placeholder="スケジュールタイトル"
										value="${schedule.getTitle()}" class="form-control "><span><form:errors
											path="title" style="color:red" /></span>
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
									<label for="color">スケジュールテーマカラー </label><input
										type="color" class="custom-color_box" id="color"
										name="color"
										value="${schedule.getColor()}"
										class="form-control ">
								</div>

								<div class="form-group">
									<div class="col-sm-2" style="margin-top: 20px;">
										<input type="checkbox" class="custom-checkbox"
											id="allDayFlgChk" onchange="onAllDayFlgChange(this)"
											<c:if test="${schedule.getAllDay()}">checked</c:if>>
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
									</div>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="panel cus_left-panel">
									<label for="otherVisibilityFlg"> スケジュールタイプ</label> 
									<select
										name="task" class="form-control"
										onchange="onEventChange(this)">
										<option value="1"
											<c:if test="${!schedule.getIsTask()}">selected</c:if>>イベント</option>
										<option value="0"
											<c:if test="${schedule.getIsTask()}">selected</c:if>>タスク</option>
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
						<%-- <div class="form-group" id="divReminder">
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
						</div> --%>
						<div class="form-group col-sm-12">
							<label for="scheduleDescription"> スケジュールの説明 :</label>
							<textarea id="scheduleDescription" name="scheduleDescription"
								placeholder="ケジュールの説明" class="form-control">${schedule.getScheduleDescription()}</textarea>
							<span><form:errors path="scheduleDescription"
									style="color:red" /></span>

						</div>
						<%-- <div class="form-group col-sm-12">
							<span style="color:red">このスケジュールには 5 人のゲストのみを招待できます。</span><br>
							<label for="attendees">ゲスト :</label>
						</div>
						<div class="form-group">
						     <div class="col-sm-4">
							    <input type ="hidden" id="selectCount" value="${not empty schedule.getAttendees() && schedule.getAttendees().size() > 0 ? schedule.getAttendees().size() : 0}" class="form-control" />
						        <select id="attendeesSelect" class="form-control" 
								<c:if test="${not empty schedule.getAttendees() && schedule.getAttendees().size() == 5}">disabled</c:if> >
							        <c:choose>
										<c:when test="${userLists.size() > 0}">
										    <option value="">-- ユーザを選択してください --</option>
										    <c:forEach var="user" items="${userLists}">
											    <c:choose>
							                        <c:when test="${not empty schedule.getAttendees() && schedule.getAttendees().size() > 0}">
							                            <c:set var="disabled" value="false"/>
							                            <c:forEach items="${schedule.getAttendees()}" var="attendee">
							                                <c:if test="${attendee.getUserCode() == user.getUserCode()}">
															    <c:set var="disabled" value="true"/>
															</c:if>
							                            </c:forEach>
							                        </c:when>
							                        <c:otherwise>
							                            <c:set var="disabled" value="false"/>
							                        </c:otherwise>
							                    </c:choose>
										        <option value="${user.getUserCode()}" data-email="${user.getEmail()}" <c:if test="${disabled}">disabled</c:if> >
										            ${user.getEmail()}
										        </option>
										    </c:forEach>
										</c:when>
										<c:otherwise>
											<option value="">-- ユーザを選択してください --</option>
										</c:otherwise>
									</c:choose>
								</select>
								
								<div class="mt-link">
									<input type="checkbox" class="custom-checkbox"
										id="guestPermissionFlgChk" onchange="guestPermissionFlgChange(this)"
										<c:if test="${schedule.getGuestPermissionFlg()}">checked</c:if> disabled>
									<label for="guestPermissionFlgChk">スケジュールの更新</label>
								</div>
						    </div>
						   <div class="col-sm-8" id="attendeesDiv1">
						    	<div id="attendeesDivList" class="list-group">
								<c:choose>
									<c:when test="${not empty schedule.getAttendees() && schedule.getAttendees().size() > 0}">
								    	<c:forEach var="attendee" items="${schedule.getAttendees()}" varStatus="loop">
								    		<c:if test="${attendee.getUserCode() != null}">
									    		<a class="list-group-item list-group-item-action col-sm-6" style="margin-left:3px;margin-bottom:3px;">
									            	${attendee.getUser().getEmail()}
										        	<input type="hidden" data-index="${loop.index}" name="attendees[${loop.index}].user.email" value="${attendee.getUser().getEmail()}" class="form-control">
										        	<input type="hidden" data-index="${loop.index}" name="attendees[${loop.index}].userCode" value="${attendee.getUserCode()}" class="form-control">
										        	<button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button>
									        	</a>
									        </c:if>
								    	</c:forEach>
							    	</c:when>
						    	</c:choose>
						    	</div>
							</div>
						</div>	 --%>
						<div class="form-group up-btn-gp col-sm-12">
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
		var allDayFlg = $('#allDayFlg').val();
		if (allDayFlg == '1') {
			$('#divReminder').css('display', 'none');
		} else {
			$('#divReminder').css('display', 'block');
		}
		var repeatType = $('#repeatType').val();
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
	    guestPermissionFlgChkPermission();
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
		    
		    var actualIndexArray = $("#actualIndexArray").val().split(",");
		    var indexArray = $("#indexArray").val().split(",");
		    var index = 0;
		    for (var i = 0; i < actualIndexArray.length; i++) {
		        if (!indexArray.includes(actualIndexArray[i])) {
		    	    index = i;
		            indexArray.push(actualIndexArray[i]);
		    	    break;
		        }
		    }
		    var indexArrayString = indexArray.join(",");
		    if (indexArrayString.charAt(0) === ',') {
		        indexArrayString = indexArrayString.slice(1);
		    }
		    $("#indexArray").val(indexArrayString);

		    selectCount = selectCount + 1;
		    $("#selectCount").val(selectCount);
		    var selectedOption = $(this).find("option:selected");
		    var selectedValue = selectedOption.val();
		    var selectedEmail = selectedOption.data('email');

		    var html = '<a class="list-group-item list-group-item-action col-sm-6" style="margin-left:3px;margin-bottom:3px;">';
		    html = html + selectedEmail;
		    html = html + '<input type="hidden" data-index="' + index + '" name="attendees[' + index + '].user.email" value="' + selectedEmail + '" class="form-control">';
		    html = html + '<input type="hidden" data-index="' + index + '" name="attendees[' + index + '].userCode" value="' + selectedValue + '" class="form-control"><button type="button" class="close" aria-label="Close">';
		    html = html + '<span aria-hidden="true">&times;</span>';
		    html = html + '</button></a>';
		    var fragment = document.createRange().createContextualFragment(html);
			document.getElementById("attendeesDivList").appendChild(fragment);
			
		    selectedOption.prop('disabled', true);
		    $('#attendeesSelect').val("");
		    if(selectCount >= 5){
		        $('#attendeesSelect').prop('disabled', true);
		    }
		    guestPermissionFlgChkPermission();
		});
		$('#attendeesDivList').on('click', '.close', function() {
		    var selectCount = parseInt($("#selectCount").val());
		    selectCount = selectCount - 1;
		    $("#selectCount").val(selectCount);
	        $('#attendeesSelect').prop('disabled', false);
		    
	        var userCode = $(this).parent().find('input[name*="userCode"]').val();
	        $("#attendeesSelect option[value='" + userCode + "']").prop('disabled', false);
	        
	        var index = $(this).parent().find('input[name*="userCode"]').data('index');
	        var indexArray = $("#indexArray").val().split(",");
	        indexArray = indexArray.filter(function(item) {
	            return item !== index.toString();
	        });
            $("#indexArray").val(indexArray);
	        
		    $(this).parent().remove();
		    guestPermissionFlgChkPermission();
		}); 
	});
	function guestPermissionFlgChkPermission() {
		var selectCount = parseInt($("#selectCount").val());
		if(selectCount > 0){
			$('#guestPermissionFlgChk').prop('disabled', false);
	    }else{
			$('#guestPermissionFlgChk').prop('disabled', true);
		}
	}
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
	function guestPermissionFlgChange(checkbox) {
		if (checkbox.checked) {
			$('#guestPermissionFlg').val("1");
		} else {
			$('#guestPermissionFlg').val("0");
		}
	}
	function onEventChange(select) {
		var selectedValue = select.value;
		if (selectedValue == '1') {
			$('#color').val("#FF4013");
		} else {
			$('#color').val("#00C7FC");
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