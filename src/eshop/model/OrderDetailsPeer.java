package eshop.model;

import java.util.Hashtable;
import java.util.Enumeration;
import java.util.HashMap;
import java.sql.SQLException;
import java.sql.Statement;
import eshop.beans.CartItem;

public class OrderDetailsPeer {

  public static void insertOrderDetails(Statement stmt, long orderId,
      HashMap<String, CartItem> shoppingCart) throws SQLException {
	    String sql;
	    for(CartItem item : shoppingCart.values()) {
	    	sql = "insert into order_details (order_id, book_id, quantity,"
	    	          + " price, title, author) values ('" + orderId + "','"
	    	          + item.getBookID() + "','" + item.getQuantity() + "','"
	    	          + item.getPrice() + "','" + item.getTitle() + "','"
	    	          + item.getAuthor() + "')"
	    	          ;
	    	      stmt.executeUpdate(sql);
	    }
    
    }
  }
