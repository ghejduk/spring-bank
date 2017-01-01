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
                <h3 class="panel-title">Account <c:out value="${account.number}"/></h3>
            </div>

            <div class="panel-body">
                <div class="alert alert-info" role="alert">
                    <h4>Current balance: ${account.balance}</h4>
                </div>

                <table class="table table-striped">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Amount</th>
                    <th>Sender</th>
                    <th>Recipient</th>
                    <th>Date</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="transaction" items="${transactions}">
                    <tr>
                        <td>${transaction.id}</td>
                        <td>${transaction.amount}</td>
                        <td>${transaction.from}</td>
                        <td>${transaction.to}</td>
                        <td>${transaction.createdAt}</td>
                    </tr>
                </c:forEach>
                </tbody>
                </table>
            </div>
        </div>
    </jsp:attribute>
</layout:base>


