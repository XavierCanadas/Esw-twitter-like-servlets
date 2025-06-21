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
        <div id="${user.id}" class="w3-container w3-card w3-round w3-white w3-section w3-center">
            <h4>My Profile</h4>
            <p><img src="${user.picture}" class="w3-circle" style="height:106px;width:106px" alt="Avatar"></p>
            <hr>
            <p class="w3-left-align"><i class="fa fa-id-card fa-fw w3-margin-right"></i> ${user.username} </p>
            <p class="w3-left-align"><i class="fa fa-envelope fa-fw w3-margin-right"></i> ${user.email} </p>
            <p class="w3-left-align"><i class="fa fa-venus-mars fa-fw w3-margin-right"></i> Gender: ${user.gender} </p>
            <p class="w3-left-align"><i class="fa fa-birthday-cake fa-fw w3-margin-right"></i>
                Birthdate: ${user.birthdate} </p>
            <p class="w3-left-align"><i class="fa fa-star fa-fw w3-margin-right"></i> Social
                Credit: ${user.socialCredit} </p>
            <p class="w3-left-align"><i class="fa fa-map-marker fa-fw w3-margin-right"></i> Polis: ${user.polis.name}
            </p>
            <c:if test="${enableEdit == true}">
                <button type="button" class="editUser w3-row w3-button w3-green w3-section"
                        data-username="${user.username}"><i class="fa fa-user-plus"></i> &nbsp;Edit
                </button>
            </c:if>
            <c:if test="${enableDelete == true}">
                <button type="button" class="deleteUser w3-row w3-button w3-green w3-section"
                        data-username="${user.username}"><i class="fa fa-user-plus"></i> &nbsp;Delete
                </button>
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