<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Mostrar encabezado con info de usuario si hay uno recibido -->
<c:if test="${user != null}">
    <div class="w3-container w3-card w3-white w3-round w3-margin">
        <div class="w3-row w3-padding">
            <div class="w3-col s3">
                <img src="${user.picture}" alt="Avatar" class="w3-circle" style="width:80px">
            </div>
            <div class="w3-col s9 w3-container">
                <h3>${user.username}</h3>
                <p><strong>Polis:</strong> ${user.polis.name}</p>
                <p><strong>Social Credit:</strong> ${user.socialCredit}</p>
            </div>
        </div>
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