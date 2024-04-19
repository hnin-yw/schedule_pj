<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
request.setAttribute("title", "Create schedule");
%>
<%@ include file="/WEB-INF/jsp/content.jsp"%>
<div class="container-fluid">
	<div class="row content">
		<%@ include file="/WEB-INF/jsp/header_menu.jsp"%>
		<%@ include file="/WEB-INF/jsp/nav_bar.jsp"%>
		<div class="col-sm-10 content_body">
			<h2 class="text-center">スケジュールの登録</h2>
			<div class="card">
				<c:if test="${not empty error}">
					<div class="alert alert-danger" role="alert">
						<strong>${error}</strong>
					</div>
				</c:if>
				<form action='/schedule/schedules/save' method='post'>
					<div class="card-body">
						<div class="row">
							<div class="col-sm-9">
								<div class="form-group col-sm-12">
									<label for="scheduleTitle"> スケジュールタイトル :</label> <input
										type="text" id="scheduleTitle" name="scheduleTitle"
										placeholder="スケジュールタイトル" class="form-control ">
								</div>
								<div class="form-group">
									<div class="col-sm-4">
										<label for="startDateTimeString"> スケジュールの開始日時 :</label> <input
											type="text" class="form-control" id="startDateTimeString"
											name="startDateTimeString" placeholder="YYYY-MM-DD HH:mm:ss" />
									</div>
									<div class="col-sm-4">
										<label for="endDateTimeString"> スケジュールの終了日時 :</label> <input
											type="text" class="form-control" id="endDateTimeString"
											name="endDateTimeString" placeholder="YYYY-MM-DD HH:mm:ss" />
									</div>
									<div class="col-sm-12"></div>
								</div>
								<div class="form-group col-sm-12" style="margin-top: 10px;">
									<label for="scheduleThemeColor">スケジュールテーマカラー </label><input
										type="color" class="custom-color_box" id="scheduleThemeColor"
										name="scheduleThemeColor" class="form-control ">
								</div>

								<div class="form-group">
									<div class="col-sm-3" style="margin-top: 10px;">
										<label for="repeatType"> 繰り返しの種類 :</label> <select
											name="repeatType" class="form-control">
											<option value="01">リピートなし</option>
											<option value="02">毎日</option>
											<option value="03">毎週</option>
											<option value="04">毎月</option>
											<option value="05">毎年</option>
										</select>
									</div>
									<div class="col-sm-2" style="margin-top: 30px;">
										<input type="hidden" id="allDayFlg" name="allDayFlg" value="0">
										<input type="checkbox" class="custom-checkbox"
											id="allDayFlgChk" onchange="onAllDayFlgChange(this)">
										<label for="male">一日中</label>
									</div>
									<div class="col-sm-12"></div>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="panel cus_left-panel">
									<label for="otherVisibilityFlg"> スケジュールタイプ</label> <select
										name="eventFlg" class="form-control"
										onchange="onEventChange(this)">
										<option value="1" selected>イベント</option>
										<option value="0">タスク</option>
									</select> <br> <label for="otherVisibilityFlg"> 公開・非公開の表示</label> <select
										name="otherVisibilityFlg" class="form-control">
										<option value="0">公開</option>
										<option value="1" selected>非公開</option>
									</select> <br> <label for="scheduleDisplayFlg"> スケジュール表示 </label> <select
										name="scheduleDisplayFlg" class="form-control">
										<option value="0">無料</option>
										<option value="1"selected">忙しい</option>
									</select>
								</div>
							</div>
						</div>
						<div id="repeateDetail">
							<div class="form-group" style="margin-top: 30px;">
								<div class="col-sm-12">
									<label for="scheduleName"> 繰り返し間隔 :</label> <input
										type="number" id="repeatInterval" name="repeatInterval"
										min="1" max="10" value="1">
								</div>
								<div class="col-sm-3">
									<label for="scheduleName"> 繰り返し間隔タイプ :</label> <select
										name="repeatIntervalType" class="form-control">
										<option value="01">日</option>
										<option value="02">週</option>
										<option value="03">月</option>
										<option value="04">年</option>
									</select>
								</div>
								<div class="col-sm-3" id="repeatDay">
									<label for="repeatDayOfWeek"> 週の繰り返し日 :</label> <select
										name="repeatDayOfWeek" class="form-control">
										<option value="01">日曜日</option>
										<option value="02">月曜日</option>
										<option value="03">火曜日</option>
										<option value="04">水曜日</option>
										<option value="05">木曜日</option>
										<option value="06">金曜日</option>
										<option value="07">土曜日</option>
									</select>
								</div>
								<div class="col-sm-2" id="repeatMonth">
									<label for="scheduleName"> 月の繰り返し日 :</label> <input
										type="number" id="repeatDayOfMonth" name="repeatInterval"
										min="1" max="28" value="1" class="form-control">
								</div>
								<div class="col-sm-2" id="repeatYear">
									<label for="scheduleName"> 年の繰り返し月 :</label> <input
										type="number" id="repeatDayOfMonth" name="repeatInterval"
										min="1" max="28" value="1" class="form-control">
								</div>
								<div class="col-sm-2" id="repeatYear">
									<label for="scheduleName"> 年の繰り返し月 :</label> <input
										type="number" id="repeatMonth" name="repeatMonth" min="1"
										max="12" value="1" class="form-control">
								</div>
								<div class="col-sm-3">
									<label for="repeatUntilDateTimeString"> 繰り返す終了日付 :</label> <input
										type="text" id="repeatUntilDateTimeString"
										name="repeatUntilDateTimeString"
										placeholder="YYYY-MM-DD HH:mm:ss" class="form-control ">
								</div>
							</div>
						</div>
						<div class="form-group col-sm-12">
							<label for="meetLink"> ミーティングのリンク :</label> <input type="text"
								id="meetLink" name="meetLink" placeholder="ミーティングのリンク"
								class="form-control ">
						</div>
						<div class="form-group col-sm-12">
							<label for="location"> ロケーション :</label> <input type="text"
								id="location" name="location" placeholder="ロケーション"
								class="form-control ">
						</div>
						<div class="form-group">
							<div class="col-sm-12">
								<label for="scheduleDescription"> 通知の種類:</label>
							</div>
							<c:forEach var="i" begin="0" end="2">
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
										name="scheduleReminders[${i}].scheduleReminderTime"
										class="form-control">
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
								placeholder="ケジュールの説明" class="form-control"></textarea>
						</div>
						<div class="up-btn-gp col-sm-12">
							<a href="/schedule/schedules">
								<button type="button" class="btn btn-Light">キャンセル</button>
							</a>
							<button type="submit" class="btn btn-primary">登録</button>
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
		document.getElementById("scheduleThemeColor").value = "#FF4013";
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
	function onEventChange(select) {
		var selectedValue = select.value;
		if (selectedValue == '1') {
			document.getElementById("scheduleThemeColor").value = "#FF4013";
		} else {
			document.getElementById("scheduleThemeColor").value = "#00C7FC";
		}
	}
</script>
</html>