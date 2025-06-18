<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Barra de Navegación izquierda</title>
</head>
<body>
<c:choose>
<c:when test="${empty sessionScope.user}">
<p> </p>
</c:when>
<c:otherwise>
<div class="lista-botones-vertical" style="margin-top: 16px">
  <a href="Timeline" class="menu w3-button w3-margin-bottom"><i class="fa fa-newspaper-o fa-fw" style="margin-right: 8px" ></i>Posts</a>
  <a href="" class="menu w3-button w3-margin-bottom"><i class="fa fa-gamepad fa-fw" style="margin-right: 8px"></i>Juegos</a>
  <a href="" class="menu w3-button "><i class="	fa fa-bank fa-fw" style="margin-right: 8px"></i>Gobierno</a>
</div>
</c:otherwise>
</c:choose>
</body>
</html>