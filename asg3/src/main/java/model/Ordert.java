package model;

/**
 * Represents an order
 */
public class Ordert {
	
	/**
	 * Represents the id of an order
	 */
	private int orderId;
	/**
	 * Represents the id of the client
	 */
	private int clientId;
	/**
	 * Represents the total cost
	 */
	private float total;	
	
	public Ordert() {
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
	 * Gets the client's id
	 * @return An int representing the id of client
	 */
	public int getClientId() {
		return clientId;
	}
	
	/**
	 * Sets the client's id
	 * @param clientId The client's id
	 */
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	
	/**
	 * Gets the total cost of the order
	 * @return An int representing the total cost of the order
	 */
	public float getTotal() {
		return total;
	}
	
	/**
	 * Sets the total cost of the order
	 * @param total The total cost of the order
	 */
	public void setTotal(float total) {
		this.total = total;
	}
	
	

}
