<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
	<%@ taglib prefix="c" uri="jakarta.tags.core" %>

		<div class="w3-bar">
			<a class="w3-bar-item w3-button icono-navbar" href="index.html"> <img src="assets/Netopolis.png" alt="Home"class="icono-navbar"></img> </a>		
			<a class="menu w3-bar-item w3-button w3-hide-small " href="Profile" data-username="${user.username}">
			  <img src="${user.picture}" class="w3-circle" style="height:40px;width:40px;vertical-align:middle;" alt="Avatar">
			  <span style="margin-left: 5px; font-weight: bold">${user.username}</span>
			</a>
			<a class="menu w3-bar-item w3-hide-small" style="margin-left: auto"> <i class="fa fa-star fa-fw"></i> ${user.socialCredit} </a>
			<a class="menu w3-bar-item w3-hide-small ">Clase Social
			  <img src="${pageContext.request.contextPath}/assets/esclavo.png" class="w3-circle" style="height:45px;width:45px;vertical-align:middle;" alt="Avatar"></img>
			</a>
			<a class="menu w3-bar-item w3-hide-small ">Oficio
			  <img src="${pageContext.request.contextPath}/assets/detenido.png"  style="height:35px;width:35px;vertical-align:middle;" alt="Avatar"></img>
			</a>
			<a class="menu w3-bar-item w3-button w3-hide-small " href="Followed"> Buddies </a>
			<c:if test="${user.isAdmin}">
        		<a class="menu w3-bar-item w3-button w3-hide-small" href="Users">Admin Users</a>
    		</c:if>
			<a href="javascript:void(0)" class="w3-bar-item w3-button w3-right w3-hide-large w3-hide-medium"
				onclick="App.stack()">&#9776;</a>
		</div>

		<div id="stack" class="w3-bar-block w3-hide w3-hide-large w3-hide-medium">
			<a class="menu w3-bar-item w3-button" href="Timeline"> Timeline </a>
			<a class="menu w3-bar-item w3-button" href="Followed"> Buddies </a>
			<a class="menu w3-bar-item w3-button" href="Profile"> Profile </a>
			<a class="menu w3-bar-item w3-button" href="NotFollowed"> Discover </a>
			<a class="menu w3-bar-item w3-button" href="Logout"> Logout </a>
			<c:if test="${user.isAdmin}">
        		<a class="menu w3-bar-item w3-button" href="Users">Admin Users</a>
    		</c:if>
		</div>