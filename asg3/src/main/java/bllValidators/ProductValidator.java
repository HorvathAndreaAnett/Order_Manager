package bllValidators;

/**
 * Validates fields of a product
 */
public class ProductValidator {
	
	/**
	 * Validates the product's name
	 * @param name The given name
	 * @return true if the name is valid, false otherwise
	 */
	public boolean isNameValid(String name) {
		return name.matches("^[ A-Za-z]+$"); //should contain only letters
	}
	
	/**
	 * Validates the product's stock
	 * @param stock The given stock given as a string
	 * @return true if the stock is valid, false otherwise
	 */
	public boolean isStockValid(String stock) {
		return stock.matches("^[0-9]+$"); //should contain only digits
	}
	
	/**
	 * Validates the product's price
	 * @param price The given price given as a string
	 * @return true if the price is valid, false otherwise
	 */
	public boolean isPriceValid(String price) {
		return price.matches("^[0-9.]+$"); //should contain only digits and '.' for floating point numbers
	}

}
