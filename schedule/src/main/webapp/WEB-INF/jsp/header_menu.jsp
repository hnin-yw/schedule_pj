<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String reqURL = request.getRequestURL().toString();
	Boolean isShowNoti = false;
	if (reqURL != null && reqURL.contains("schedules")) {
		isShowNoti = true;
	}
	System.out.println(reqURL != null && reqURL.contains("schedules"));
%>
<nav class="navbar-inverse">
	<div class="container-fluid">
		<div class="collapse navbar-collapse">${reqURL != null && reqURL.contains("schedules")}
			<ul class="nav navbar-nav navbar-right">
				<!-- Display user information -->
				<li><a><span class="glyphicon glyphicon-user"></span> <sec:authentication var="principal" property="principal" /> 
                    <c:choose>
                        <c:when test="${isOauth2User}">
                            ${oauth2User.getAttribute('userName')}
                        </c:when>
                        <c:otherwise>
                            <sec:authentication property="name" />
                        </c:otherwise>
                    </c:choose>
                </a></li>
				<c:if test="${isShowNoti}">
					<li><a><span class="glyphicon glyphicon-bell"></span> <span id="showNotiCount"></span></a></li>
				</c:if>
				<li><a href="/schedule/login"><span class="glyphicon glyphicon-log-in"></span> ログアウト</a></li>
			</ul>
		</div>
	</div>
</nav>
