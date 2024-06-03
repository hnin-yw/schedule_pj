<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<sec:authorize var="isAdmin" access="hasRole('ADMIN')" />
<sec:authorize var="isGroupAdmin" access="hasRole('GROUP_ADMIN')" />
<sec:authorize var="isViewer" access="hasRole('VIEWER')" />
<sec:authorize var="isCreator" access="hasRole('CREATOR')" />
<sec:authorize var="isEditor" access="hasRole('EDITOR')" />
<sec:authorize var="isUser" access="hasRole('USER')" />

<sec:authentication var="principal" property="principal" />

<c:choose>
    <c:when test="${fn:startsWith(principal.getClass().getName(), 'org.springframework.security.oauth2.core.user.DefaultOAuth2User')}">
        <c:set var="isOauth2User" value="true" />
        <c:set var="oauth2User" value="${principal}" />
        <c:set var="userCode" value="${oauth2User.getAttribute('userCode')}" />
        <c:set var="groupCode" value="${null}" />
    </c:when>
    <c:otherwise>
        <c:set var="isOauth2User" value="false" />
        <sec:authentication var="userCode" property="principal.user.userCode" />
        <sec:authentication var="groupCode" property="principal.user.groupCode" />
    </c:otherwise>
</c:choose>