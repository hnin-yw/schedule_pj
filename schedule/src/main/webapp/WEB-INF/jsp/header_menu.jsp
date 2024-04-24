<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String userName = "";
String userId = "";
Cookie[] cookies = request.getCookies();
if (cookies != null) {
    for (Cookie cookie : cookies) {
        if (cookie.getName().equals("userName")) {
            userName = cookie.getValue();
        } else if (cookie.getName().equals("userId")) {
            userId = cookie.getValue();
        }
    }
}
%>
<nav class="navbar-inverse">
	<div class="container-fluid">
		<div class="collapse navbar-collapse">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="/schedule/users/edit/<%=userId%>"><span
						class="glyphicon glyphicon-user"></span> <%=userName%></a></li>
				<li><a href="/schedule/logout"><span
						class="glyphicon glyphicon-log-in"></span> ログアウト</a></li>
			</ul>
		</div>
	</div>
</nav>
