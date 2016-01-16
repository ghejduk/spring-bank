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
                <a href="<c:url value="/account/create"/>" class="pull-right">Create new account</a>
            </div>

            <div class="panel-body">
                <div class="alert alert-success" role="alert">
                    <h3>${account.balance.toDouble()}</h3>
                </div>
            </div>
        </div>
    </jsp:attribute>
</layout:base>


