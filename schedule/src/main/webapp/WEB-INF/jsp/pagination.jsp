<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Pagination controls -->
<nav aria-label="Page navigation example" class="cus-pagination">
	<ul class="pagination justify-content-center">
		<li class="page-item">
			<c:choose>
				<c:when test="${currentPage == 0}">
					<a class="page-link" aria-label="Previous"> 
						<span aria-hidden="true">&laquo;</span> <span class="sr-only">Previous</span>
					</a>
				</c:when>
				<c:otherwise>
					<a class="page-link" href="?page=${currentPage - 1}" aria-label="Previous"> 
						<span aria-hidden="true">&laquo;</span> <span class="sr-only">Previous</span>
					</a>
				</c:otherwise>
			</c:choose>
		</li>
		<c:forEach begin="0" end="${totalPages - 1}" var="i">
			<c:choose>
				<c:when test="${currentPage == i}">
					<li class="page-item active"><a class="page-link" href="#"><strong>${i + 1}</strong></a></li>
				</c:when>
				<c:otherwise>
					<li class="page-item"><a class="page-link" href="?page=${i}">${i + 1}</a></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<li class="page-item">
			<c:choose>
				<c:when test="${currentPage == totalPages - 1}">
					<a class="page-link" aria-label="Next"> 
						<span aria-hidden="true">&raquo;</span> <span class="sr-only">Next</span>
					</a>
				</c:when>
				<c:otherwise>
					<a class="page-link" href="?page=${currentPage + 1}" aria-label="Next"> 
						<span aria-hidden="true">&raquo;</span> <span class="sr-only">Next</span>
					</a>
				</c:otherwise>
			</c:choose>
		</li>
	</ul>
</nav>
