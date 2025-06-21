<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:forEach var="t" items="${tweets}">
    <div id="${t.id}" class="tweet-container w3-container w3-card w3-section w3-white w3-round w3-animate-opacity"><br>
            <%-- De momento me cargo la imagen --%>

        <img src="${t.profilePictureUrl}" alt="${t.username} avatar" class="w3-left w3-circle w3-margin-right" style="width:60px">

        <c:if test="${t.parentId != null}">
                <span class="load-parent-tweet-comment w3-right" data-parent-id="${t.parentId}">
                    Respuesta
                </span>
            </c:if>

        <div style="display: flex; flex-direction: column; align-items: flex-start;">
            <span class= "w3-opacity"> ${t.postDateTime} </span>
        </div>
        <button
			  class="load-parent-tweet-comment"
			  style="margin: 0px; font-size: 1.17em; font-weight: bold; background: none; border: none; padding: 0;"
			  data-username="${t.username}"
			>${t.username}
			</button>
        <hr class="w3-clear">
        <p> ${t.content} </p>
        <button type="button"
                class="likeTweet w3-button w3-margin-bottom${t.likedByCurrentUser ? ' liked' : ''}">
            <i class="fa fa-thumbs-up"></i> &nbsp;
            <span>${t.likesCount}</span>
            <c:if test="${user.isAdmin || user.id == t.uid}">
	            <button type="button"
	                    class="delTweet w3-button w3-margin-bottom" style= "margin-left: 5px"> 
	                <i class="fa fa-trash"></i> &nbsp;Delete
	            </button>
            </c:if>
        </button>
    </div>
</c:forEach>

<button type="button" class="load-more-tweets w3-button w3-margin-bottom" data-total-tweets="${fn:length(tweets)}" aria-label="Load more tweets">
    <i class="fa fa-refresh"></i> &nbsp;Load More Tweets
</button>