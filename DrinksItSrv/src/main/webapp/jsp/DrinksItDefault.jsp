<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DrinksIt</title>
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
	<%
		/*String userId = (String) session.getAttribute("userId");
		if ((userId == null) || (userId == "")) {
			response.sendRedirect("/login");
		}*/
	%>
	<a href="OrdersDashboard">Orders Dashboard</a> or
	<a href="QRCodeGenerator">QR Code Generator</a> or
	<a href="OrdersReportGenerator">Orders Report Generator</a>
	</br>
	</br>
	<a href="logout">Logout></a>
</body>
</html>