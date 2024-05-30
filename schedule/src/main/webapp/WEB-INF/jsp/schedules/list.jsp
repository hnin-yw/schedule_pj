<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%
request.setAttribute("title", "Schedules");
%>
<%@ include file="/WEB-INF/jsp/var.jsp"%>
<%@ include file="/WEB-INF/jsp/content.jsp"%>
<div id='notificationCount'></div>
<div id='calendar'></div>
<div id="toastContainer" aria-live="polite" aria-atomic="true" style="position: fixed; top: 20px; right: 20px; z-index: 9999;"></div>
<c:set var="isEdit" value="true" />
<c:set var="isDelete" value="true" />
<input type="hidden" id="scheduleId" class="form-control" value=""/>
<input type="hidden" id="scheduleCode" class="form-control" value=""/>
<input type="hidden" id="susMsg" class="form-control" value="${susMsg}" />
<!-- Bootstrap Modal for Event Actions -->
<div class="modal fade" id="eventDetailsModal" tabindex="-1" role="dialog" aria-labelledby="eventModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="eventModalLabel">スケジュールの情報</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="eventTitle">スケジュールタイトル :</label>
                    <span id="eventTitle"></span>
                </div>
                <div class="form-group">
                    <label for="eventStart">スケジュールの開始日時 :</label>
                    <span id="eventStart"></span>
                </div>
                <div class="form-group">
                    <label for="eventEnd">スケジュールの終了日時 :</label>
                    <span id="eventEnd"></span>
                </div>
                <div class="form-group">
                    <label for="eventMeetLink">ミーティングのリンク :</label>
                    <span id="eventMeetLink"></span>
                </div>
           	</div>
            <div class="modal-footer">
             	<a id="eventHrefValue" style="margin-right:5px;">
					<button type="button" class="btn btn-primary" id="btnEventEdit">編集</button>
				</a>
             	<button type="button" class="btn btn-danger" id="btnEventDelete" data-toggle="modal" data-target="#deleteConfirmModel">削除</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">キャンセル</button>
            </div>
        </div>
    </div>
