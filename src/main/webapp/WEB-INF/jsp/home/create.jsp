<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<layout:base title="Create new car">
    <jsp:attribute name="body">
        <form:form method="post" commandName="car">
            <form:errors path="make"/>
            <form:input path="make" name="make"/>

            <form:errors path="model"/>
            <form:input path="model" name="model"/>

            <button type="submit">Send</button>
        </form:form>
    </jsp:attribute>
</layout:base>
