<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
request.setAttribute("title", "Guest Lists");
%>
<%@ include file="/WEB-INF/jsp/content.jsp"%>
<div class="container-fluid">
	<div class="row content">
		<%@ include file="/WEB-INF/jsp/header_menu.jsp"%>
		<%@ include file="/WEB-INF/jsp/nav_bar.jsp"%>
		<div class="col-sm-10 content_body">
			<h2 class="text-center">スケジュールのゲスト</h2>
			<%@ include file="/WEB-INF/jsp/message.jsp"%>
			<div class="card">
				<div class="card-body">
					<c:choose>
						<c:when test="${schedule.getAttendees().size() > 0}">
							<div class="form-group col-sm-12 list-group">
								<c:forEach items="${schedule.getAttendees()}" var="attendee"
									varStatus="loop">
									<a class="list-group-item">${loop.index+1}.
										${attendee.getUser().getEmail()}</a>
								</c:forEach>
							</div>
						</c:when>
						<c:otherwise>
							<div class="form-group col-sm-12 list-group">
								<span>このスケジュールに設定しているゲストはいません。</span>
							</div>
						</c:otherwise>
					</c:choose>
					<div class="up-btn-gp col-sm-12">
						<a href="/schedule/schedules">
							<button type="button" class="btn btn-Light">戻る</button>
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>