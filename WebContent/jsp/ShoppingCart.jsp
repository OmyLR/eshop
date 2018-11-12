<%@page language="java" contentType="text/html"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.*"%>
<%@page import="eshop.beans.Book"%>
<%@page import="eshop.beans.CartItem"%>
<jsp:useBean id="dataManager" scope="application"
  class="eshop.model.DataManager"/>
  <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
  <%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
<%
 String base = (String) application.getAttribute("base");
%>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <title>Shopping Cart</title>
  <link rel="stylesheet" href="/eshop/css/eshop.css" type="text/css"/>
  </head>
<body>
<jsp:include page="TopMenu.jsp" flush="true"/>
<jsp:include page="LeftMenu.jsp" flush="true"/>
    <div class="content">
      <h2>Shopping Cart</h2>
      <table>
        <tr>
          <th>Title</th>
          <th>Author</th>
          <th>Price</th>
          <th>Quantity</th>
          <th>Subtotal</th>
          <th>Delete</th>
          </tr>
          
          <c:forEach items="${carrito}" var="libro">
          	<tr>
	            <td><c:out value="${libro.value.title}"/></td>
	            <td><c:out value="${libro.value.author}"/></td>
	            <td><c:out value="${libro.value.price}"/></td>
	            <td>
	           		<form>
	              		<input type="hidden" name="action" value="updateItem"/>
	              		<input type="hidden" name="bookId" value="${libro.value.bookID}"/>
	              		<input type="number" size="2" name="quantity" value="${libro.value.quantity}"/>	             
	              		<input type="submit" value="Update"/>
	              </form>
	            </td>
	            <td>
	              <c:out value="${libro.value.priceQuantity}"/>
	            </td>
	            <td>
	            	<form>
		              <input type="hidden" name="action" value="deleteItem"/>
		              <input type="hidden" name="bookId" value="${libro.value.bookID}"/>
		              <input type="submit" value="Delete"/>
	              </form>
	              </td>
            </tr>
          </c:forEach> 

        <tr>
          <td colspan="5" id="total">Total: <fmt:formatNumber type = "number" maxIntegerDigits = "3" value = "${totalPrice}" /></td>
          <td class="total">&nbsp;</td>
          </tr>
        <tr>
          <td colspan="6" class="center"><a class="link1"
            href="<%=base%>?action=checkOut">Check Out</a></td>
          </tr>
        </table>
      </div>
      <c:if test="${mensaje != null} ">
		<p class="info">The Shopping cart is empty.</p>
	   </c:if>
 
</body>
</html>
