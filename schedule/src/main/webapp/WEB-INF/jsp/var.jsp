<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<sec:authorize var="isAdmin" access="hasRole('ADMIN')" />
<sec:authorize var="isGroupAdmin" access="hasRole('GROUP_ADMIN')" />
<sec:authorize var="isViewer" access="hasRole('VIEWER')" />
<sec:authorize var="isCreator" access="hasRole('CREATOR')" />
<sec:authorize var="isEditor" access="hasRole('EDITOR')" />
<sec:authorize var="isUser" access="hasRole('USER')" />
<sec:authentication var="userCode" property="principal.user.userCode" />
<sec:authentication var="groupCode" property="principal.user.groupCode" />