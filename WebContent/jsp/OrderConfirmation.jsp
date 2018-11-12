<%@page language="java" contentType="text/html"%>
<%@page import="java.util.*"%>
<%@page import="eshop.beans.CartItem"%>
  <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <title>Order</title>
  <link rel="stylesheet" href="/eshop/css/eshop.css" type="text/css"/>
  </head>
<body>
<jsp:include page="TopMenu.jsp" flush="true"/>
<jsp:include page="LeftMenu.jsp" flush="true"/>
<div class="content">
  <h2>Order</h2>

      <p class="info">
        Thank you for your purchase.<br/>
        Your Order Number is: <c:out value="${orderId}"/>
        </p>

  </div>
</body>
</html>
