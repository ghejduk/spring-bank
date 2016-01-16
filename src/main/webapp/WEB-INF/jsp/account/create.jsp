<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<layout:base title="List">
    <jsp:attribute name="body">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Create new account</h3>
                <a href="<c:url value="/"/>" class="pull-right">Back to the list</a>
            </div>

            <div class="panel-body">
                <form:form commandName="createAccountForm" method="post">
                    <c:if test="${not empty bindingResult}" var="resultIsSet"/>

                    <c:if test="${resultIsSet && bindingResult.hasFieldErrors('balance')}" var="balanceHasErrors"/>
                    <div class="form-group<c:if test="${balanceHasErrors}"> has-error</c:if>">
                        <label for="balance">Balance:</label>
                        <form:input path="balance" name="balance" cssClass="form-control"/>
                        <c:if test="${balanceHasErrors}">
                            <p class="text-danger"><form:errors path="balance"/></p>
                        </c:if>
                    </div>

                    <button type="submit" class="btn btn-success pull-right">Create</button>
                </form:form>
            </div>
        </div>
    </jsp:attribute>
</layout:base>


