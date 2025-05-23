<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<form id="registerForm" action="Register" method="POST" enctype="multipart/form-data">

	<div>
		<label for="email">Email:</label>
		<input type="email" id="email" name="email" required pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}" value="${user.email}" title="Enter a valid email address" />
	</div>

	<div>
		<label for="username" class="w3-text-theme">Name:</label>
		<input type="text" class="w3-input w3-border w3-light-grey" 
			id="username"
			name="username" required minlength="2" maxlength="15" value="${user.username}"
			title="Username must be beetween 5 and 20 characters." />
	</div>
	<div>
		<label for="password" class="w3-text-theme">Password:</label> 
		<input type="password" class="w3-input w3-border w3-light-grey"
			id="password" name="password" required
			pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*]).{8,}$"
			value="${user.password}"
			title="Minimum 8 characters, including uppercase, number, and special character: *[!@#$%^&*]" />

		<label for="confirmPassword" class="w3-text-theme">Repeat password:</label> 
		<input type="password" class="w3-input w3-border w3-light-grey"
		    id="confirmPassword" name="confirmPassword" required
			value="${user.password}" title="Passwords must match" />
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
			   value="${not empty user.birthdate ? user.birthdate : ''}" />

		<label for="polis">Polis:</label>
		<select id="polis" name="polisId" required>
			<option value="" disabled ${empty user.polis ? 'selected' : ''}>Select polis</option>
			<c:forEach var="polisOption" items="${polisList}">
				<option value="${polisOption.id}" ${user.polis.id == polisOption.id ? 'selected' : ''}>${polisOption.name}</option>
			</c:forEach>
		</select>

	</div>
	<!--
	<div>
		<label for="picture" class="w3-text-theme">Upload a profile picture (optional)</label>
		<input type="file" class="w3-input w3-border w3-light-grey"
			   id="picture" name="picture" accept="image/*" />
		<label for="webcamCapture" class="w3-text-theme"> ... or take a picture using your webcam:</label>
		<div class="video-container w3-center">
			<select id="cameraSelect"></select> <br/>
			<div style="position: relative; display: inline-block;">
				<video id="webcam" width="300" height="200" autoplay muted></video>
				<canvas id="overlay" width="300" height="200" style="position: absolute; top: 0; left: 0;"></canvas>
			</div>
			<canvas id="canvas" style="display: none;"></canvas>
			<br/>
			<button type="button" class="w3-btn w3-theme" id="captureBtn">Capture Image</button>
		</div>
	</div>
	-->
	<br/>
	<button type="submit" class="w3-btn w3-theme"> Register </button>

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
	initValidation(window.App.Errors);
	initCamera();
</script>

