package bllValidators;

/**
 * Validates fields of a client
 */
public class ClientValidator {

	/**
	 * Validates the client's name
	 * @param name The given name
	 * @return true if the name is valid, false otherwise
	 */
	public boolean isNameValid(String name) {
		return name.matches("^[ A-Za-z]+$"); //should contain only letters
	}
	
	/**
	 * Validates the client's address
	 * @param address The given address
	 * @return true if the address is valid, false otherwise
	 */
	public boolean isAddressValid(String address) {
		return address.matches("^[ A-Za-z1-9,.-]+$"); //should contain only letters, digits and '.', ',', '-'
	}
	
}
