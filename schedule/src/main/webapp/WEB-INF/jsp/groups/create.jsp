<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" />
<title>Create Group</title>
</head>
<body>
<div class="container pt-5">
		<h1 class="text-center">グループの登録</h1>
		<div class="container mt-5">
			<div class="row justify-content-center">
				<div class="col-md-6">
					<div class="card">
						<c:if test="${not empty error}">
							<div class="alert alert-danger" role="alert">
								<strong>${error}</strong>
							</div>
						</c:if>
						<div class="card-body">
							<form action='/schedule/groups/save' method='post'>
								<div class="form-group">
									<label for="email"> グループ名 :</label> 
									<input type="text" class="form-control" id="groupName" name="groupName" placeholder="Group Name" />
								</div>
								<button type="submit" class="btn btn-primary">登録</button>
								<a href="/schedule/groups">
									<button type="button" class="btn btn-Light">キャンセル</button>
								</a>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>