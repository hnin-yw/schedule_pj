<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
request.setAttribute("title", "Create User");
%>
<%@ include file="/WEB-INF/jsp/content.jsp"%>
<div class="container-fluid">
	<div class="row content">
		<%@ include file="/WEB-INF/jsp/header_menu.jsp"%>
		<%@ include file="/WEB-INF/jsp/nav_bar.jsp"%>
		<div class="col-sm-10 content_body">
			<h2 class="text-center">ユーザの登録</h2>
			<%-- <%@ include file="/WEB-INF/jsp/message.jsp"%> --%>
			<sec:authorize access="hasAnyRole('ADMIN', 'CREATOR')">
				<div class="alert alert-danger alert-dismissible" id="errorMsg"
					style="display: none;">
					<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
					<strong></strong>
				</div>
				<div class="alert alert-success alert-dismissible" id="successMsg"
					style="display: none;">
					<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
					<strong></strong>
				</div>
				<div class="card">
					<div class="card-body">
						<form id="frmUser">
							<div class="form-group col-sm-12">
								<label for="groupCode"> グループ :</label> <select id="groupCode"
									name="groupCode" class="form-control">
									<c:choose>
										<c:when test="${gpLists.size() > 0}">
											<option value="">-- グループを選択してください --</option>
											<c:forEach items="${gpLists}" var="group">
												<option value="${group.getGroupCode()}"
													<c:if test="${group.getGroupCode() == user.getGroupCode()}">selected</c:if>>${group.getGroupName()}</option>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<option value="">-- グループを選択してください --</option>
										</c:otherwise>
									</c:choose>
								</select><span> <form:errors path="groupCode" style="color:red" />
								</span>
							</div>
							<div class="form-group col-sm-12">
								<label for="userName"> ユーザ名・ログイン名 :</label> <input type="text"
									id="userName" name="userName" placeholder="ユーザ名・ログイン名"
									value="${user.getUserName()}" class="form-control "><span>
									<form:errors path="userName" style="color:red" />
								</span>
							</div>
							<div class="form-group col-sm-12">
								<label for="password"> パスワード :</label> <input type="password"
									id="password" name="password" placeholder="パスワード"
									class="form-control "><span> <form:errors
										path="password" style="color:red" /></span>
							</div>
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
										placeholder="000-0000" class="form-control "><span>
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
									placeholder="000-0000-0000" class="form-control "><span>
									<form:errors path="telNumber" style="color:red" />
								</span>
							</div>
							<div class="form-group col-sm-12">
								<label for="email"> メール :</label> <input type="text" id="email"
									name="email" value="${user.getEmail()}" placeholder="メール"
									class="form-control "><span> <form:errors
										path="email" style="color:red" />
								</span>
							</div>
							<div class="form-group col-sm-12">
								<label for="roleId"> グループ :</label> <select id="roles[0]"
									name="roleId" class="form-control">
									<c:choose>
										<c:when test="${roleLists.size() > 0}">
											<c:forEach items="${roleLists}" var="role">
												<option value="${role.getId()}">${role.getRoleName()}</option>
											</c:forEach>
										</c:when>
									</c:choose>
								</select><span> <form:errors path="groupCode" style="color:red" />
								</span>
							</div>
							<div class="up-btn-gp col-sm-12">
								<a href="/schedule/users">
									<button type="button" class="btn btn-Light">キャンセル</button>
								</a>
								<button type="submit" id="btnUserCreate" class="btn btn-primary">登録</button>
							</div>
						</form>
					</div>
				</div>
			</sec:authorize>

			<sec:authorize access="!hasAnyRole('ADMIN', 'CREATOR')">
				<%@ include file="/WEB-INF/jsp/auth_message.jsp"%>
			</sec:authorize>
		</div>
	</div>
</div>
</body>
<script>
	$(document).ready(function() {
		function dataClear() {
			$('#groupCode').val("");
			$('#userName').val("");
			$('#password').val("");
			$('#userFirstName').val("");
			$('#userLastName').val("");
			$('#postCode').val("");
			$('#address').val("");
			$('#telNumber').val("");
			$('#email').val("");
			$('#roleId').val("");
		}

		$('#frmUser').on('submit', function(e) {
			e.preventDefault();
			var formData = {
				groupCode : $('#groupCode').val(),
				userName : $('#userName').val(),
				password : $('#password').val(),
				userFirstName : $('#userFirstName').val(),
				userLastName : $('#userLastName').val(),
				postCode : $('#postCode').val(),
				address : $('#address').val(),
				telNumber : $('#telNumber').val(),
				email : $('#email').val(),
				roleId : $('#roleId').val(),
			};
			$.ajax({
				url : '/schedule/users/save',
				type : 'POST',
				contentType : 'application/json',
				data : JSON.stringify(formData),
				success : function(response) {
					console.log(response);
					if (response.error) {
						console.log(response.error);
						$('#errorMsg strong').html(response.error);
						$('#errorMsg').show();
						$('#successMsg').hide();
					} else {
						$('#successMsg strong').html(response.success);
						$('#successMsg').show();
						$('#errorMsg').hide();
						dataClear();
					}
				},
				error : function(xhr, status, error) {
					console.error(xhr.responseText);
				}
			});
		});
	});
</script>
</html>