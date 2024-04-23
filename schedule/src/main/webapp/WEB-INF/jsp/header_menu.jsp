<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String userName = "";
Cookie[] cookies = request.getCookies();
if (cookies != null) {
    for (Cookie cookie : cookies) {
        if (cookie.getName().equals("userName")) {
            userName = cookie.getValue();
            break; // Exit loop once userName cookie is found
        }
    }
}
%>
<nav class="navbar-inverse">
	<div class="container-fluid">
		<div class="collapse navbar-collapse">
			<ul class="nav navbar-nav navbar-right">
				<li><a><span class="glyphicon glyphicon-user"></span>
						<%= userName %></a></li>
				<li><a href="/schedule/logout"><span class="glyphicon glyphicon-log-in"></span>
						ログアウト</a></li>
			</ul>
		</div>
	</div>
</nav>
