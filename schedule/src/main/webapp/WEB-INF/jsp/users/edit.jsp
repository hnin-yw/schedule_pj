<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
request.setAttribute("title", "Edit User");
%>
<%@ include file="/WEB-INF/jsp/var.jsp"%>
<%@ include file="/WEB-INF/jsp/content.jsp"%>
<div class="container-fluid">
	<div class="row content">
		<%@ include file="/WEB-INF/jsp/header_menu.jsp"%>
		<%@ include file="/WEB-INF/jsp/nav_bar.jsp"%>
		<div class="col-sm-10 content_body">
			<h2 class="text-center">ユーザの編集</h2>
			<%@ include file="/WEB-INF/jsp/message.jsp"%>
			<sec:authorize access="hasAnyRole('ADMIN', 'USER')">
				<c:if
					test="${isAdmin || isEditor || isUser && user.getCreatedBy() == userCode}">
					<div class="card">
						<div class="card-body">
							<form:form action='/schedule/users/update' method='post'
								modelAttribute="user">
								<div class="form-group col-sm-12">
									<label for="groupName"> グループ :</label> <select name="groupCode"
										class="form-control">
										<c:choose>
											<c:when test="${gpLists.size() > 0}">
												<option value="">-- グループを選択してください --</option>
												<c:forEach items="${gpLists}" var="group">
													<option value="${group.getGroupCode()}"
														<c:if test="${group.getGroupCode() == user.getGroupCode()}">selected</c:if>>${group.getGroupName()}</option>
												</c:forEach>
											</c:when>
											<c:otherwise>
											</c:otherwise>
										</c:choose>
									</select>
								</div>
								<%-- <div class="form-group col-sm-12">
							<label for="userName"> ユーザ名・ログイン名 :</label> <input type="text"
								id="userName" name="userName" value="${user.getUserName()}"
								placeholder="ユーザ名・ログイン名" class="form-control " >
						</div>
							<input type="hidden" id="groupCode" name="groupCode"
								value="${user.getGroupCode()}" class="form-control "> --%>
								<input type="hidden" id="userName" name="userName"
									value="${user.getUserName()}" class="form-control ">
								<input type="hidden" id="password" name="password"
									value="${user.getPassword()}" class="form-control ">
								<div class="form-group col-sm-12">
									<label for="userFirstName"> ユーザの名 :</label> <input type="text"
										id="userFirstName" name="userFirstName"
										value="${user.getUserFirstName()}" placeholder="ユーザの名"
										class="form-control "><span> <form:errors
											path="userFirstName" style="color:red" />
									</span>
								</div>
								<div class="form-group col-sm-12">
									<label for="userLastName"> ユーザの姓 :</label> <input type="text"
										id="userLastName" name="userLastName"
										value="${user.getUserLastName()}" placeholder="ユーザの姓"
										class="form-control "><span> <form:errors
											path="userLastName" style="color:red" />
									</span>
								</div>
								<div class="form-group">
									<div class="col-sm-4">
										<label for="postCode"> 郵便番号 :</label> <input type="text"
											id="postCode" name="postCode" value="${user.getPostCode()}"
											placeholder="郵便番号" class="form-control "><span>
											<form:errors path="postCode" style="color:red" />
										</span>
									</div>
									<div class="col-sm-8">
										<label for="address"> 住所 :</label>
										<textarea id="address" name="address" placeholder="ユーザの住所"
											class="form-control">${user.getAddress()}</textarea>
										<span> <form:errors path="address" style="color:red" />
										</span>
									</div>
								</div>
								<div class="form-group col-sm-12">
									<label for="telNumber"> 電話番号 :</label> <input type="text"
										id="telNumber" name="telNumber" value="${user.getTelNumber()}"
										placeholder="電話番号" class="form-control "><span>
										<form:errors path="telNumber" style="color:red" />
									</span>
								</div>
								<div class="form-group col-sm-12">
									<label for="email"> メール :</label> <input type="text" id="email"
										name="email" placeholder="メール" value="${user.getEmail()}"
										class="form-control "><span> <form:errors
											path="email" style="color:red" />
									</span>
								</div>
								<div class="form-group col-sm-12">
									<label for="roleId"> 許可 :</label> <select id="roleId"
										name="roles[0].id" class="form-control">
										<c:choose>
											<c:when test="${roleLists.size() > 0}">
												<c:forEach items="${roleLists}" var="role">
													<option value="${role.getId()}"
														<c:if test="${fn:contains(user.getRoles(), role)}">selected</c:if>>${role.getRoleName()}</option>
												</c:forEach>
											</c:when>
										</c:choose>
									</select><span> <form:errors path="roles" style="color:red" />
									</span>
								</div>
								<input type='hidden' id='id' class='form-control' name='id'
									value="${user.getId()}" />
								<div class="up-btn-gp col-sm-12">
									<a href="/schedule/users">
										<button type="button" class="btn btn-Light">キャンセル</button>
									</a>
									<button type="submit" class="btn btn-primary">編集</button>
								</div>
							</form:form>
						</div>
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
</body>
</html>