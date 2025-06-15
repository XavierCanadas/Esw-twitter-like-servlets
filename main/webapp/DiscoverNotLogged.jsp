<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Discover</title>
    <style>
        .half {
            float: left;
            width: 50%;
            box-sizing: border-box;
            padding: 10px;
        }
    </style>
</head>
<body>
    <div class="half">
        <h2>Usuarios más populares</h2>
        <c:if test="${not empty popularUsers}">
            <ul>
                <c:forEach var="user" items="${popularUsers}">
                    <li>${user.username}</li> 
                </c:forEach>
            </ul>
        </c:if>
        <c:if test="${empty popularUsers}">
            <p>No hay usuarios populares para mostrar.</p>
        </c:if>
    </div>

    <div class="half">
        <h2>Últimos tweets</h2>
        <c:if test="${not empty latestTweets}">
            <ul>
            <c:forEach var="t" items="${latestTweets}">
                <div id="${t.id}" class="w3-container w3-card w3-section w3-white w3-round w3-animate-opacity"><br>
                    <img src="default.jpg" alt="Avatar" class="w3-left w3-circle w3-margin-right" style="width:60px">
                    <span class="w3-right w3-opacity">${t.postDateTime}</span>
                    <h4>${t.username}</h4><br>
                    <hr class="w3-clear">
                    <p>${t.content}</p>
					<button type="button" disabled title="Join in" class="likeTweet w3-button w3-theme w3-margin-bottom ${t.likedByCurrentUser ? 'liked' : ''}">
					    <i class="fa fa-thumbs-up"></i> &nbsp;
					    <c:choose>
					        <c:when test="${t.likedByCurrentUser}">Liked</c:when>
					        <c:otherwise>Like</c:otherwise>
					    </c:choose>
					    <span>(${t.likesCount})</span>
					</button>
                </div>
            </c:forEach>
            </ul>
        </c:if>
        <c:if test="${empty latestTweets}">
            <p>No hay tweets recientes.</p>
        </c:if>
    </div>
</body>
</html>
