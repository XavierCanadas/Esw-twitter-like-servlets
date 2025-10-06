<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<script type="text/javascript">
    $(document).ready(function () {

        var username = "${user.username}";

        var timelineOption = "${timelineOption}";

        var tweets = "${tweets}";
    });
</script>

<script type="text/javascript">
    $(document).ready(function () {
        var parentId = "${tweet.id}";
        $('#iterator').load('Comments?parentId=' + encodeURIComponent(parentId));
    });
</script>

<div class="w3-container w3-card w3-round w3-white w3-section">
    <h6 class="w3-opacity"> Search tweets </h6>
    <p id="word-query" contenteditable="true" class="w3-border w3-padding"></p>
    <button id="search-input" type="button" class="w3-button boton-relevante w3-section"><i class="fa fa-search"></i> &nbsp;Post
    </button>
</div>

<div id="iterator"></div>


