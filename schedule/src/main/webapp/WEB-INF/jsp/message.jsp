<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:choose>
	<c:when test="${msgLists.errors.size() > 0}">
		<div class="alert alert-danger alert-dismissible">
			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			<c:forEach items="${msgLists.errors}" var="errMsg">
				<strong>${errMsg}</strong>
				<br>
			</c:forEach>
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