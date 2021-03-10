package bllValidators;

/**
 * Validates fields of an order
 */
public class OrderValidator {
	
	/**
	 * Validates the order's quantity
	 * @param quantity The given quantity given as a string
	 * @return true if the quantity is valid, false otherwise
	 */
	public boolean isQuantityValid(String quantity) {
		return quantity.matches("^[ 0-9]+$"); //should contain only digits
	}
	
}
