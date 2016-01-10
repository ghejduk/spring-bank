<!DOCTYPE html>
<%@ tag description="Base Template" display-name="layout" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="title" %>
<%@ attribute name="showNavbar" required="false" %>
<%@ attribute name="head" fragment="true" required="false" %>
<%@ attribute name="body" fragment="true" required="true" %>
<%@ attribute name="footer" fragment="true" required="false" %>

<html>
<head>
    <title>${title}</title>
    <link rel='stylesheet' href='<c:url value="/webjars/bootstrap/3.3.6/css/bootstrap.min.css"/>'>
    <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet"/>

    <jsp:invoke fragment="head" var="head_content"/>
    <c:if test="${head_content.length() > 0}">
        ${head_content}
    </c:if>
</head>
<body>

<c:if test="${empty showNavbar}" var="showNavbarBool"/>

<c:if test="${showNavbarBool}">
    <jsp:include page="../../../WEB-INF/jsp/structure/navbar.jsp"/>
</c:if>

<div class="container">
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">
                ${successMessage}
        </div>
    </c:if>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">
                ${errorMessage}
        </div>
    </c:if>

    <jsp:invoke fragment="body"/>
</div>

<footer class="footer">
    <div class="container">
        <p class="text-muted">
            Bank &copy; 2015-2016
            <jsp:invoke fragment="footer" var="footer_content"/>
            <c:choose>
                <c:when test="${footer_content.length() > 0}">
                    ${footer_content}
                </c:when>
                <c:otherwise>
                    default footer :)
                </c:otherwise>
            </c:choose>
        </p>
    </div>
</footer>

<script src="<c:url value="/webjars/jquery/2.0.3/jquery.min.js"/>"></script>
<script src="<c:url value="/webjars/bootstrap/3.3.6/js/bootstrap.min.js"/>"></script>
</body>
</html>