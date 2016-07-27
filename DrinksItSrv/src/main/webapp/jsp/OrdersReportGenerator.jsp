<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Orders Report Generator</title>
<meta charset="utf-8">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">
<script>
	$(function() {
		$("#dateStart").datepicker();
		$("#dateEnd").datepicker();
	});
</script>
<style>
body {
	font-size: 20px;
	color: teal;
	font-family: Calibri;
}

td {
	font-size: 15px;
	color: black;
	width: 100px;
	height: 22px;
	text-align: center;
}

.heading {
	font-size: 18px;
	color: white;
	font: bold;
	background-color: orange;
	border: thick;
}
</style>
</head>
<body>
<a href="DrinksItDefault">Back to Default></a>
<%
		/*String userId = (String) session.getAttribute("userId");
		if ((userId == null) || (userId == "")) {
			response.sendRedirect("/login");
		}*/
	%>
<br></br>
<p>${message}</p>
	<form method="post" action="getOrdersReport" target="_blank">
		<table>
			<tr>
				<td>Start Date:</td>
				<td><input type="text" name="dateStart" id="dateStart"></td>
			</tr>
			<tr>
				<td>End Date:</td>
				<td><input type="text" name="dateEnd" id="dateEnd"></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Generate Report" /></td>
			</tr>
		</table>
	</form>
	<br></br>
	<a href="logout">Logout></a>
</body>
</html>