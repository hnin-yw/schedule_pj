<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
request.setAttribute("title", "Edit Group");
%>
<%@ include file="/WEB-INF/jsp/content.jsp"%>
<div class="container-fluid">
	<div class="row content">
		<%@ include file="/WEB-INF/jsp/header_menu.jsp"%>
		<%@ include file="/WEB-INF/jsp/nav_bar.jsp"%>
		<div class="col-sm-10 content_body">
			<h2 class="text-center">グループの編集</h2>
			<div class="card">
				<%@ include file="/WEB-INF/jsp/message.jsp"%>
				<sec:authorize access="hasAnyRole('ADMIN', 'USER')">
					<c:if
						test="${isAdmin || isEditor || isUser && user.getCreatedBy() == userCode}">
						<div class="card-body">
							<form:form action='/schedule/groups/update' method='post'
								modelAttribute="group">
								<div class="form-group col-sm-12">
									<label for="groupName"> グループ名 :</label> <input type='text'
										name='groupName' id='groupName' class='form-control'
										placeholder="グループ名" value="${group.getGroupName()}" /> <span><form:errors
											path="groupName" cssStyle="color:red" /></span>
								</div>
								<input type='hidden' id='id' class='form-control' name='id'
									value="${group.getId()}" />
								<div class="up-btn-gp col-sm-12">
									<a href="/schedule/groups">
										<button type="button" class="btn btn-Light">キャンセル</button>
									</a>
									<button type="submit" class="btn btn-primary">編集</button>
								</div>
							</form:form>
						</div>
					</c:if>
				</sec:authorize>

				<c:if
					test="${!isAdmin && (!isUser || (isUser && user.getCreatedBy() != userCode))}">
					<%@ include file="/WEB-INF/jsp/auth_message.jsp"%>
				</c:if>
			</div>
		</div>
	</div>
</div>
</body>
</html>