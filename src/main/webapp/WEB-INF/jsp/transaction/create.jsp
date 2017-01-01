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
                <h3 class="panel-title">Transfer funds</h3>
                <a href="<c:url value="/"/>" class="pull-right">Back to the list</a>
            </div>

            <div class="panel-body">
                <form:form commandName="transactionForm" method="post">
                    <c:if test="${not empty bindingResult}" var="resultIsSet"/>

                    <c:if test="${resultIsSet && bindingResult.hasFieldErrors('sender')}" var="senderHasErrors"/>
                    <div class="form-group<c:if test="${senderHasErrors}"> has-error</c:if>">
                        <label for="sender">Sender:</label>

                        <form:select path="sender" name="sender" cssClass="form-control">
                            <form:option value="" label="--- Select ---"/>
                            <form:options items="${accounts}" itemLabel="number" itemValue="id"/>
                        </form:select>

                        <c:if test="${senderHasErrors}">
                            <p class="text-danger"><form:errors path="sender"/></p>
                        </c:if>
                    </div>

                    <c:if test="${resultIsSet && bindingResult.hasFieldErrors('receiver')}" var="receiverHasErrors"/>
                    <div class="form-group<c:if test="${receiverHasErrors}"> has-error</c:if>">
                        <label for="receiver">Receiver:</label>

                        <form:select path="receiver" name="receiver" cssClass="form-control">
                            <form:option value="" label="--- Select ---"/>
                            <form:options items="${accounts}" itemLabel="number" itemValue="id"/>
                        </form:select>

                        <c:if test="${receiverHasErrors}">
                            <p class="text-danger"><form:errors path="receiver"/></p>
                        </c:if>
                    </div>

                    <c:if test="${resultIsSet && bindingResult.hasFieldErrors('amount')}" var="amountHasErrors"/>
                    <div class="form-group<c:if test="${amountHasErrors}"> has-error</c:if>">
                        <label for="amount">Amount:</label>
                        <form:input path="amount" name="amount" cssClass="form-control"/>
                        <c:if test="${amountHasErrors}">
                            <p class="text-danger"><form:errors path="amount"/></p>
                        </c:if>
                    </div>

                    <button type="submit" class="btn btn-warning pull-right">Transfer</button>
                </form:form>
            </div>
        </div>
    </jsp:attribute>
</layout:base>


