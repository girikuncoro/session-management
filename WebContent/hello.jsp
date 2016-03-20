<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Project1a - Giri Kuncoro</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<style>.btn:focus { outline: none !important; }</style>
<script src="js/jquery-2.2.2.min.js"></script>
</head>
<body>

<div class="container">
    <div class="row" style="margin-top: 10px;">
      <div class="col-sm-6">
        <ul class="list-group">
          <li class="list-group-item active"><b>NetId:</b> gk256</li>
          <li class="list-group-item"><b>Session:</b> <span id="session"></span></li>
          <li class="list-group-item"><b>Version:</b> <span id="version"></span></li>
          <li class="list-group-item"><b>Date:</b> <span id="date"></span></li>
        </ul>
      </div>
    </div>
    <div class="jumbotron">
      <h1 id="user"></h1>
      <div>
		<input id="message" class="form-control" style="margin-bottom: 10px;" name="message" value="" type="text" maxlength="500"/>
		<input id="replace" class="btn btn-lg btn-primary request" name="replace" value="Replace" type="button"/>
		<input id="refresh" class="btn btn-lg btn-primary request" name="refresh" value="Refresh" type="button"/>
		<input id="logout" class="btn btn-lg btn-primary request" name="logout" value="Logout" type="button"/>
	  </div>
    </div>
	<div class="row" style="margin-top: 10px;">
      <div class="col-sm-6">
        <ul class="list-group">
          <li class="list-group-item"><b>Cookie:</b> <span id="cookie"></span></li>
          <li class="list-group-item"><b>Expires:</b> <span id="expire"></span></li>
        </ul>
      </div>
    </div>
</div>

<script>
	var servletCall = function(params) {
		$.ajax({
			url: "hello",
			type: "GET",
			datatype: "text",
			data: params,
			success: function(data) {
				console.log(data);
				$("#user").text(data.message);
				$("#session").text(data.session);
				$("#version").text(data.version);
				$("#date").text(data.curr);
				$("#cookie").text(data.cookie);
				$("#expire").text(data.expire);
			}
		});
	}
	
	$(document).ready(function() {
		servletCall({ param: "init" });
	});

	$(".request").click(function() {
		console.log("not coming!! ");
		console.log($("#message").val());
		
		servletCall({ param: $(this).attr("name"), msg: $("#message").val() });
		if($(this).attr("name") == "logout") {
			window.location.href = "logout.jsp";
		}
	});

</script>
</body>
</html>
