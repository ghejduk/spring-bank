<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<layout:base title="Register customer account" showNavbar="false">
    <jsp:attribute name="body">
        <div class="col-xs-12 col-sm-6 col-sm-offset-3">
            <h1>Registration form</h1>
            <form:form commandName="registerForm" method="post">
                <c:if test="${not empty bindingResult}" var="resultIsSet"/>

                <c:if test="${resultIsSet && bindingResult.hasFieldErrors('email')}" var="emailHasErrors"/>
                <div class="form-group<c:if test="${emailHasErrors}"> has-error</c:if>">
                    <label for="email">Email:</label>
                    <form:input path="email" name="email" cssClass="form-control"/>
                    <c:if test="${emailHasErrors}">
                        <p class="text-danger"><form:errors path="email"/></p>
                    </c:if>
                </div>

                <c:if test="${resultIsSet && bindingResult.hasFieldErrors('password')}" var="passwordHasErrors"/>
                <div class="form-group<c:if test="${passwordHasErrors}"> has-error</c:if>">
                    <label for="password">Password:</label>
                    <form:password path="password" name="password" cssClass="form-control"/>
                    <c:if test="${passwordHasErrors}">
                        <p class="text-danger"><form:errors path="password"/></p>
                    </c:if>
                </div>

                <c:if test="${resultIsSet && bindingResult.hasFieldErrors('repeatPassword')}"
                      var="repeatPasswordHasErrors"/>
                <div class="form-group<c:if test="${repeatPasswordHasErrors}"> has-error</c:if>">
                    <label for="repeatPassword">Repeat password:</label>
                    <form:password path="repeatPassword" name="repeatPassword" cssClass="form-control"/>
                    <c:if test="${repeatPasswordHasErrors}">
                        <p class="text-danger"><form:errors path="repeatPassword"/></p>
                    </c:if>
                </div>

                <a href="<c:url value="/login"/>" class="pull-left">Login</a>
                <button type="submit" class="btn btn-success pull-right">Register</button>
            </form:form>
        </div>
    </jsp:attribute>
</layout:base>


