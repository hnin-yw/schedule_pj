<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
request.setAttribute("title", "Create Group");
%>
<%@ include file="/WEB-INF/jsp/content.jsp"%>
<div class="container-fluid">
	<div class="row content">
		<%@ include file="/WEB-INF/jsp/header_menu.jsp"%>
		<%@ include file="/WEB-INF/jsp/nav_bar.jsp"%>
		<div class="col-sm-10 content_body">
			<h2 class="text-center">グループの登録</h2>
			<%@ include file="/WEB-INF/jsp/message.jsp"%>
			<div class="card">
				<div class="card-body">
					<form action='/schedule/groups/save' method='post'>
						<div class="form-group col-sm-12">
							<label for="groupName"> グループ名 :</label> <input type="text"
								class="form-control" id="groupName" name="groupName"
								placeholder="Group Name" value="${cusGroup.getGroupName()}" />
						</div>
						<div class="up-btn-gp col-sm-12">
							<a href="/schedule/groups">
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