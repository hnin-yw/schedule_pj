<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
	<c:when test="${not empty error}">
		<div class="alert alert-danger alert-dismissible">
			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			<strong>${error}</strong>
		</div>
	</c:when>
	<c:when test="${not empty msgLists && msgLists.errors.size() > 0}">
		<div class="alert alert-danger alert-dismissible">
			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			<c:forEach items="${msgLists.errors}" var="errMsg">
				<strong>${errMsg}</strong>
				<br>
			</c:forEach>
		</div>
	</c:when>
	<c:when test="${not empty message}">
		<div class="alert alert-success alert-dismissible">
			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			<strong>${message}</strong>
		</div>
	</c:when>
	<c:otherwise>
		<c:if test="${msgLists.messages.size() > 0}">
			<div class="alert alert-success alert-dismissible">
				<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
				<c:forEach items="${msgLists.messages}" var="msg">
					<strong>${msg}</strong>
					<br>
				</c:forEach>
			</div>
		</c:if>
	</c:otherwise>
</c:choose>