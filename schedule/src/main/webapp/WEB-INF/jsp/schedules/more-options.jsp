<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
request.setAttribute("title", "スケジュールの登録");
%>
<%@ include file="/WEB-INF/jsp/content.jsp"%>
<div class="container-fluid">
	<div class="row content">
		<div class="col-sm-12 content_body">
			<h2 class="text-center">スケジュールの登録</h2>
			<div class="card">
				<form:form action='/schedule/schedules/save' method='post' id="FrmSubmit" modelAttribute="schedule">
					<div class="card-body">
						<input type="hidden" id="guestPermissionFlg"
							name="guestPermissionFlg" /> <input type="hidden" id="allDayFlg"
							name="allDay" value="0" /> <input type='hidden' id='indexArray'
							name='indexArray' /> <input type='hidden' id='delFlg'
							name='delFlg' value="0" /> <input type='hidden'
							id='actualIndexArray' name='actualIndexArray' value="0,1,2,3,4" />
						<div class="row">
							<div class="col-sm-9">
								<div class="form-group col-sm-12">
									<label for="title"> スケジュールタイトル :</label> <input type="text"
										id="title" name="title" placeholder="スケジュールタイトル"
										value="${param.title}" class="form-control "> <span
										id="titleError" class="error" style="color: red;"></span>
								</div>
								<div class="form-group">
									<div class="col-sm-5">
										<label for="start"> スケジュールの開始日時 :</label> <input type="text"
											id="start" name="startDateTimeString" class="form-control"
											value="${param.start}" placeholder="YYYY-MM-DD HH:mm:ss" />
										<span id="startError" class="error" style="color: red;"></span>
									</div>
									<div class="col-sm-5">
										<label for="end"> スケジュールの終了日時 :</label> <input type="text"
											id="end" name="endDateTimeString" class="form-control"
											value="${param.end}" placeholder="YYYY-MM-DD HH:mm:ss" /> <span
											id="endError" class="error" style="color: red;"></span>
									</div>
								</div>
								<div class="form-group col-sm-12" style="margin-top: 10px;">
									<label for="color">スケジュールテーマカラー </label> <input type="color"
										class="custom-color_box" id="color" name="color"
										value="${param.color}" class="form-control ">
								</div>
								<div class="form-group">
									<div class="col-sm-2" style="margin-top: 20px;">
										<input type="checkbox" class="custom-checkbox"
											id="allDayFlgChk"
											<c:if test="${param.allDay == 'true'}">checked</c:if>>
										<label for="allDay">一日中</label>
									</div>
									<div class="col-sm-3">
										<label for="repeatType">繰り返しの種類:</label> <select
											id="repeatType" name="repeatType" class="form-control">
											<option value="01"
												<c:if test="${param.repeatType == '01'}">selected</c:if>>リピートなし</option>
											<option value="02"
												<c:if test="${param.repeatType == '02'}">selected</c:if>>毎日</option>
											<option value="03"
												<c:if test="${param.repeatType == '03'}">selected</c:if>>毎週</option>
											<option value="04"
												<c:if test="${param.repeatType == '04'}">selected</c:if>>毎月</option>
											<option value="05"
												<c:if test="${param.repeatType == '05'}">selected</c:if>>毎年</option>
										</select>
									</div>
									<div class="form-group" id="repeateDetail">
										<div class="col-sm-3" id="repeatDayDiv" style="display: none">
											<label for="repeatDayOfWeek">週の繰り返し日:</label> <select
												id="repeatDayOfWeek" name="repeatDayOfWeek"
												class="form-control">
												<option value="01"
													<c:if test="${param.repeatDayOfWeek == '01'}">selected</c:if>>日曜日</option>
												<option value="02"
													<c:if test="${param.repeatDayOfWeek == '02'}">selected</c:if>>月曜日</option>
												<option value="03"
													<c:if test="${param.repeatDayOfWeek == '03'}">selected</c:if>>火曜日</option>
												<option value="04"
													<c:if test="${param.repeatDayOfWeek == '04'}">selected</c:if>>水曜日</option>
												<option value="05"
													<c:if test="${param.repeatDayOfWeek == '05'}">selected</c:if>>木曜日</option>
												<option value="06"
													<c:if test="${param.repeatDayOfWeek == '06'}">selected</c:if>>金曜日</option>
												<option value="07"
													<c:if test="${param.repeatDayOfWeek == '07'}">selected</c:if>>土曜日</option>
											</select>
										</div>
										<div class="col-sm-3" id="repeatMonthDiv"
											style="display: none">
											<label for="repeatTypeOfMonth">月の繰り返し日:</label> <select
												id="repeatTypeOfMonth" name="repeatTypeOfMonth"
												class="form-control"></select>
										</div>
										<div class="col-sm-4" id="repeatUntilDiv"
											style="display: none">
											<label for="repeatUntil"> 繰り返す終了日付 :</label> <input
												type="text" id="repeatUntil"
												name="repeatUntilDateTimeString"
												placeholder="YYYY-MM-DD HH:mm:ss" class="form-control ">
											<span id="repeatUntilError" class="error" style="color: red;"></span>
										</div>
									</div>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="panel cus_left-panel">
									<label for="isTask"> スケジュールタイプ</label> <select id="isTask"
										name="isTask" class="form-control">
										<option value="1"
											<c:if test="${param.isTask == '1'}">selected</c:if>>イベント</option>
										<option value="0"
											<c:if test="${param.isTask == '0'}">selected</c:if>>タスク</option>
									</select> <br> <label for="otherVisibilityFlg"> 公開・非公開の表示</label> <select
										name="otherVisibilityFlg" class="form-control">
										<option value="0"
											<c:if test="${param.otherVisibilityFlg == '0'}">selected</c:if>>公開</option>
										<option value="1"
											<c:if test="${param.otherVisibilityFlg == '1'}">selected</c:if>>非公開</option>
									</select> <br> <label for="scheduleDisplayFlg"> スケジュール表示 </label> <select
										name="scheduleDisplayFlg" class="form-control">
										<option value="0"
											<c:if test="${param.scheduleDisplayFlg == '0'}">selected</c:if>>無料</option>
										<option value="1"
											<c:if test="${param.scheduleDisplayFlg == '1'}">selected</c:if>>忙しい</option>
									</select>
								</div>
							</div>
							<div class="form-group col-sm-12 mt-link">
								<label for="meetLink"> ミーティングのリンク :</label> <input type="text"
									id="meetLink" name="meetLink" value="${param.meetLink}"
									class="form-control ">
								<span id="meetLinkError" class="error" style="color: red;"></span>
							</div>
							<div class="form-group col-sm-12">
								<label for="location"> ロケーション :</label> <input type="text"
									id="location" name="location" value="${param.location}"
									class="form-control ">
								<span id="locationError" class="error" style="color: red;"></span>
							</div>
							<div class="form-group" id="divReminder">
								<div class="col-sm-12">
									<label for="reminder"> 通知の種類:</label>
								</div>
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
							</div>
							<div class="form-group col-sm-12">
								<label for="scheduleDescription"> スケジュールの説明 :</label>
								<textarea id="scheduleDescription" name="scheduleDescription"
									placeholder="ケジュールの説明" class="form-control">${param.description}</textarea>
								<span id="scheduleDescriptionError" class="error" style="color: red;"></span>
							</div>
							<div class="form-group col-sm-12">
								<span style="color: red">このスケジュールには 5 人のゲストのみを招待できます。</span><br>
								<label for="attendees">ゲスト :</label>
							</div>
							<div class="form-group">
								<div class="col-sm-4">
									<input type="hidden" id="selectCount" value="0"
										class="form-control" /> <select id="attendeesSelect"
										class="form-control">
										<c:choose>
											<c:when test="${userLists.size() > 0}">
												<option value="">-- ユーザを選択してください --</option>
												<c:forEach var="user" items="${userLists}">
													<c:choose>
														<c:when
															test="${not empty schedule.getAttendees() && schedule.getAttendees().size() > 0}">
															<c:set var="disabled" value="false" />
															<c:forEach items="${schedule.getAttendees()}"
																var="attendee">
																<c:if
																	test="${attendee.getUserCode() == user.getUserCode()}">
																	<c:set var="disabled" value="true" />
																</c:if>
															</c:forEach>
														</c:when>
														<c:otherwise>
															<c:set var="disabled" value="false" />
														</c:otherwise>
													</c:choose>
													<option value="${user.getUserCode()}"
														data-email="${user.getEmail()}"
														<c:if test="${disabled}">disabled</c:if>>${user.getEmail()}</option>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<option value="">-- ユーザを選択してください --</option>
											</c:otherwise>
										</c:choose>
									</select>
									<div class="mt-link">
										<input type="checkbox" class="custom-checkbox"
											id="guestPermissionFlgChk" disabled> <label
											for="guestPermissionFlgChk">スケジュールの更新</label>
									</div>
								</div>
								<div class="col-sm-8" id="attendeesDiv1">
									<div id="attendeesDivList" class="list-group"></div>
								</div>
							</div>
							<div class="form-group up-btn-gp col-sm-12">
								<button type="submit" class="btn btn-primary" id="btnSubmit">登録</button>
							</div>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
