<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<layout:base title="Car">
    <jsp:attribute name="body">
        <div>
            <h1>${car.id}</h1>
            <ul>
                <li>${car.make}</li>
                <li>${car.model}</li>
            </ul>
        </div>
    </jsp:attribute>
</layout:base>