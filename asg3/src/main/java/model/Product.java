package model;

/**
 * Represents a product
 */
public class Product {
	
	/**
	 * Represents the id of a product
	 */
	private int productId;
	/**
	 * Represents the name of a product
	 */
	private String productName;
	/**
	 * Represents the stock of a product
	 */
	private int stock;
	/**
	 * Represents the price of a product
	 */
	private float price;
	
	
	public Product() {
	}

	/**
	 * Gets the id of a product
	 * @return An int representing the id of a product
	 */
	public int getProductId() {
		return productId;
	}

	/**
	 * Sets the product's id
	 * @param productId The product's id
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}

	/**
	 * Gets the name of a product
	 * @return A string representing the name of a product
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * Sets the product's name
	 * @param productName The product's name
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 Gets the price of a product
	 * @return A float representing the price of a product
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * Sets the product's price
	 * @param price The product's price
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	/**
	 Gets the stock of a product
	 * @return An int representing the stock of a product
	 */
	public int getStock() {
		return stock;
	}

	/**
	 * Sets the product's stock
	 * @param stock The product's stock
	 */
	public void setStock(int stock) {
		this.stock = stock;
	}
	

}
