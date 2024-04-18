<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String requestURL = request.getRequestURL().toString();
	String gpClass = "";
	String userClass = "";
	String scheClass = "";
	if (requestURL.contains("groups")) {
		gpClass = "active";
	} else if (requestURL.contains("users")) {
		userClass = "active";
	} else if (requestURL.contains("schedules")) {
		scheClass = "active";
	}
%>
<div class="col-sm-2 sidenav">
    <ul class="nav nav-pills nav-stacked">
        <li class="<%= userClass %>"><a href="/schedule/users">ユーザ</a></li>
        <li class="<%= gpClass %>"><a href="/schedule/groups">グループ</a></li>
        <li class="<%= scheClass %>"><a href="/schedule/schedules">スケジュール</a></li>
    </ul>
</div>
