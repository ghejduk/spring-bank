<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<layout:base title="Register customer account" showNavbar="false">
    <jsp:attribute name="head">
        <link href="<c:url value="/static/css/signin.css"/>" rel="stylesheet"/>
    </jsp:attribute>

    <jsp:attribute name="body">
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success">
                    ${successMessage}
            </div>
        </c:if>

        <form action="<c:url value="/login"/>" method="post" class="form-signin">
            <input class="form-control" type="text" name="username" placeholder="Username">
            <input class="form-control" type="password" name="password" placeholder="Password">
            <sec:csrfInput/>
            <input type="submit" value="Login" class="btn btn-lg btn-primary btn-block">
        </form>

        <div class="text-center">
            <a href="<c:url value="/register"/>">Register</a>
        </div>
    </jsp:attribute>
</layout:base>