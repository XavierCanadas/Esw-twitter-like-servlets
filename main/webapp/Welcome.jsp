<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div id="${user.id}" class="w3-container w3-card w3-text-theme w3-round w3-center">
  <h4>My Profile</h4>
  <p><img src="/images/${user.picture}" class="w3-circle" style="height:106px;width:106px" alt="Avatar"></p>
  <hr>
  <p class="w3-left-align"> <i class="fa fa-id-card fa-fw w3-margin-right"></i> ${user.username} </p>
 </div>
<br>