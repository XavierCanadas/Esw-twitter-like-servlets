<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>


<c:choose>
    <c:when test="${empty users}">
        <p>No hay usuarios populares para mostrar.</p>
    </c:when>
    <c:otherwise>
        <h2>Popular Users</h2>  
        <ul>
            <c:forEach var="user" items="${users}">
                <li>${user.username}</li> 
            </c:forEach>
        </ul>
    </c:otherwise>
</c:choose>