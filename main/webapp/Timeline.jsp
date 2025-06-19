<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
  $(document).ready(function () {
    // Cargas iniciales
    $('#lcolumn').load('NotFollowed');
    $('#rcolumn').load('Navegation');
    $('#iterator').load('Tweets');

    // Botón izquierdo (polis)
    $('#boton1').on('click', function () {
      $(this).addClass('active');
      $('#boton2').removeClass('active');
      $('#iterator').load('Tweets');
    });

    // Botón derecho (siguiendo)
    $('#boton2').on('click', function () {
      $(this).addClass('active');
      $('#boton1').removeClass('active');
      $('#iterator').load('Profile');
    });
  });
</script>

<div class="w3-container w3-card w3-round w3-white w3-section">
	<h6 class="w3-opacity"> ${user.username}, what are you thinking? </h6>
	<p id="tweetContent" contenteditable="true" class="w3-border w3-padding"> </p>
	<button id="addTweet" type="button" class="w3-button w3-theme w3-section"><i class="fa fa-pencil"></i> &nbsp;Post</button> 
</div>
 <div class="w3-row">
  <div class="w3-col s6">
    <a id="boton1" class="w3-button w3-block toggle-btn active">${user.polis.name}</a>
  </div>
  <div class="w3-col s6">
    <a id="boton2" class="w3-button w3-block toggle-btn">Siguiendo</a>
  </div>
</div>
 
 <div id="iterator">
</div>


