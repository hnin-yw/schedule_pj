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
			<%@ include file="/WEB-INF/jsp/message.jsp"%>
			<div class="card">
				<div class="card-body">
					<form action='/schedule/users/save' method='post'>
						<div class="form-group col-sm-12">
							<label for="groupName"> グループ :</label> <select name="groupCode"
								class="form-control">
								<c:choose>
									<c:when test="${gpLists.size() > 0}">
										<option value="">-- グループを選択してください --</option>
										<c:forEach items="${gpLists}" var="group">
											<option value="${group.getGroupCode()}">${group.getGroupName()}</option>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<option value="">-- グループを選択してください --</option>
									</c:otherwise>
								</c:choose>
							</select>
						</div>
						<div class="form-group col-sm-12">
							<label for="userName"> ユーザ名・ログイン名 :</label> <input type="text"
								id="userName" name="userName" placeholder="ユーザ名・ログイン名"
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
							<label for="userLastName"> ユーザの姓 :</label> <input type="text"
								id="userLastName" name="userLastName" placeholder="ユーザの姓"
								class="form-control ">
						</div>
						<div class="form-group">
							<div class="col-sm-4">
								<label for="postCode"> 郵便番号 :</label> <input type="text"
									id="postCode" name="postCode" placeholder="000-0000"
									class="form-control ">
							</div>
							<div class="col-sm-8">
								<label for="address"> 住所 :</label>
								<textarea id="address" name="address" placeholder="ユーザの住所"
									class="form-control"></textarea>
							</div>
						</div>
						<div class="form-group col-sm-12">
							<label for="telNumber"> 電話番号 :</label> <input type="text"
								id="telNumber" name="telNumber" placeholder="000-0000-0000"
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