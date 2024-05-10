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
				<div>
					<input type="hidden" name="scheduleCode" id="scheduleCode" /> 
					<input type="hidden" id="scheduleId" name="id" />
				</div>	
				<table class="table table-bordered" style="margin-top: 10px;">
				    <thead class="tbl-header-ft">
				        <tr>
				            <th scope="col" rowspan="2" style="width: 2%;"></th>
				            <th scope="col" rowspan="2" style="width: 2%;"></th>
				            <th scope="col" rowspan="2" style="width: 2%;"></th>
				            <th scope="col" colspan="2" style="width: 47%;">イベント開始日時 ~ 終了日時</th>
				            <th scope="col" rowspan="2" style="width: 4%;"><b>作成者</b></th>
				            <th scope="col" colspan="2" style="width: 16%;"><b>繰り返しタイプ</b></th>
				            <th scope="col" rowspan="2" style="width: 8%;"><b>タイプ</b></th>
				            <th scope="col" rowspan="2" style="width: 19%;"></th>
				        </tr>
				        <tr>
				            <th><b>スケジュールタイトル</b></th>
				            <th><b>説明</b></th>
				            <th><b>繰り返す終了日付</b></th>
				        </tr>
				    </thead>
				  <tbody class="tbl-body-ft">
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

										<td rowspan="2" style="width: 2%;"><c:if
												test="${schedule.getUserCode().equals(userCode) && !schedule.getScheduleStatusFlg()}">
												<input type="checkbox" id="chkSelectedIds"
													name="chkSelectedIds" class="custom-checkbox"
													onChange="updateButtonState()" value="${schedule.getId()}" />
											</c:if></td>
										<td rowspan="2" style="width: 2%;"><c:if test="${schedule.getAllDayFlg()}">
													一日中
												</c:if></td>
										<td rowspan="2" style="width: 2%;"><c:choose>
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
										<td colspan="2" style="width: 47%;">${schedule.getScheduleStartDateTime().format(formatter)}
											~ ${schedule.getScheduleEndDateTime().format(formatter)}</td>
										<td rowspan="2" style="width: 4%;">${schedule.getUser().getUserName()}</td>
										<td colspan="2" style="width: 16%;">${schedule.getRepeatType() == '01' ? "リピート無し" : 
										schedule.getRepeatType() == '02' ? "毎日" :
										schedule.getRepeatType() == '03' ? "毎週" :
										schedule.getRepeatType() == '04' ? "毎月" :
										schedule.getRepeatType() == '05' ? "毎年" : ""}</td>
										<td rowspan="2" style="width: 8%;">${schedule.getEventFlg()? "イベント" : "タスク"}</td>
										<td rowspan="2" style="width: 19%;">
										<c:if test="${schedule.getUserCode().equals(userCode)}">
											<c:if test="${!schedule.getScheduleStatusFlg()}">
												<a href="/schedule/schedules/edit/${schedule.getId()}">
													<button type="button" class="btn btn-primary">編集</button>
												</a>
											</c:if>
											<button type="button" data-scheduleid="${schedule.getId()}"
												data-schedulecode="${schedule.getScheduleCode()}"
												id="btnScheduleDelete" class="btn btn-danger"
												data-toggle="modal" data-target="#deleteConfirmModel">削除</button>
											<%-- <c:if test="${!schedule.getEventFlg() && !schedule.getScheduleStatusFlg()}">
												<button type="button" data-scheduleid="${schedule.getId()}"
														id="btnScheduleUpdate" class="btn btn-light"
														data-toggle="modal" data-target="#completeConfirmModel">完了</button>
											</c:if> --%>
										</c:if>
										<c:if test="${!schedule.getUserCode().equals(userCode)}">
											<c:set var="isModifySchedule" value="false" />
											<c:choose>
											    <c:when test="${not empty schedule.attendees}">
											        <c:forEach items="${schedule.attendees}" var="attendee">
											            <c:if test="${attendee.userCode eq userCode}">
											                <c:set var="isModifySchedule" value="true" />
											            </c:if>
											        </c:forEach>
											    </c:when>
											</c:choose>
											<c:if test="${!schedule.getGuestPermissionFlg() || !isModifySchedule}">
												<a href="/schedule/schedules/guest_lists/${schedule.getId()}">ゲストリスト</a>
											</c:if>
											<c:if test="${schedule.getGuestPermissionFlg() && isModifySchedule}">
												<c:if test="${!schedule.getScheduleStatusFlg()}">
													<a href="/schedule/schedules/edit/${schedule.getId()}">
														<button type="button" class="btn btn-primary">編集</button>
													</a>
												</c:if>
											</c:if>
										</c:if>
										</td>
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
									<td colspan="10">スケジュールはありません.</td>
								</tr>

							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
				<c:if test="${listSchedules.getTotalElements() > 0}">
					<%@ include file="/WEB-INF/jsp/pagination.jsp"%>
				</c:if>
				<button type="button" class="btn btn-primary"
					id="btnDownloadSchedule" data-toggle="modal"
					data-target="#downloadConfirmModel" disabled>
					<i class="bi bi-download"></i> ダウンロード
				</button>
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
					<!-- <div class="modal-body">スケジュールの情報を削除してもよろしいですか?</div> -->
					<div class="modal-body">
						<p>スケジュールの情報を削除してもよろしいですか?</p>
						<div class="form-check">
							<input type="radio" class="form-check-input" name="deleteOption"
								value="1" checked> <label class="form-check-label">
								すべてのスケジュール削除 </label>
						</div>
						<div class="form-check">
							<input type="radio" class="form-check-input" name="deleteOption"
								value="0"> <label class="form-check-label">
								このスケジュールのみ削除 </label>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-danger" id="btnDeleteOK">削除</button>
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
						<button type="submit" class="btn btn-danger"
							id="btnScheduleUpdateOK">完了</button>
						<button type="button" class="btn btn-secondary"
							id="btnScheduleUpdateCancel" data-dismiss="modal">キャンセル</button>
					</div>
				</div>
			</div>
		</div>
		<!-- Download Confirm Modal -->
		<div class="modal fade" id="downloadConfirmModel" tabindex="-1"
			role="dialog" aria-labelledby="downloadConfirmModelLabel"
			aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<form action='/schedule/schedules/download' method='post'>
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabel">CSVダウンロードの確認</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body">スケジュールCSVをダウンロードしてもよろしいですか?</div>
						<div class="modal-footer">
							<input type="hidden" name="selectedIds" id="selectedIds" />
							<button type="submit" class="btn btn-danger" id="btnDownloadOK">OK</button>
							<button type="button" class="btn btn-secondary"
								id="btnDownloadCancel" data-dismiss="modal">キャンセル</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