</body>
<script>
$(document).ready(function() {
	var modifyParamObjec = 0;
	$('#start').datetimepicker({
	    format: 'YYYY-MM-DD HH:mm:ss'
	});

	$('#start').on('dp.change', function(e) {
	    var selectedDateTime = moment(e.date);
	    if (!selectedDateTime.isValid()) {
	    	var formattedDateTime = moment().add(30, 'minutes').format('YYYY-MM-DD HH:mm:ss');
            $('#end').val(formattedDateTime);
	    } else {
			if(!$("#allDayFlgChk").prop('checked')){
			    var end = selectedDateTime.add(30, 'minutes');
			    $('#end').val(end.format('YYYY-MM-DD HH:mm:ss'));
			}else{
		        var start = moment($('#start').val()).startOf('day');
		        var end = start.clone().endOf('day');
		        $('#end').val(end.format('YYYY-MM-DD HH:mm:ss'));
			}
	        
	        modifyParamObjec = 1;
	        bindMdRepeatUntilDateTimeString();
	    }
	});
    
    $('#end').datetimepicker({
        format: 'YYYY-MM-DD HH:mm:ss'
    });
    $('#repeatUntil').datetimepicker({
        format: 'YYYY-MM-DD HH:mm:ss'
    });

    var start = '${param.start}';
    var end = '${param.end}';
    var repeatUntil = '${param.repeatUntil}';

    if (start) {
        $('#start').val(start);
    }
    if (end) {
        $('#end').val(end);
    }
    if (repeatUntil) {
        $('#repeatUntil').val(repeatUntil);
    }

    $('#isTask').change(function() {
        if ($(this).val() == '1') {
            $('#color').val("#039BE5");
        } else {
        	$('#color').val("#D81B60");
        }
    }).trigger('change');

    $('#allDayFlgChk').change(function() {
        if ($(this).is(':checked')) {
			$('#allDayFlg').val("1");
            $('#divReminder').hide();
        } else {
			$('#allDayFlg').val("0");
            $('#divReminder').show();
        }
        onAllDayFlgChange($(this).prop('checked'));
    }).trigger('change');
	function onAllDayFlgChange(checked) {
	    if (checked){
	        var start = moment($('#start').val()).startOf('day');
	        $('#start').val(start.format('YYYY-MM-DD HH:mm:ss'));
	        var end = start.clone().endOf('day');
	        $('#end').val(end.format('YYYY-MM-DD HH:mm:ss'));
	    }
	}
    
    $('#repeatType').change(function() {
        var repeatType = $(this).val();
        $('#repeatDayDiv, #repeatMonthDiv, #repeatUntilDiv').hide();
        if (repeatType === '03') {
            $('#repeatDayDiv').show();
        } else if (repeatType === '04') {
            $('#repeatMonthDiv').show();
        }
        if (repeatType !== '01') {
            $('#repeatUntilDiv').show();
            bindMdRepeatUntilDateTimeString();
        }
    }).trigger('change');

    function bindMdRepeatUntilDateTimeString() {
        var repeatType = $('#repeatType').val();
        var startDate = moment($('#start').val());
        if (!startDate.isValid()) {
            startDate = moment();
        }
        if (repeatType == '02') {
            startDate.add(1, 'months');
        } else if (repeatType == '03') {
            var repeatDayOfWeek = '${param.repeatDayOfWeek}';
            if (modifyParamObjec === 1) {
                var dayOfStartDate = startDate.toDate().getDay() + 1;
                var paddedDay = ("0" + dayOfStartDate).slice(-2);
                $('#repeatDayOfWeek').val(paddedDay);
            }else{
                $('#repeatDayOfWeek').val(repeatDayOfWeek);
            }
            startDate.add(1, 'months');
        } else if (repeatType == '04') {
            removeOption();
            var dateOfStartDate = startDate.toDate().getDate();
            var select = document.getElementById("repeatTypeOfMonth");
            var option1 = document.createElement("option");
            option1.value = "01";
            option1.text = "毎月" + startDate.toDate().getDate() + "日";
            var dayOfStartDate = startDate.toDate().getDay() + 1;
            var paddedDay = ("0" + dayOfStartDate).slice(-2);
            var dayName = getDayName(paddedDay);
            var option2 = document.createElement("option");
            option2.value = "02";
            option2.text = "毎月第4" + dayName;
            var option3 = document.createElement("option");
            option3.value = "03";
            option3.text = "毎月最後の" + dayName;
            var repeatTypeOfMonth = '${param.repeatTypeOfMonth}';
            if (repeatTypeOfMonth === "01") {
            	option1.selected = true;
            } else if (repeatTypeOfMonth === "02") {
            	option2.selected = true;
            } else{
            	option3.selected = true;
            }
            select.add(option1);
            select.add(option2);
            select.add(option3);
            startDate.add(3, 'months');
        } else if (repeatType == '05') {
            startDate.add(3, 'years');
        }
        var formattedDateTime = startDate.format('YYYY-MM-DD HH:mm:ss');
        $('#repeatUntil').val(formattedDateTime);
    }

    function removeOption() {
        var select = document.getElementById("repeatTypeOfMonth");
        select.innerHTML = '';
    }

    function getDayName(type) {
        var dayName = "日曜日";
        if (type == '02') {
            dayName = "月曜日";
        } else if (type == '03') {
            dayName = "火曜日";
        } else if (type == '04') {
            dayName = "水曜日";
        } else if (type == '05') {
            dayName = "木曜日";
        } else if (type == '06') {
            dayName = "金曜日";
        } else if (type == '07') {
            dayName = "土曜日";
        }
        return dayName;
    }
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
	function guestPermissionFlgChkPermission() {
	    var selectCount = parseInt($("#selectCount").val(), 5); 
	    if (selectCount > 0) {
	        $('#guestPermissionFlgChk').prop('checked', false);
	        $('#guestPermissionFlgChk').prop('disabled', false);
	    } else {
	        $('#guestPermissionFlgChk').prop('disabled', true);
	    }
	}
	$('#guestPermissionFlgChk').change(function() {
        if ($(this).is(':checked')) {
        	$('#guestPermissionFlg').val("1");
        } else {
        	$('#guestPermissionFlg').val("0");
        }
    }).trigger('change');

    function clearModalError() {
        $("#titleError").text("");
        $("#startError").text("");
        $("#endError").text("");
        $("#meetLinkError").text("");
        $("#locationError").text("");
        $("#scheduleDescriptionError").text("");
        $("#repeatUntilError").text("");
    }
    $('#FrmSubmit').submit(function(event) {
        event.preventDefault();

        var errors = 0;
        var title = $("#title").val();
        var start = $("#start").val();
        var end = $("#end").val();
        var repeatType = $("#repeatType").val();
        var repeatUntil = $("#repeatUntil").val();
        var meetLink = $("#meetLink").val();
        var location = $("#location").val();
        var scheduleDescription = $("#scheduleDescription").val();
        clearModalError();

        if (title === '') {
            $("#titleError").text("スケジュールタイトルは必須です。");
            errors += 1;
        } else if (title.length > 100) {
            $("#titleError").text("スケジュールタイトルは最大100文字までです。");
            errors += 1;
        }
        if (start === '') {
            $("#startError").text("スケジュールの開始日時は必須です。");
            errors += 1;
        }
        if (end === '') {
            $("#endError").text("スケジュールの終了日時は必須です。");
            errors += 1;
        }
        if (start !== '' && end !== '') {
            if (moment(start).isAfter(moment(end))) {
                $("#endError").text("終了日時は開始日時より後でなければなりません。");
                errors += 1;
            }
        }
        if (end !== '' && repeatUntil !== '' && repeatType !== '01') {
            if (moment(end).isAfter(moment(repeatUntil))) {
                $("#repeatUntilError").text("繰り返す終了日付はスケジュールの終了日時より後でなければなりません。");
                errors += 1;
            }
        }
        if (meetLink !== '' && meetLink.length > 100) {
            $("#meetLinkError").text("ミーティングのリンクは最大100文字までです。");
            errors += 1;
        }
        if (location !== '' && location.length > 100) {
            $("#locationError").text("ロケーションは最大100文字までです。");
            errors += 1;
        }
        if (scheduleDescription !== '' && scheduleDescription.length > 250) {
            $("#scheduleDescriptionError").text("スケジュールの説明は最大250文字までです。");
            errors += 1;
        }

        if (errors > 0) {
            return;
        }

        this.submit();
    });
});
</script>
</html>
