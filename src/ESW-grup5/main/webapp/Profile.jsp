<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<script type="text/javascript">
    $(document).ready(function () {
        var username = "${user.username}";
        $('#iterator').load('Tweets?username=' + encodeURIComponent(username));
    });
</script>


<c:choose>
    <c:when test="${user != null}">
        <div id="${user.id}" class="w3-container w3-card w3-round w3-white w3-section w3-center" data-username="${user.username}">
            <h4 style="font-weight: bold;">${user.username}</h4>
            <p><img src="${user.picture}" class="w3-circle" style="height:106px;width:106px" alt="Avatar"></p>
            <hr>
            <div class="w3-container w3-padding-32">
			  <div class="w3-row">
			
			    <div class="w3-third w3-center">
			      <div class="w3-card w3-padding w3-light-grey">
			        <h3><i class="fa fa-star fa-fw"></i></h3>
			        <p style="font-weight: bold;">${user.socialCredit}</p>
			      </div>
			    </div>
			
			    <div class="w3-third w3-center">
			      <div class="w3-card w3-padding w3-light-grey">
			        <h3>Seguidores</h3>
			        <p style="font-weight: bold;">${followerCount}</p>
			      </div>
			    </div>
			
			    <a class="menu" href="Followed" style="text-decoration: none; color: inherit;">
				  <div class="w3-third w3-center">
				    <div class="w3-card w3-padding w3-light-grey">
				      <h3>Siguiendo</h3>
				      <p style="font-weight: bold;">${followingCount}</p>
				    </div>
				  </div>
				</a>

			
			  </div>
			</div>
            <c:if test="${enableEdit == true}">
                <button type="button" class="editUser w3-row w3-button w3-section boton-relevante"
                        data-username="${user.username}"><i class="fa fa-user-plus"></i> &nbsp;Edit
                </button>
            </c:if>
            <c:if test="${enableDelete == true}">
                <button type="button" class="deleteUser w3-row w3-button w3-section boton-relevante"
                        data-username="${user.username}"><i class="fa fa-user-times"></i> &nbsp;Delete
                </button>
            </c:if>
            <c:if test="${enableFollowButtons == true}">
                <c:choose>
                    <c:when test="${isFollowing == true}">
                        <button type="button" class="unfollowUser w3-row w3-button w3-section w3-red"
                                data-user-id="${user.id}"><i class="fa fa-user-times"></i> &nbsp;Unfollow
                        </button>
                    </c:when>
                    <c:otherwise>
                        <button type="button" class="followUser w3-row w3-button w3-section w3-theme"
                                data-user-id="${user.id}"><i class="fa fa-user-plus"></i> &nbsp;Follow
                        </button>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </div>
        <br>

        <!-- Tweets of the user -->
        <h5 class="w3-center">User Tweets</h5>
        <div id="iterator" data-profile-username="${user.username}"></div>

    </c:when>
    <c:otherwise>
        <p></p>
    </c:otherwise>
</c:choose>