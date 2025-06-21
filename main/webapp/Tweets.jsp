<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:forEach var="t" items="${tweets}">
    <div id="${t.id}" class="tweet-container w3-container w3-card w3-section w3-white w3-round w3-animate-opacity"><br>
            <%-- De momento me cargo la imagen --%>

        <img src="${t.profilePictureUrl}" alt="${t.username} avatar" class="w3-left w3-circle w3-margin-right" style="width:60px">

        <span class="w3-right w3-opacity"> ${t.postDateTime} </span>


        <div style="display: flex; flex-direction: column; align-items: flex-start;">
            <c:if test="${t.parentId != null}">
                <button type="button" class="load-parent-tweet-comment" data-parent-id="${t.parentId}">
                    This tweet is a reply
                </button>
            </c:if>
            <button type="button" class="tweet-username" data-username="${t.username}">
                    ${t.username}
            </button>
        </div>
        <br>
        <hr class="w3-clear">
        <p> ${t.content} </p>
        <button type="button"
                class="likeTweet w3-button w3-theme w3-margin-bottom${t.likedByCurrentUser ? ' liked' : ''}">
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
            <c:if test="${user.isAdmin || user.id == t.uid}">
            <button type="button"
                    class="delTweet w3-button w3-red w3-margin-bottom">
                <i class="fa fa-trash"></i> &nbsp;Delete
            </button>
            </c:if>
        </button>
    </div>
</c:forEach>

<button type="button" class="load-more-tweets w3-button w3-theme w3-margin-bottom" data-total-tweets="${fn:length(tweets)}" aria-label="Load more tweets">
    <i class="fa fa-refresh"></i> &nbsp;Load More Tweets
</button>