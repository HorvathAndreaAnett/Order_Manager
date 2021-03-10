package model;

/** 
 * Represents a client
 */
public class Client {
	
	/**
	 * Represents the id of a client
	 */
	private int clientId;
	/**
	 * Represents the name of the client
	 */
	private String clientName;
	/**
	 * Represents the address of the client
	 */
	private String address;
	
	
	public Client() {
	}

	/**
	 * Gets the id of a client
	 * @return An int representing the id of a client
	 */
	public int getClientId() {
		return clientId;
	}
	
	/**
	 * Sets the client's id
	 * @param clientId The client's is
	 */
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	
	/**
	 * Gets the name of a client
	 * @return A string representing the name of a client
	 */
	public String getClientName() {
		return clientName;
	}
	
	/**
	 * Sets the client's name
	 * @param clientName The client's name
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	/**
	 * Gets the address of a client
	 * @return A string representing the address of a client
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Sets the client's address
	 * @param address The client's address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
}
