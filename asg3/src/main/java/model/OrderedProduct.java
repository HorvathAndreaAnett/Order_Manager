package model;

/**
 * Represents an ordered product
 */
public class OrderedProduct {

	/**
	 * Represents the orderd product's id
	 */
	private int orderedProductId;
	/**
	 * Represents the order's id
	 */
	private int orderId;
	/**
	 * Represents the product's id
	 */
	private int productId;
	/**
	 * Represents the quantity
	 */
	private int quantity;
	
	public OrderedProduct() {
	}

	/**
	 * Gets the ordered product's id
	 * @return An int representing the ordered product's id
	 */
	public int getOrderedProductId() {
		return orderedProductId;
	}
	
	/**
	 * Sets the ordered product's id
	 * @param orderedProductId The ordered product's id
	 */
	public void setOrderedProductId(int orderedProductId) {
		this.orderedProductId = orderedProductId;
	}
	
	/**
	 * Gets the id of the product
	 * @return An int representing the product's id
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
	 * Gets the order's id
	 * @return An int representing the order's id
	 */
	public int getOrderId() {
		return orderId;
	}
	/**
	 * Sets the order's id
	 * @param orderId The order's id
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	/**
	 * Gets the quantity
	 * @return An int representing the quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * Sets the quantity
	 * @param quantity The quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
