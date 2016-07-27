<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Drink's QR code generator</title>
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
<form method="post" action="generateQRCode">
   <table>  
     <tr>  
      <td>Drink Id :</td>  
      <td><input id="drinkId" name="drinkId" path="drinkId" /></td>
     </tr>
     <tr>
      <td> </td>  
      <td><input type="submit" value="Generate QR Code" /></td>  
     </tr>
     </table>
</form>
    
</br>
<a href="logout">Logout></a>
<br></br>
<img  src="data:image/${qrCodeFormat};base64,${qrCodeImage}" />
<table>
<tr>
    <td>serverUrl</td>
    <td>drinkName</td>
    <td>drinkPrice</td>
    <td>drinkSize</td>
    <td>drinkBarId</td>
    <td>codeData</td>
</tr>
<tr>
	<td>${serverUrl}</td>
	<td>${drinkName}</td>
	<td>${drinkPrice}</td>
	<td>${drinkSize}</td>
	<td>${drinkBarId}</td>
	<td>${codeData}</td>
	</tr>
</table>
</body>
</html>