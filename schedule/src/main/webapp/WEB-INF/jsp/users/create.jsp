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
			<div class="card">
				<c:if test="${not empty error}">
					<div class="alert alert-danger" role="alert">
						<strong>${error}</strong>
					</div>
				</c:if>
				<div class="card-body">
					<form action='/schedule/users/save' method='post'>
						<div class="form-group col-sm-12">
							<label for="groupName"> グループ :</label> <select name="groupId"
								class="form-control">
								<c:choose>
									<c:when test="${gpLists.size() > 0}">
										<option value="">-- グループを選択してください --</option>
										<c:forEach items="${gpLists}" var="group">
											<option value="${group.getId()}">${group.getGroupName()}</option>
										</c:forEach>
									</c:when>
									<c:otherwise>
									</c:otherwise>
								</c:choose>
							</select>
						</div>
						<div class="form-group col-sm-12">
							<label for="userName"> ユーザー名・ログイン名 :</label> <input type="text"
								id="userName" name="userName" placeholder="ユーザー名・ログイン名"
								class="form-control ">
						</div>
						<div class="form-group col-sm-12">
							<label for="password"> パスワード :</label> <input type="password"
								id="password" name="password" placeholder="パスワード"
								class="form-control ">
						</div>
						<div class="form-group col-sm-12">
							<label for="userFirstName"> ユーザの名 :</label> <input type="text"
								id="userFirstName" name="userFirstName" placeholder="ユーザの名"
								class="form-control ">
						</div>
						<div class="form-group col-sm-12">
							<label for="userLastName"> ユーザーの姓 :</label> <input type="text"
								id="userLastName" name="userLastName" placeholder="ユーザーの姓"
								class="form-control ">
						</div>
						<div class="form-group">
							<div class="col-sm-4">
								<label for="postCode"> 郵便番号 :</label> <input type="text"
									id="postCode" name="postCode" placeholder="郵便番号"
									class="form-control ">
							</div>
							<div class="col-sm-8">
								<label for="address"> 住所 :</label>
								<textarea id="address" name="address" placeholder="ユーザーの住所"
									class="form-control"></textarea>
							</div>
						</div>
						<div class="form-group col-sm-12">
							<label for="telNumber"> 電話番号 :</label> <input type="text"
								id="telNumber" name="telNumber" placeholder="電話番号"
								class="form-control ">
						</div>
						<div class="form-group col-sm-12">
							<label for="email"> メール :</label> <input type="text" id="email"
								name="email" placeholder="メール" class="form-control ">
						</div>
						<div class="up-btn-gp col-sm-12">
							<a href="/schedule/users">
								<button type="button" class="btn btn-Light">キャンセル</button>
							</a>
							<button type="submit" class="btn btn-primary">登録</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>