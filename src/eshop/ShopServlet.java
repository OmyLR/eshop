package eshop;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import eshop.beans.Book;
import eshop.beans.CartItem;
import eshop.beans.Customer;
import eshop.model.DataManager;

import java.util.HashMap;
import java.util.Hashtable;

public class ShopServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	private static final long serialVersionUID = 1L;
	private HashMap<String, CartItem> shoppingCart;

	public ShopServlet() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		System.out.println("*** initializing controller servlet.");
		super.init(config);

		DataManager dataManager = new DataManager();
		dataManager.setDbURL(config.getInitParameter("dbURL"));
		dataManager.setDbUserName(config.getInitParameter("dbUserName"));
		dataManager.setDbPassword(config.getInitParameter("dbPassword"));

		ServletContext context = config.getServletContext();
		context.setAttribute("base", config.getInitParameter("base"));
		context.setAttribute("imageURL", config.getInitParameter("imageURL"));
		context.setAttribute("dataManager", dataManager);

		try { // load the database JDBC driver
			Class.forName(config.getInitParameter("jdbcDriver"));
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		}
	}

	protected void addItem(HttpServletRequest request, DataManager dm) {
		if (shoppingCart == null) {
			shoppingCart = new HashMap<String, CartItem>(10);
		}
		try {
			String bookId = request.getParameter("bookId");
			Book book = dm.getBookDetails(bookId);
			if (book != null) {
				CartItem item = new CartItem(book, 1);
				shoppingCart.remove(bookId);
				shoppingCart.put(bookId, item);
				request.getSession().setAttribute("carrito", shoppingCart);
				calculateTotalPrice(request);
			}
		} catch (Exception e) {
			request.setAttribute("mensjae", "Error adding the selected book to the shopping cart!");
		}
	}

	protected void updateItem(HttpServletRequest request) {
		try {
				System.out.println("Actualizando!!");
		      String bookId = request.getParameter("bookId");
		      String quantity = request.getParameter("quantity");
		      CartItem item = shoppingCart.get(bookId);
		      if (item != null) {
		    	  System.out.println("no está nulo!");
		    	  item.setQuantity(quantity);
		    	  calculateTotalPrice(request);
		      }
		}catch (Exception e) {
		     // out.println("Error updating shopping cart!");
			request.setAttribute("mensaje", "Error updating shopping cart!");
		}
	}

	protected void deleteItem(HttpServletRequest request) {
		try {
		      String bookId = request.getParameter("bookId");
		      shoppingCart.remove(bookId);
		      calculateTotalPrice(request);
		} catch (Exception e) {
			request.setAttribute("mensaje", "Error updating shopping cart!");
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String base = "/jsp/";
		String url = base + "index.jsp";
		String action = request.getParameter("action");
		// recuperar  datamanager del contexto
		DataManager datamanager = (DataManager) request.getServletContext().getAttribute("dataManager");
		shoppingCart = getShoppingCart(request);
		if (action != null) {
			switch (action) {
			case "search":
				url = base + "SearchOutcome.jsp";
				break;
			case "selectCatalog":
				url = base + "SelectCatalog.jsp";
				break;
			case "bookDetails":
				url = base + "BookDetails.jsp";
				break;
			case "checkOut":
				url = base + "Checkout.jsp";
				break;
			case "orderConfirmation":
				orderConfirmation(request, datamanager);
				url = base + "OrderConfirmation.jsp";
				break;
			case "addItem":
				addItem(request, datamanager);
				url = base + "ShoppingCart.jsp";
				break;
			case "updateItem":
				updateItem(request);
				url = base + "ShoppingCart.jsp";
				break;
			case "deleteItem":
				deleteItem(request);
				url = base + "ShoppingCart.jsp";
				break;
			case "showCart":
				url = base + "ShoppingCart.jsp";
				break;
			}
		}
		RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
		requestDispatcher.forward(request, response);
	}
	
	private HashMap<String, CartItem> getShoppingCart(HttpServletRequest request){
		return (HashMap<String, CartItem>)request.getSession().getAttribute("carrito");
	}
	
	private void calculateTotalPrice(HttpServletRequest request) {
		double totalPrice = 0;
		for(CartItem book : shoppingCart.values()) {
			totalPrice += book.getPriceQuantity();
		}
		request.getSession().setAttribute("totalPrice", totalPrice);
	}
	
	private void orderConfirmation(HttpServletRequest request, DataManager dataManager) {
		Customer customer = new Customer();
		customer.setCcExpiryDate(request.getParameter("ccExpiryDate"));
		customer.setCcName(request.getParameter("ccName"));
		customer.setCcNumber(request.getParameter("ccNumber"));
		customer.setDeliveryAddress(request.getParameter("deliveryAddress"));
		long orderId = dataManager.insertOrder(customer, shoppingCart);
	    if (orderId > 0L) {
	      request.getSession().setAttribute("orderId", orderId);
	      request.getSession().removeAttribute("carrito");
	      request.getSession().removeAttribute("totalPrice");
	      shoppingCart = null;
	    }
	}
		
	
}
