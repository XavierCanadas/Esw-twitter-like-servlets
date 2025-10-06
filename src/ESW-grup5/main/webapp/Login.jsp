<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> Login </title>
</head>
<body>

<form id="loginForm" action="Login" method="POST">
    <div>
        <label for="username" class="w3-text-theme">Username:</label>
        <input type="text" class="w3-input w3-border w3-light-grey"
            id="username" name="username" required
            value="${user.username}" />
    </div>
    <div>
        <label for="password" class="w3-text-theme">Password:</label>
        <input type="password" class="w3-input w3-border w3-light-grey"
            id="password" name="password" required
            value="" />
    </div>
    <br/>
    <button type="submit" class="w3-btn w3-theme">Login</button>

    <div class="error-container">
        <c:if test="${not empty errors}">
            <ul class="error-list">
                <c:forEach var="error" items="${errors}">
                    <li class="error-item">${error.value}</li>
                </c:forEach>
            </ul>
        </c:if>
    </div>

    <c:if test="${not empty param.registered}">
        <div class="success-message">
            User ${user.username} has been correctly registered. Please login.
        </div>
    </c:if>

    <div class="tip-section">
        <p><strong>Tip:</strong> Use the following credentials for testing:</p>
        <ul>
            <li><strong>Username:</strong> gracie.abrams</li>
            <li><strong>Password:</strong> Aaee1122@</li>
        </ul>
    </div>

</form>

<script>
    window.App.Errors = {
        <c:forEach var="error" items="${errors}">
            "${error.key}": "${error.value}",
        </c:forEach>
    };
</script>

</body>
</html>

