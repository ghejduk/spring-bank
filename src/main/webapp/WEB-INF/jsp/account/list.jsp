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
                <h3 class="panel-title">Accounts</h3>
                <a href="<c:url value="/account/create"/>" class="pull-right">Create new account</a>
            </div>

            <div class="panel-body">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>Number</th>
                        <th>Balance</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="account" items="${accounts}">
                        <tr>
                            <td>
                                <a href="<c:url value="/account/${account.number}"/>">${account.number}</a>
                            </td>
                            <td>${account.balance}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </jsp:attribute>
</layout:base>


