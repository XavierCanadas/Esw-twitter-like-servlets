<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:set var="isEditMode" value="${editing == true}"/>

<form id="registerForm" action="${isEditMode ? 'EditUser' : 'Register'}" method="POST" enctype="multipart/form-data"
      class="w3-container w3-card-4 w3-light-grey w3-text-theme w3-margin">

    <h3>${isEditMode ? 'Edit Profile' : 'Register'}</h3>

    <c:if test="${isEditMode}">
        <input type="hidden" name="userId" value="${user.id}"/>
    </c:if>

    <div>
        <label for="email">Email:</label>
        <input type="email" class="w3-input w3-border w3-light-grey"
               id="email"
               name="email" required pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}" value="${user.email}"
               title="Enter a valid email address"/>
    </div>

    <div>
        <label for="username" class="w3-text-theme">Username:</label>
        <input type="text" class="w3-input w3-border w3-light-grey"
               id="username"
               name="username" required minlength="2" maxlength="15" value="${user.username}"
               title="Username must be beetween 5 and 20 characters."/>
    </div>
    <div>
        <label for="password" class="w3-text-theme">Password:</label>
        <input type="password" class="w3-input w3-border w3-light-grey"
               id="password" name="password" required
               pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*]).{8,}$"
               value="${user.password}"
               title="Minimum 8 characters, including uppercase, number, and special character: *[!@#$%^&*]"/>

        <label for="confirmPassword" class="w3-text-theme">Repeat password:</label>
        <input type="password" class="w3-input w3-border w3-light-grey"
               id="confirmPassword" name="confirmPassword" required
               value="${user.password}" title="Passwords must match"/>
    </div>

    <div>
        <label for="gender">Gender:</label>
        <select id="gender" name="gender" required>
            <option value="" disabled ${empty user.gender ? 'selected' : ''}>Select gender</option>
            <option value="male" ${user.gender == 'male' ? 'selected' : ''}>Male</option>
            <option value="female" ${user.gender == 'female' ? 'selected' : ''}>Female</option>
            <option value="non-binary" ${user.gender == 'non-binary' ? 'selected' : ''}>Non Binary</option>
            <option value="indeterminate" ${user.gender == 'indeterminate' ? 'selected' : ''}>Indeterminate</option>
        </select>

        <label for="birthdate">Birthdate:</label>
        <input type="date" id="birthdate" name="birthdate" required
               value="${not empty user.birthdate ? user.birthdate : ''}"/>

        <label for="polis">Polis:</label>
        <select id="polis" name="polisId" required>
            <option value="" disabled ${empty user.polis ? 'selected' : ''}>Select polis</option>
            <c:forEach var="polisOption" items="${polisList}">
                <option value="${polisOption.id}" ${user.polis.id == polisOption.id ? 'selected' : ''}>${polisOption.name}</option>
            </c:forEach>
        </select>
    </div>
	<c:if test="${!isEditMode}">
	    <label>Choose a profile picture:</label>
		<div id="profile-picture-selection" style="display: flex; gap: 1rem; flex-wrap: wrap;">
		    <label class="avatar-option">
		        <input type="radio" name="picturePath" value="hombreAvatar.png" required>
		        <img src="assets/hombreAvatar.png" alt="Avatar 1">
		    </label>
		    <label class="avatar-option">
		        <input type="radio" name="picturePath" value="mujerAvatar.png">
		        <img src="assets/mujerAvatar.png" alt="Avatar 2">
		    </label>
		    <label class="avatar-option">
		        <input type="radio" name="picturePath" value="robotAvatar.png">
		        <img src="assets/robotAvatar.png" alt="Avatar 3">
		    </label>
		</div>
    </c:if>
    <br/>
    <button type="submit" class="w3-btn w3-theme"> Register</button>

    <div class="error-container">
        <c:if test="${not empty errors}">
            <ul class="error-list">
                <c:forEach var="error" items="${errors}">
                    <li class="error-item">${error.value}</li>
                </c:forEach>
            </ul>
        </c:if>
    </div>

</form>

<script src="js/webcam.js"></script>
<script src="js/RegisterValidation.js"></script>
<script>
    window.App = window.App || {};
    window.App.Errors = {
        <c:forEach var="error" items="${errors}">
        "${error.key}": "${error.value}",
        </c:forEach>
    };

    window.App.isEditMode = ${isEditMode};

    initValidation(window.App.Errors);
</script>

