<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
request.setAttribute("title", "Create Group");
%>
<%@ include file="/WEB-INF/jsp/content.jsp"%>
<div class="container-fluid">
	<div class="row content">
		<%@ include file="/WEB-INF/jsp/header_menu.jsp"%>
		<%@ include file="/WEB-INF/jsp/nav_bar.jsp"%>
		<div class="col-sm-10 content_body">
			<h2 class="text-center">グループの登録</h2>
			<div class="card">
				<c:if test="${not empty error}">
					<div class="alert alert-danger" role="alert">
						<strong>${error}</strong>
					</div>
				</c:if>
				<div class="card-body">
					<form action='/schedule/schedules/save' method='post'>
						<table class="table-border">
							<tr>
								<td>スケジュールタイプ :</td>
								<td><input type="radio" id="event" name="eventFlg"
									value="1" onChange="handleRadioChange(this)"> <label
									for="male">イベント </label> <input type="radio" id="task"
									name="eventFlg" value="0" onChange="handleRadioChange(this)">
									<label for="female">タスク</label></td>
							</tr>
							<tr>
								<td>スケジュールタイトル :</td>
								<td><input type="text" id="scheduleTitle"
									name="scheduleTitle" placeholder="Schedule Title"
									class="form-control "></td>
							</tr>
							<tr>
								<td>スケジュールの開始日時 :</td>
								<td><input type="text" id="startDateTimeString"
									name="startDateTimeString" class="form-control "></td>
							</tr>
							<tr>
								<td>スケジュールの終了日時 :</td>
								<td><input type="text" id="endDateTimeString"
									name="endDateTimeString" class="form-control "></td>
							</tr>
							<tr>
								<td>一日中 :</td>
								<td><input type="checkbox" id="allDayFlg" name="allDayFlg"
									class="form-control "></td>
							</tr>
							<tr>
								<td>繰り返しの種類 :</td>
								<td><select name="repeatType" class="form-control">
										<option value="01">リピートなし</option>
										<option value="02">毎日</option>
										<option value="03">毎週</option>
										<option value="04">毎月</option>
										<option value="05">毎年</option>
								</select></td>
							</tr>
							<tr>
								<td>繰り返す終了日付 :</td>
								<td><input type="text" id="repeatUntilDateTimeString"
									name="repeatUntilDateTimeString" class="form-control "></td>
							</tr>
							<tr>
								<td>繰り返し間隔 :</td>
								<td><input type="number" id="repeatInterval"
									name="repeatInterval"
									class="form-control "></td>
							</tr>
							<tr>
								<td>繰り返し間隔タイプ :</td>
								<td><select name="repeatIntervalType" class="form-control">
										<option value="01">日</option>
										<option value="02">週</option>
										<option value="03">月</option>
										<option value="04">年</option>
								</select></td>
							</tr>
							<tr>
								<td>週の繰り返し日 :</td>
								<td><select name="repeatDayOfWeek" class="form-control">
										<option value="01">日曜日</option>
										<option value="02">月曜日</option>
										<option value="03">火曜日</option>
										<option value="04">水曜日</option>
										<option value="05">木曜日</option>
										<option value="06">金曜日</option>
										<option value="07">土曜日</option>
								</select></td>
							</tr>
							<tr>
								<td>月の繰り返し日 :</td>
								<td><input type="text" id="repeatDayOfMonth"
									name="repeatDayOfMonth" class="form-control "></td>
							</tr>
							<tr>
								<td>繰り返し月 :</td>
								<td><input type="text" id="repeatMonth" name="repeatMonth"
									class="form-control "></td>
							</tr>
							<tr>
								<td>スケジュール表示フラグ :</td>
								<td><input type="checkbox" id="scheduleDisplayFlg"
									name="scheduleDisplayFlg" class="form-control "></td>
							</tr>
							<tr>
								<td>ロケーション :</td>
								<td><input type="text" id="location" name="location"
									class="form-control "></td>
							</tr>
							<tr>
								<td>リマインダー :</td>
								<td><div>
										通知の種類 <input type="radio" id="mail"
											name="scheduleReminders[0].notiMethodFlg" value="1"
											onChange="handleRadioChange(this)"> <label for="mail">メール
										</label> <input type="radio" id="event"
											name="scheduleReminders[0].notiMethodFlg" value="0"
											onChange="handleRadioChange(this)" checked> <label
											for="event">通知</label> <input type="number"
											id="scheduleReminders[0].scheduleReminderTime"
											name="scheduleReminders[0].scheduleReminderTime"
											class="form-control "> <select
											name="scheduleReminders[0].scheduleReminderType"
											class="form-control">
											<option value="01">分間</option>
											<option value="02">時間</option>
										</select>
									</div>
									<div>
										通知の種類 <input type="radio" id="mail"
											name="scheduleReminders[1].notiMethodFlg" value="1"
											onChange="handleRadioChange(this)" checked> <label
											for="mail">メール </label> <input type="radio" id="event"
											name="scheduleReminders[1].notiMethodFlg" value="0"
											onChange="handleRadioChange(this)" checked> <label
											for="event">通知</label> <input type="number"
											id="scheduleReminders[1].scheduleReminderTime"
											name="scheduleReminders[1].scheduleReminderTime"
											class="form-control "> <select
											name="scheduleReminders[1].scheduleReminderType"
											class="form-control">
											<option value="01">分間</option>
											<option value="02">時間</option>
										</select>
									</div></td>
							</tr>
							<tr>
								<td>ミーティングのリンク :</td>
								<td><input type="text" id="meetLink" name="meetLink"
									class="form-control "></td>
							</tr>
							<tr>
								<td>スケジュールの説明 :</td>
								<td><input type="text" id="scheduleDescription"
									name="scheduleDescription" class="form-control "></td>
							</tr>
							<tr>
								<td>スケジュールテーマカラー :</td>
								<td><input type="color" id="scheduleThemeColor"
									name="scheduleThemeColor" class="form-control "></td>
							</tr>
							<tr>
								<td>公開・非公開の表示フラグ :</td>
								<td><input type="checkbox" id="otherVisibilityFlg"
									name="otherVisibilityFlg" class="form-control "></td>
							</tr>
							<tr>
								<td>スケジュールタスクフラグ :</td>
								<td><input type="checkbox" id="scheduleStatusFlg"
									name="scheduleStatusFlg" class="form-control "></td>
							</tr>
							<tr>
								<td></td>
								<td>
									<button type="submit" class="btn btn-primary">Save
										User</button> <a href="/schedule/users">
										<button type="button" class="btn btn-primary">Cancel</button>
								</a>
								</td>
							</tr>

						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
</body>

<script>
	document.addEventListener("DOMContentLoaded", function(event) {
		document.getElementById("event").checked = true;
	});
	$(document).ready(function () {
        $('#endDateTimeString').datetimepicker({
            format: 'YYYY-MM-DD HH:mm:ss'
        });
        $('#startDateTimeString').datetimepicker({
            format: 'YYYY-MM-DD HH:mm:ss'
        });
        $('#repeatUntilDateTimeString').datetimepicker({
            format: 'YYYY-MM-DD HH:mm:ss'
        });
    });
</script>
</html>