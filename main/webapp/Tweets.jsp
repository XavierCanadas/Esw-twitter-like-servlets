<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:forEach var="t" items="${tweets}">
    <div id="${t.id}" class="w3-container w3-card w3-section w3-white w3-round w3-animate-opacity"><br>
        <img src="${user.picture}" alt="Avatar" class="w3-left w3-circle w3-margin-right" style="width:60px">
        <span class="w3-right w3-opacity"> ${t.postDateTime} </span>
        <h4> ${t.username} </h4><br>
        <hr class="w3-clear">
        <p> ${t.content} </p>
        <button type="button" class="likeTweet w3-button w3-theme w3-margin-bottom${t.likedByCurrentUser ? ' liked' : ''}">
            <i class="fa fa-thumbs-up"></i> &nbsp;
            <c:choose>
                <c:when test="${t.likedByCurrentUser}">
                    Liked
                </c:when>
                <c:otherwise>
                    Like
                </c:otherwise>
            </c:choose>
            <span>(${t.likesCount})</span>
        </button>
        <button type="button" class="delTweet w3-button w3-red w3-margin-bottom"><i class="fa fa-trash"></i> &nbsp;Delete
        </button>
    </div>
</c:forEach>