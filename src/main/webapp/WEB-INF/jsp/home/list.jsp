<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<layout:base title="List">
    <jsp:attribute name="body">
        <p><a href="<c:url value="/car/create"/>">Add new car</a></p>

        <c:forEach items="${cars}" var="car">
            <div>
                <h4>${car.id}</h4>
                <ul>
                    <li><c:out value="${car.make}"/></li>
                    <li><c:out value="${car.model}"/></li>
                </ul>
            </div>
        </c:forEach>
    </jsp:attribute>
</layout:base>