</div>
<!-- スケジュールの登録 Modal -->
<div class="modal fade" id="addScheduleModal" tabindex="-1" role="dialog" aria-labelledby="addScheduleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
        	<security:csrfMetaTags />
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">スケジュールの登録</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="eventForm">
                    <div class="row">
                        <div class="col-sm-9">
                            <div class="form-group">
                                <label for="title"> スケジュールタイトル :</label>
                                <input type="text" id="title" name="title" placeholder="スケジュールタイトル" value="${schedule.title}" class="form-control">
                            	<span id="titleError" class="error" style="color: red;"></span>
                            </div>
                            <div class="form-group row">
                                <div class="col-sm-6">
                                    <label for="start"> スケジュールの開始日時 :</label>
                                    <input type="text" id="start" name="start" class="form-control" placeholder="YYYY-MM-DD HH:mm:ss" />
	                                <span id="startError" class="error" style="color: red;"></span>
	                            </div>
                                <div class="col-sm-6">
                                    <label for="end"> スケジュールの終了日時 :</label>
                                    <input type="text" id="end" name="end" class="form-control" placeholder="YYYY-MM-DD HH:mm:ss" />
	                                <span id="endError" class="error" style="color: red;"></span>
	                            </div>
                            </div>
                            <div class="form-group" style="margin-top: 10px;">
                                <label for="color">スケジュールテーマカラー</label>
                                <input type="color" class="custom-color_box" id="color" name="color" value="#FF4013" class="form-control">
                            </div>
                            <div class="form-group">
                                <div class="col-sm-2" style="margin-top: 20px;">
                                    <input type="checkbox" class="custom-checkbox" id="allDayFlgChk" onchange="onAllDayFlgChange(this)">
                                    <label for="allDayFlgChk">一日中</label>
                                </div>
                                <div class="col-sm-3">
                                    <label for="repeatType">繰り返しの種類:</label>
                                    <select id="repeatType" name="repeatType" class="form-control">
                                        <option value="01">リピートなし</option>
                                        <option value="02">毎日</option>
                                        <option value="03">毎週</option>
                                        <option value="04">毎月</option>
                                        <option value="05">毎年</option>
                                    </select>
                                </div>
                                <div class="form-group" id="repeateDetail">
                                    <div class="col-sm-3" id="repeatDayDiv" style="display: none">
                                        <label for="repeatDayOfWeek">週の繰り返し日:</label>
                                        <select id="repeatDayOfWeek" name="repeatDayOfWeek" class="form-control">
                                            <option value="01">日曜日</option>
                                            <option value="02">月曜日</option>
                                            <option value="03">火曜日</option>
                                            <option value="04">水曜日</option>
                                            <option value="05">木曜日</option>
                                            <option value="06">金曜日</option>
                                            <option value="07">土曜日</option>
                                        </select>
                                    </div>
                                    <div class="col-sm-3" id="repeatMonthDiv" style="display: none">
                                        <label for="repeatTypeOfMonth">月の繰り返し日:</label>
                                        <select id="repeatTypeOfMonth" name="repeatTypeOfMonth" class="form-control"></select>
                                    </div>
                                    <div class="col-sm-4" id="repeatUntilDiv" style="display: none">
                                        <label for="repeatUntil"> 繰り返す終了日付 :</label>
                                        <input type="text" id="repeatUntil" name="repeatUntil" placeholder="YYYY-MM-DD HH:mm:ss" class="form-control">
	                                	<span id="repeatUntilError" class="error" style="color: red;"></span>
	                                </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div class="panel cus_left-panel">
                                <label for="isTask"> スケジュールタイプ</label>
                                <select id="isTask" name="isTask" class="form-control">
                                    <option value="1">イベント</option>
                                    <option value="0">タスク</option>
                                </select>
                                <br>
                                <label for="otherVisibilityFlg"> 公開・非公開の表示</label>
                                <select id="otherVisibilityFlg" name="otherVisibilityFlg" class="form-control">
                                    <option value="0">公開</option>
                                    <option value="1">非公開</option>
                                </select>
                                <br>
                                <label for="scheduleDisplayFlg"> スケジュール表示</label>
                                <select id="scheduleDisplayFlg" name="scheduleDisplayFlg" class="form-control">
                                    <option value="0">無料</option>
                                    <option value="1">忙しい</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group col-sm-12 mt-link">
                            <label for="meetLink"> ミーティングのリンク :</label>
                            <input type="text" id="meetLink" name="meetLink" placeholder="ミーティングのリンク" class="form-control">
                        	<span id="meetLinkError" class="error" style="color: red;"></span>
                        </div>
                        <div class="form-group col-sm-12">
                            <label for="location"> ロケーション :</label>
                            <input type="text" id="location" name="location" placeholder="ロケーション" class="form-control">
                        	<span id="locationError" class="error" style="color: red;"></span>
                        </div>
                        <div class="form-group col-sm-12">
                            <label for="scheduleDescription"> スケジュールの説明 :</label>
                            <textarea id="scheduleDescription" name="scheduleDescription" placeholder="スケジュールの説明" class="form-control"></textarea>
                        	<span id="scheduleDescriptionError" class="error" style="color: red;"></span>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="btnMoreOptions">More Options</button>
                <button type="button" class="btn btn-primary" id="btnAddSchedule">登録</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal" id="btnAddCancel">キャンセル</button>
            </div>
        </div>
    </div>
</div>
<!-- delete ConfirmModel modal -->
<div class="modal fade" id="deleteConfirmModel" tabindex="-1" role="dialog" aria-labelledby="deleteConfirmModelLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
        	<security:csrfMetaTags />
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">情報削除の確認</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p>スケジュールの情報を削除してもよろしいですか?</p>
                <div class="form-check form-group">
                    <input type="radio" class="form-check-input" name="deleteOption" id="deleteAll" value="1" checked>
                    <label class="form-check-label" for="deleteAll">すべてのスケジュール削除</label>
                </div>
                <div class="form-check form-group">
                    <input type="radio" class="form-check-input" name="deleteOption" id="deleteOne" value="0">
                    <label class="form-check-label" for="deleteOne">このスケジュールのみ削除</label>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="btnDeleteOK">OK</button>
                <button type="button" class="btn btn-secondary" id="btnDeleteCancel" data-dismiss="modal">キャンセル</button>
            </div>
        </div>
    </div>
</div>
</body>

