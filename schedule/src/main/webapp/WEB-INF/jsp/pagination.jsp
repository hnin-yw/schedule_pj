<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!-- Pagination controls -->
<c:if test="${currentPage > 0}">
	<a href="?page=${currentPage - 1}">Previous</a>
</c:if>

<c:forEach begin="0" end="${totalPages - 1}" var="i">
	<c:choose>
		<c:when test="${currentPage == i}">
			<strong>${i + 1}</strong>
		</c:when>
		<c:otherwise>
			<a href="?page=${i}">${i + 1}</a>
		</c:otherwise>
	</c:choose>
</c:forEach>

<c:if test="${currentPage < totalPages - 1}">
	<a href="?page=${currentPage + 1}">Next</a>
</c:if>