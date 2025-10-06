<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Mostrar encabezado con info de usuario si hay uno recibido -->
<c:if test="${user != null}">
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
        </div>
</c:if>

<!-- Si no hay user, solo se muestra titulo -->
<c:if test="${user == null}">
    <h2 class="w3-center w3-padding">Latest Tweets</h2>
</c:if>

<!-- Lista de tweets (sin imagen) -->
<c:forEach var="t" items="${tweets}">
    <div id="${t.id}" class="w3-container w3-card w3-section w3-white w3-round w3-animate-opacity"><br>

        <!-- No se muestra imagen del usuario -->
        <span class="w3-right w3-opacity">${t.postDateTime}</span>
        <h4>${t.username}</h4><br>

        <hr class="w3-clear">
        <p>${t.content}</p>

        <!-- Mostrar solo numero de likes -->
        <p><i class="fa fa-thumbs-up"></i> (${t.likesCount})</p>
    </div>
</c:forEach>


<c:if test="${user == null}">
    <div class="w3-center w3-panel w3-border">
        To see more tweets, please log in or register.
    </div>
</c:if>