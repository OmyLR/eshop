package eshop.beans;

public class CartItem {
  private String author;
  private String title;
  private double price;
  private double priceQuantity;
  private String bookID;
  private String quantity;

	public CartItem(Book book, int quantity) {
		bookID = book.getId();
		title = book.getTitle();
		author = book.getAuthor();
		price = book.getPrice();
		this.quantity = new Integer(quantity).toString();
		calculatePrice();
	  }

	public String getAuthor() { return author; }
	public void setAuthor(String author) { this.author = author; }

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }

	public double getPrice() { return price; }
	public void setPrice(double price) { 
		this.price = price; 
		calculatePrice();
	}

	public String getBookID() { return bookID; }
	public void setBookId(String bookID) { this.bookID = bookID; }

	public String getQuantity() { return quantity; }
	public void setQuantity(String quantity) { 
		this.quantity = quantity; 
		calculatePrice();
	}
	
	public void calculatePrice() {
		 this.priceQuantity = Math.round(Integer.parseInt(quantity)
		    * price * 100.) / 100.;
	}

	public double getPriceQuantity() {
		return priceQuantity;
	}

	public void setPriceQuantity(double priceQuantity) {
		this.priceQuantity = priceQuantity;
	}
	
	
  }
