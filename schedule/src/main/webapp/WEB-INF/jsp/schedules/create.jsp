<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="utf-8" />
<title>Create Group</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script>
	document.addEventListener("DOMContentLoaded", function(event) {
	    document.getElementById("male").checked = true;
	});
	
	function handleRadioChange(eventType) {
	    window.alert(eventType.value);
	    if(eventType.value == '0'){
	        document.getElementById("task").style.display = "block";
	        document.getElementById("event").style.display = "none";
	    }
	};
</script>
</head>
<body>
	<div class="continer">
		<div align="center">
			<h1>Create Group</h1>
			<br />
			<form action='/schedule/schedules/save' method='post'>
						Event Type
						
							<label for="male">Event </label> 
							<input type="radio" id="male" name="gender" value="1" onChange="handleRadioChange(this)"><br> 
							<label for="female">Task</label> 
							<input type="radio" id="female" name="gender" value="0" onChange="handleRadioChange(this)"><br>
			
						<div id="event" style="display: block;">
							Group Name:
								<input type="text" id="groupName" name="groupName" placeholder="Group Name" class="form-control "> 
						</div>
						<div id="task" style="display: none;">
							Task Name:
								<input type="text" id="groupName" name="groupName" placeholder="Task Name" class="form-control ">
						</div>
							<button type="submit" class="btn btn-primary">Save Group</button>
							<a href="/schedule/schedules">
								<button type="button" class="btn btn-primary">Cancel</button>
						</a>
			</form>
		</div>
	</div>
</body>
</html>