<script>
    $(document).ready(function() {
        $('#start').datetimepicker({
            format: 'YYYY-MM-DD HH:mm:ss'
        });
        $('#start').on('dp.change', function(e) {
            var selectedDateTime = moment(e.date);
            if (!selectedDateTime.isValid()) {
            	var formattedDateTime = moment().add(30, 'minutes').format('YYYY-MM-DD HH:mm:ss');
                $('#end').val(formattedDateTime);
            } else {
				onAllDayFlgChange($("#allDayFlgChk").prop('checked'));
                
                bindMdRepeatUntilDateTimeString();
            }
        });

        $('#end').datetimepicker({
            format: 'YYYY-MM-DD HH:mm:ss'
        });
        $('#repeatUntil').datetimepicker({
            format: 'YYYY-MM-DD HH:mm:ss'
        });
    });

    $('#isTask').change(function() {
        if ($(this).val() == '1') {
            $('#color').val("#039BE5");
        } else {
            $('#color').val("#D81B60");
        }
    }).trigger('change');

    $('#allDay').change(function() {
        if ($(this).is(':checked')) {
            $('#divReminder').hide();
        } else {
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
	    } else {
	        var start = moment($('#start').val());
	        var end = start.clone().add(30, 'minutes');
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
        	var dayOfStartDate = startDate.toDate().getDay() + 1;
            var paddedDay = ("0" + dayOfStartDate).slice(-2);
            $('#repeatDayOfWeek').val(paddedDay);
            startDate.add(1, 'months');
        } else if (repeatType == '04') {
            var dateOfStartDate = startDate.date();
            var select = document.getElementById("repeatTypeOfMonth");
            var option1 = document.createElement("option");
            option1.value = "01";
            option1.text = "毎月" + startDate.date() + "日";
            select.add(option1);
            var dayOfStartDate = startDate.day() + 1;
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
        $('#repeatUntil').val(formattedDateTime);
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
	
	$('#btnAddSchedule').click(function(event) {
        event.preventDefault();

        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");

        var errors = 0;
        var title = $("#title").val();
        var start = $("#start").val();
        var end = $("#end").val();
        var color = $("#color").val();
        var allDay = $("#allDayFlgChk").prop('checked') ? 'true' : 'false';
        var repeatType = $("#repeatType").val();
        var repeatDayOfWeek = $("#repeatDayOfWeek").val();
        var repeatTypeOfMonth = $("#repeatTypeOfMonth").val();
        var repeatUntil = $("#repeatUntil").val();
        var isTask = !!$("#isTask").val();
        var otherVisibilityFlg = $("#otherVisibilityFlg").val();
        var scheduleDisplayFlg = $("#scheduleDisplayFlg").val();
        var meetLink = $("#meetLink").val();
        var location = $("#location").val();
        var scheduleDescription = $("#scheduleDescription").val();

        clearAddScheduleModalError();

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

        var schedule = {
            title: title,
            startDateTimeString: start,
            endDateTimeString: end,
            color: color,
        	allDay: allDay,
        	repeatType: repeatType,
        	repeatDayOfWeek: repeatDayOfWeek,
        	repeatTypeOfMonth: repeatTypeOfMonth,
        	repeatUntilDateTimeString: repeatUntil,
        	isTask: isTask,
        	otherVisibilityFlg: otherVisibilityFlg,
        	scheduleDisplayFlg: scheduleDisplayFlg,
        	meetLink: meetLink,
        	location: location,
        	scheduleDescription: scheduleDescription,
        	guestPermissionFlg: false
        };

        $.ajax({
            type: "POST",
            url: "/schedule/schedules/saveData",
            contentType: "application/json",
            data: JSON.stringify(schedule),
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function(response) {
                clearAddScheduleModalError();
                clearAddScheduleModal();
                $("#addScheduleModal").modal("hide");
                setTimeout(function() {
                    showToast(response.message);
                    setTimeout(function() {
                        window.location.reload();
                    }, 300);
                }, 300);
            },
            error: function(error) {
                if (error.responseJSON && error.responseJSON.message) {
                    alert("Error: " + error.responseJSON.message);
                } else {
                    alert(error);
                }
            }
        });
    });

    $('#btnAddCancel').click(function() {
        clearAddScheduleModal();
    });

    function clearAddScheduleModalError() {
        $("#titleError").text("");
        $("#startError").text("");
        $("#endError").text("");
        $("#meetLinkError").text("");
        $("#locationError").text("");
        $("#scheduleDescription").text("");
        $("#repeatUntilError").text("");
    }

    function clearAddScheduleModal() {
        $("#title").val('');
        clearAddScheduleModalError(); // Also clear errors when cancelling
    }

	$('#btnDeleteOK').click(function() {
	    var csrfToken = $("meta[name='_csrf']").attr("content");
	    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
	    var deleteOption = $('input[name="deleteOption"]:checked').val();
	    var scheduleCode = deleteOption == 0 ? $("#scheduleId").val() : $("#scheduleCode").val();
	    var deleteUrl = deleteOption == 0 ? "/schedule/schedules/deleteById/" : "/schedule/schedules/deleteByCode/";
	    
	    $.ajax({
	        url: deleteUrl + scheduleCode,
	        type: "DELETE",
	        beforeSend: function(xhr) {
	            xhr.setRequestHeader(csrfHeader, csrfToken);
	        },
            success: function(response) {
	            $("#deleteConfirmModel").modal("hide");
                setTimeout(function() {
                    showToast(response.message);
                    setTimeout(function() {
                        window.location.reload();
                    }, 300);
                }, 300);
            },
	        error: function(xhr, status, error) {
	            $("#message").text("Error deleting data: " + error);
	        }
	    });
	});
	
	function showToast(message) {
		toastr.options = {
		    closeButton: true,
		    progressBar: true,
		    positionClass: 'toast-bottom-center'
		};
		
	    toastr.success(message);
	}

    document.addEventListener('DOMContentLoaded', function() {
    	var initialEventURL = '/schedule/schedules/api/all';
        var calendarEl = document.getElementById('calendar');
        var calendar = new FullCalendar.Calendar(calendarEl, {
            initialView: 'timeGridWeek',
            headerToolbar: {
                left: 'prev,next today',
                center: 'title',
                right: 'timeGridDay,timeGridWeek,dayGridMonth'
            },
            events: {
                url: initialEventURL
            },
            eventDidMount: function(info) {
                var eventContent = info.el.querySelector('.fc-event-main');
                var icon = document.createElement('i');
                var isOauth2User = ${isOauth2User};
                var userCode = "${userCode}";
	            if(eventContent){
	            	if(!isOauth2User){
	    	        	var isAdmin = ${isAdmin};
	    	        	var groupCode = "${groupCode}";
	                    if (!isAdmin) {
	                        if (userCode !== info.event.extendedProps.userCode) {
	                            if (groupCode === info.event.extendedProps.groupCode) {
	                                var isGroupAdmin = ${isGroupAdmin};
	                                if (!isGroupAdmin) {
	                                    //icon.className = info.event.extendedProps.otherVisibilityFlg ? 'fas fa-globe' : 'fas fa-lock';
	                                    if (!info.event.extendedProps.scheduleDisplayFlg) {
	                                        eventContent.classList.add('event-display-free');
	                                    } else {
	                                        eventContent.classList.add('event-display-busy');
	                                    }
	                                }
	                            } else {
	                                if (info.event.extendedProps.guestPermissionFlg) {
	                                    eventContent.classList.add('event-edit');
	                                } else {
	                                    eventContent.classList.add('event-not-edit');
	                                }
	                                //icon.className = info.event.extendedProps.guestPermissionFlg ? 'fas fa-pencil-alt' : 'fas fa-lock';
	                            }
	                        }
	                	}
	                } else{
	                	if (userCode !== info.event.extendedProps.userCode) {
		                	if (info.event.extendedProps.guestPermissionFlg) {
	                            eventContent.classList.add('event-edit');
	                        } else {
	                            eventContent.classList.add('event-not-edit');
	                        }
	                	}
	                }
                    eventContent.insertBefore(icon, eventContent.firstChild);
                }
            },
            dateClick: function(info) {
                var isViewer = ${isViewer};
                if(!isViewer){
	                var start = moment(info.dateStr);
	                var end = start.clone().add(30, 'minutes');
	                showAddEventForm(start.format('YYYY-MM-DD HH:mm:ss'), end.format('YYYY-MM-DD HH:mm:ss'));
                }
            },
            eventClick: function(info) {
                info.jsEvent.preventDefault();
                var isOauth2User = ${isOauth2User};
                var userCode = "${userCode}";
                if(!isOauth2User){
	                var isAdmin = ${isAdmin};
	                var groupCode = "${groupCode}";
	                var isGroupAdmin = ${isGroupAdmin};
	                if (!isAdmin) {
	                    if (userCode !== info.event.extendedProps.userCode) {
	                        if (groupCode === info.event.extendedProps.groupCode) {
	                            if (!isGroupAdmin) {
	                                $("#btnEventEdit").hide();
	                                $("#btnEventDelete").hide();
	                            }
	                        } else {
	                            if (info.event.extendedProps.guestPermissionFlg) {
	                                $("#btnEventEdit").show();
	                                $("#btnEventDelete").hide();
	                            } else {
	                                $("#btnEventEdit").hide();
	                                $("#btnEventDelete").hide();
	                            }
	                        }
	                    } else {
	                        $("#btnEventEdit").show();
	                        $("#btnEventDelete").show();
	                    }
	                } else {
	                    $("#btnEventEdit").show();
	                    $("#btnEventDelete").show();
	                }
                } else {
                	if (userCode !== info.event.extendedProps.userCode) {
                        if (info.event.extendedProps.guestPermissionFlg) {
                            $("#btnEventEdit").show();
                            $("#btnEventDelete").hide();
                        } else {
                            $("#btnEventEdit").hide();
                            $("#btnEventDelete").hide();
                        }
                    } else {
	                    $("#btnEventEdit").show();
	                    $("#btnEventDelete").show();
	                }
	            }
                const scheduleId = info.event.id;
                $('#scheduleId').val(scheduleId);
                const scheduleCode = info.event.extendedProps.scheduleCode;
                $('#scheduleCode').val(scheduleCode);
                const customUrl = `/schedule/schedules/edit/` + scheduleId;

                $('#eventTitle').text(info.event.title);
                $('#eventStart').text(moment(info.event.start).format('YYYY-MM-DD HH:mm:ss'));
                $('#eventMeetLink').text(info.event.eventMeetLink);

                if (info.event.allDay) {
                    var end = moment(info.event.start).endOf('day');
                    $('#eventEnd').text(end.format('YYYY-MM-DD HH:mm:ss'));
                } else {
                    $('#eventEnd').text(moment(info.event.end).format('YYYY-MM-DD HH:mm:ss'));
                }

                $('#eventHrefValue').attr('href', customUrl);
                $('#eventDetailsModal').modal('show');
            }
        });

        calendar.render();

        function showAddEventForm(start, end) {
            document.getElementById('start').value = start;
            document.getElementById('end').value = end;
            $("#addScheduleModal").modal("show");
        }

        document.getElementById('btnMoreOptions').addEventListener('click', function(event) {
            var title = document.getElementById('title').value;
            var start = document.getElementById('start').value;
            var end = document.getElementById('end').value;
            var color = document.getElementById('color').value;
            var allDay = document.getElementById('allDayFlgChk').checked;
            var repeatType = document.getElementById('repeatType').value;
            var repeatDayOfWeek = document.getElementById('repeatDayOfWeek').value;
            var repeatTypeOfMonth = document.getElementById('repeatTypeOfMonth').value;
            var repeatUntil = document.getElementById('repeatUntil').value;
            var meetLink = document.getElementById('meetLink').value;
            var location = document.getElementById('location').value;
            var description = document.getElementById('scheduleDescription').value;
            var isTask = document.getElementById('isTask').value;
            var otherVisibilityFlg = document.getElementById('otherVisibilityFlg').value;
            var scheduleDisplayFlg = document.getElementById('scheduleDisplayFlg').value;

            var queryParams = new URLSearchParams({
                title: title,
                start: start,
                end: end,
                color: color,
                allDay: allDay,
                repeatType: repeatType,
                repeatDayOfWeek: repeatDayOfWeek,
                repeatTypeOfMonth: repeatTypeOfMonth,
                repeatUntil: repeatUntil,
                meetLink: meetLink,
                location: location,
                description: description,
                isTask: isTask,
                otherVisibilityFlg: otherVisibilityFlg,
                scheduleDisplayFlg: scheduleDisplayFlg,
                formMethod: 'create'
            });

            window.location.href = '/schedule/schedules/more-options?' + queryParams.toString();
        });
        
		if($("#susMsg").val() !== ""){
			setTimeout(function() {
                showToast($("#susMsg").val());
            }, 300);
		}
    });
</script>
</html>
