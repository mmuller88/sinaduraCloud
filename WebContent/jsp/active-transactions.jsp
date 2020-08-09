<%@page import="net.zylk.sinadura.cloud.model.TransactionVO"%>

<%@page import="java.util.List"%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	List<TransactionVO> transactions = (List<TransactionVO>) request.getAttribute("transactions");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Active transactions status</title>
</head>

<body>

<%
	if (transactions != null && transactions.size() > 0) {
			
		out.println("<p>Hay <b>" + transactions.size() + "</b> transaciones activas</p>");
		for (TransactionVO transaction : transactions) {
%>
			<b>TransactionId :: <%=transaction.getUuid()%></b>
			<pre><%=transaction.toString()%></pre>
<%
		}
		
	} else {
%>
		<h4>No hay transacciones activas</h4>
<%
	}
%>

</body>
</html>