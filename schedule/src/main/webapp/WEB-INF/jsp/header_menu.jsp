<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<nav class="navbar-inverse">
	<div class="container-fluid">
		<div class="collapse navbar-collapse">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="#"><span class="glyphicon glyphicon-user"></span>
						<%= session.getAttribute("user_code") %></a></li>
				<li><a href="#"><span class="glyphicon glyphicon-log-in"></span>
						ログアウト</a></li>
			</ul>
		</div>
	</div>
</nav>