<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<link rel="stylesheet" href="css/style.css">
<title>Register Form</title>
</head>
<body>

	<form id="registerForm" action="Register" method="POST">

		<label for="username">Name:</label>
		<input type="text" id="username" name="username" required minlength="2" value="${user.username}" title="the username must be beetween 2 and 15 characters."/>

		<label for="password">Password:</label> 
		<input type="password" id="password" name="password" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*]).{8,}$" value="${user.password}" title="Minimum 8 characters, including uppercase, number, and special character: *[!@#$%^&*]" />

		<label for="confirmPassword">Repeat password:</label> 
		<input type="password" id="confirmPassword" name="confirmPassword" required value="${user.password}" title="Passwords must match" />

		<label for="gender">Gender:</label>
		<select id="gender" name="gender" required>
			<option value="" disabled ${empty user.gender ? 'selected' : ''}>Select gender</option>
			<option value="male" ${user.gender == 'male' ? 'selected' : ''}>Male</option>
			<option value="female" ${user.gender == 'female' ? 'selected' : ''}>Female</option>
			<option value="non-binary" ${user.gender == 'non-binary' ? 'selected' : ''}>Non Binary</option>
			<option value="indeterminate" ${user.gender == 'indeterminate' ? 'selected' : ''}>Indeterminate</option>
		</select>

		<label for="birthdate">Birth Date:</label>
		<input type="date" id="birthdate" name="birthdate" required value="${user.birthdate}" />

		<label for="polis">Polis:</label>
		<select id="polis" name="polis" required>
			<option value="" disabled ${empty user.polis ? 'selected' : ''}>Select polis</option>
			<!-- todo: add polis -->
			<option value="option1" ${user.polis == 'option1' ? 'selected' : ''}>Option 1</option>

		</select>

		<button type="submit">Send</button>
	</form>
	
	<!--  
	<ul>
		<c:forEach var="error" items="${errors}">
			<li>${error.key} -> ${error.value}</li>
		</c:forEach>
	</ul>
	-->
	
	<script>
		// Server errors to JS object
	    const serverErrors = {
	      <c:forEach var="error" items="${errors}">
	        "${error.key}": "${error.value}",
	      </c:forEach>
	    };
	</script>
	<script src="js/validation.js"></script>


</body>
</html>