<script>
	$(document).ready(function() {
		$('body').on('click','#btnScheduleDelete',function() {
			var scheduleId = $(this).data('scheduleid');
			$('#scheduleId').val(scheduleId);
			var scheduleCode = $(this).data('schedulecode');
			$('#scheduleCode').val(scheduleCode);
		}); 
		
		$("#btnDeleteOK").click(function(e) {
		    var deleteOption = $("input[name='deleteOption']:checked").val();
		    var scheduleCode = $("#scheduleCode").val();
		    var deleteUrl = "/schedule/schedules/deleteByCode/" + scheduleCode;
		    if (deleteOption == 0) {
		        deleteUrl = "/schedule/schedules/deleteById/" + scheduleCode;
			    scheduleCode = $("#scheduleId").val();
		    }
		    $.ajax({
		        url: deleteUrl,
		        type: "DELETE",
		        data: { scheduleCode: scheduleCode },
		        success: function(response) {
		            $("#deleteConfirmModel").modal("hide");
		            window.alert(response.message);
		            window.location.reload();
					clearTextData();
		        },
		        error: function(xhr, status, error) {
		            $("#message").text("Error deleting data: " + error);
		        }
		    });
		});
		
		$("#btnDeleteCancel").click(function(e) {
			clearTextData();
		});
		
		$('body').on('click','#btnScheduleUpdate',function() {
			var scheduleId = $(this).data('scheduleid');
			$('#scheduleId').val(scheduleId);
		});
		
		$("#btnScheduleUpdateOK").click(function(e) {
		    var scheduleId = $("#scheduleId").val();
			var url = '/schedule/schedules/status/update';
		    $.ajax({
		        url: url,
		        type: "POST",
		        data: { id: scheduleId },
		        success: function(response) {
		            $("#deleteConfirmModel").modal("hide");
		            window.alert(response.message);
		            window.location.reload();
		            clearTextData();
		        },
		        error: function(xhr, status, error) {
		            $("#message").text("Error updating schedule status: " + error);
		        }
		    });
		});
		
		$("#btnScheduleUpdateCancel").click(function(e) {
			clearTextData();
		});
		
		$("#btnDownloadSchedule").click(function(e) {
			var checkboxes = document.querySelectorAll('input[name="chkSelectedIds"]:checked');
			var values = [];
			checkboxes.forEach(function(checkbox) {
				values.push(checkbox.value);
			});
			document.getElementById('selectedIds').value = values.join(',');
			$("#selectedIds").val(values.join(','));
		});
		
		$("#btnDownloadOK").click(function(e) {
			$("#downloadConfirmModel").modal("hide");
			var checkboxes = document.querySelectorAll('input[name="chkSelectedIds"]:checked');
			for (var i = 0; i < checkboxes.length; i++) {
				checkboxes[i].checked = false;
			}
			document.getElementById('btnDownloadSchedule').disabled = true;
		});
		
		$('body').on('click','#btnScheduleGuestList',function() {
			var scheduleId = $(this).data('scheduleid');
			$('#scheduleId').val(scheduleId);
		});
	});
	
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
	function clearTextData() {
		$('#scheduleId').val("");
		$('#scheduleCode').val("");
	}
</script>
</html>