<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="ru.drinksit.auxillary.database.OrderStatus"%>
<%@ page import="ru.drinksit.auxillary.database.Order"%>

<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DrinksIt Orders Dashboard</title>
<head>
<title>Orders Dashboard</title>
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
	<%
		String message = (String) request.getAttribute("message");
		if (message != null) {
			out.println("<p>" + message + "</p>");
		}
	%>
	<center>
		<b>Table of orders</b>
		<table border="1">
			<tr>
				<td class="heading">Creation Time</td>
				<td class="heading">Update Time</td>
				<td class="heading">Order Id</td>
				<td class="heading">Drink Id</td>
				<td class="heading">Drink Name</td>
				<td class="heading">Size</td>
				<td class="heading">Quantity</td>
				<td class="heading">Status</td>
			</tr>
			<%
				List<Order> orderList = (List<Order>) request.getAttribute("orderList");

				if (orderList != null) {
					Iterator<Order> listOrdersIter = orderList.iterator();
					while (listOrdersIter.hasNext()) {
						Order order = listOrdersIter.next();
			%>
			<tr>
				<td><%=order.getCreationTS()%></td>
				<td><%=order.getUpdateTS().toString()%></td>
				<td><%=order.getOrder_id()%></td>
				<td><%=order.getDrink().getDrink_id()%></td>
				<td><%=order.getDrink().getName()%></td>
				<td><%=order.getDrink().getSize()%></td>
				<td><%=order.getQuantity()%></td>
				<td><a
					href="updateOrderStatus?orderId=<%=order.getOrder_id()%>&status=<%=order.getStatus()%>"><%=order.getStatus()%></a></td>
			</tr>
			<%
				}
				}
			%>
		</table>
	</center>
	<br></br>
	<a href="logout">Logout></a>
</html>