<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<link rel="stylesheet" href="css/style.css">
<title>Register Form</title>
</head>
<body>

	<form id="registerForm" action="Register" method="POST">

		<label for="email">Email:</label>
		<input type="email" id="email" name="email" required pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}" value="${user.email}" title="Enter a valid email address" />

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

		<input type="date" id="birthdateString" name="birthdateString" required
			   value="<c:if test="${not empty user.birthdateString}"><fmt:formatDate pattern="yyyy-MM-dd" value="${user.birthdateString}"/></c:if>" />

		<label for="polis">Polis:</label>
		<select id="polis" name="polisId" required>
			<option value="" disabled ${empty user.polis ? 'selected' : ''}>Select polis</option>
			<c:forEach var="polisOption" items="${polisList}">
				<option value="${polisOption.id}" ${user.polis.id == polisOption.id ? 'selected' : ''}>${polisOption.name}</option>
			</c:forEach>
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