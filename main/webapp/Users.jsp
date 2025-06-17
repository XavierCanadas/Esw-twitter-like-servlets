<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %> 
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:choose>
    <c:when test="${empty users}">
        <p>No citizens found</p>
    </c:when>
    <c:otherwise>
        <h2>Popular Users</h2>  
        <div class="w3-margin-top">
            <c:forEach var="user" items="${users}">
                <button 
                    type="button" 
                    class="w3-button w3-theme w3-margin-bottom showTweets" 
                    data-username="${user.username}">
                    ${user.username}
                </button>
            </c:forEach>
        </div>
    </c:otherwise>
</c:choose>
