package businessLogic;

import java.util.ArrayList;
import java.util.List;

import bllValidators.ProductValidator;
import dataAccess.ProductDAO;
import model.Product;

/**
 * Contains the methods which implement the product related commands:
 * -insert product
 * -delete product
 * -generate report
 */
public class ProductBll {

	private ProductValidator v = new ProductValidator();
	private ProductDAO dao = new ProductDAO();
	private PDF pdf = new PDF();
	
	/**
	 * Eliminates extra spaces form a string
	 * @param s The original string
	 * @return The string without extra spaces
	 */
	private String getString(String s) {
		String product = s.replaceAll("\\s+", " "); //eliminate extra spaces
		if (product.startsWith(" ")) {
			product = product.substring(1); //eliminate space at the begining
		}
		if (product.endsWith(" ")) {
			product = product.substring(0, product.length() - 1); //eliminate space at the end
		}
		return product;
	}
	
	/**
	 * Implements the "Insert product" command
	 * @param s The arguments of the command
	 */
	public void insertProduct(String s) {
		String product = getString(s);
		Product p = new Product();
		String name = product.substring(0, product.indexOf(',')); //the name is the first part of the given string; until ','
		if (!v.isNameValid(name)) { //if the name is not valid, return
			return;
		}
		p.setProductName(getString(name)); //if the name is valid, set the name
		String aux = product.substring(product.indexOf(',') + 1);
		String stock = getString(aux.substring(0, aux.indexOf(','))); //the stock is the second part of the given string; between the two ','
		if (!v.isStockValid(stock)) { //if the stock is not valid, return
			return;
		}
		p.setStock(Integer.parseInt(stock)); //if the stock is valid, set the stock
		String price = getString(aux.substring(aux.indexOf(',') + 1)); //the price is the third part of the given string; from the second ',' until the end
		if (!v.isPriceValid(price)) { //if the price is not valid, return
			return;
		}
		p.setPrice(Float.parseFloat(price)); //if the price is valid, set the price
		if (dao.getIdByName(p.getProductName()) == 0) { //if there is no product with this name insert the product
			p.setProductId(dao.getAvailableId());
			dao.insert(p);
		}
		else { //otherwise update the existing product
			p.setProductId(dao.getIdByName(p.getProductName())); 
			updateProduct(p);
		}
	}
	
	/**
	 * Implements an update for a product
	 * @param p The product to be updated
	 */
	private void updateProduct(Product p) {
		Product newP = new Product();
		newP.setProductId(p.getProductId()); //keep the id
		newP.setProductName(p.getProductName()); //keep the name
		List<Product> products = dao.findById(p.getProductId());
		if (!products.isEmpty()) {
			Product oldP = products.get(0);
			newP.setStock(p.getStock() + oldP.getStock()); //increment the stock with the new stock
			newP.setPrice(p.getPrice()); //set the new price
			dao.update(2, oldP, newP); //update the stock
			dao.update(3, oldP, newP ); //update the price
		}	
	}
	
	/**
	 * Implements the "Delete product" command
	 * @param s The arguments of the command
	 */
	public void deleteProduct(String s) {
		String product = getString(s);
	
		if (!v.isNameValid(product)) { //if the name is not valid, return
			return;
		}
		List<Product> existent = dao.findById(dao.getIdByName(product)); //get the id of the product with the given name
		if (!existent.isEmpty()) {
			dao.delete(existent.get(0)); //delete it
		}
	}
	
	/**
	 * Implements the "Report product" command
	 * @param name The name of the PDF report
	 */
	public void generateReport(String name) {
		List<Product> products = dao.findAll();
		List<String> headers = new ArrayList<String>(); //add the headers of the client table
		headers.add("ID");
		headers.add("Name");
		headers.add("Stock");
		headers.add("Price");
		
		List<String> contents = new ArrayList<String>();
		for (Product p: products) { //add the content; the fields of each object in the table
			contents.add("" + p.getProductId());
			contents.add(p.getProductName());
			contents.add("" + p.getStock());
			contents.add("" + p.getPrice());
		}
		
		pdf.generateReport(4, name, headers, contents);
		
	}
}
