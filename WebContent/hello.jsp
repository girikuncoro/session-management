<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Project1a</title>
<script src="js/jquery-2.2.2.min.js"></script>
</head>
<body>

<div class="container">
	<div>
		<p>NetId: gk256  Session: <span id="session"></span> Version: <span id="version"></span>   Date: <span id="date"></span>  </p>
	</div>
	<h1 id="user"></h1>
	<div>
		<input id="message" name="message" value="" type="text"/>
		<input id="replace" class="request" name="replace" value="Replace" type="submit"/>
		<input id="refresh" class="request" name="refresh" value="Refresh" type="submit"/>
		<input id="logout" class="request" name="logout" value="Logout" type="submit"/>
	</div>
	<div>
		<p>Cookie: <span id="cookie"></span>   Expires: <span id="expire"></span>  </p>
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
