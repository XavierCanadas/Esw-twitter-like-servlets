<%--
  Created by IntelliJ IDEA.
  User: xavicanadas
  Date: 20/6/25
  Time: 00:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>


<script type="text/javascript">
    $(document).ready(function () {
        var parentId = "${tweet.id}";
        $('#iterator').load('Comments?parentId=' + encodeURIComponent(parentId));
    });
</script>



<div id="${tweet.id}" class="w3-container w3-card w3-round w3-white w3-section w3-center">
    <p><img src="${tweet.profilePictureUrl}" class="w3-circle" style="height:106px;width:106px" alt="Avatar"></p>
    <hr>
    <span class="w3-opacity" style="font-size: 11px;"> ${tweet.postDateTime} </span>
    <div>
    <button type="button" class="load-parent-tweet-comment tweet-username sin-hover" style="background-color: white; border-width: 0px;" data-username="${tweet.username}">
        ${tweet.username}
    </button>
    </div>
    <br>
    <hr class="w3-clear">
    <p> ${tweet.content} </p>
    <br>
    <button type="button" data-current-tweet-id="${tweet.id}"
            class="likeTweet w3-button w3-theme w3-margin-bottom${tweet.likedByCurrentUser ? ' liked' : ''}">
        <i class="fa fa-thumbs-up"></i> &nbsp;
        <c:choose>
            <c:when test="${tweet.likedByCurrentUser}">
                Liked
            </c:when>
            <c:otherwise>
                Like
            </c:otherwise>
        </c:choose>
        <span>(${tweet.likesCount})</span>
    </button>
    <c:if test="${user.isAdmin || user.id == tweet.uid}">
        <button type="button" data-current-tweet-id="${tweet.id}" class="delTweet w3-button w3-red w3-margin-bottom">
            <i class="fa fa-trash"></i> &nbsp;Delete
        </button>
    </c:if>
</div>
<br>

<!-- Write a comment -->
<!-- Ns que componente es un textarea, -->
<div class="w3-container w3-card w3-round w3-white w3-section w3-center">
    <h5>Write a comment</h5>
    <textarea id="commentContent" class="w3-input w3-border" rows="4" placeholder="Write your comment here..."></textarea>
    <div><button id="addComment" data-tweet-id="${tweet.id}" class="w3-button w3-green w3-section">Add Comment</button></div>
</div>

<br>

<!-- Tweets of the user -->
<h5 class="w3-center">Comments</h5>
<div id="iterator" data-parent-id="${tweet.id}"></div>

