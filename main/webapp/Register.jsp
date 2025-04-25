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

		<label for="name">Name:</label> 
		<input type="text" id="name" name="name" required minlength="3" value="${user.name}" title="Username must be beetween 5 and 20 characters."/> 
		
		<label for="password">Password:</label> 
		<input type="password" id="password" name="password" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*]).{8,}$" value="${user.password}" title="Minimum 8 characters, including uppercase, number, and special character: *[!@#$%^&*]" />

		<label for="confirmPassword">Repeat password:</label> 
		<input type="password" id="confirmPassword" name="confirmPassword" required value="${user.password}" title="Passwords must match" />

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