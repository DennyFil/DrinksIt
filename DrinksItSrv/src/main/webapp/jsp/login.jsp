<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <title>DrinksIt Login</title>
   <link rel="stylesheet" type="text/css" href="styles.css"/>
</head>
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
 text-align: left;
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
<b><h1>Login</h1></b>
<%
   String message = (String) request.getAttribute("message");
   if (message != null) {
      out.println("<p>" + message + "</p>");
   }
%>

<form method="post" action="checkCreds">
   <table>
     <tr>
      <td>Username :</td>
      <td><input id="userName" name="userName" path="userName" /></td>
     </tr>
     <tr>
      <td>Password :</td>
      <td><input id="password" name="password" type="password" path="password" /></td>
     </tr>
     <tr>
      <td> </td>
      <td><input type="submit" value="Login" /></td>
     </tr>
     </table>
</form>
</body>
</html>