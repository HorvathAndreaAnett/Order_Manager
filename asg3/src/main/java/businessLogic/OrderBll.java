package businessLogic;

import dataAccess.*;
import model.*;

import java.util.ArrayList;
import java.util.List;

import bllValidators.*;

/**
 * Contains the methods which implement the order related commands:
 * -order
 * -generate report
 */
public class OrderBll {

	private ClientValidator cValid = new ClientValidator();
	private ProductValidator pValid = new ProductValidator();
	private OrderValidator oValid = new OrderValidator();
	private ProductDAO pdao = new ProductDAO();
	private ClientDAO cdao = new ClientDAO();
	private OrderDAO odao = new OrderDAO();
	private OrderedProductDAO opdao = new OrderedProductDAO();
	private PDF pdf = new PDF();

	/**
	 * Represents the current number of the bill.
	 * Used to generate distinct names for the bills.
	 */
	private static int billNr = 1;
	
	/**
	 * Eliminates extra spaces form a string
	 * @param s The original string
	 * @return The string without extra spaces
	 */
	private String getString(String s) {
		String order = s.replaceAll("\\s+", " "); //eliminate extra spaces
		if (order.startsWith(" ")) {
			order = order.substring(1); //eliminate space at the begining
		}
		if (order.endsWith(" ")) {
			order = order.substring(0, order.length() - 1); //eliminate space at the end
		}
		return order;
	}
	
	/**
	 * Implements the "Order" command
	 * @param order The arguments of the command
	 */
	public void addOrder(String order) {
		Ordert o = new Ordert();
		OrderedProduct op = new OrderedProduct();
		String name = getString(order.substring(0, order.indexOf(','))); //the name is the first part of the given string; until ','
		if ((!cValid.isNameValid(name)) || cdao.getIdByName(name) == 0) { //if the name is not valid or if the client does not exist then return
			return;
		}
		o.setClientId(cdao.getIdByName(name)); //if the name is valid, set the id corresponding to that name
		String aux = order.substring(order.indexOf(',') + 1);
		String product = getString(aux.substring(0, aux.indexOf(','))); //the product is the second part of the given string; between the two ','
		if ((!pValid.isNameValid(product)) || pdao.getIdByName(product) == 0) { //if the product name is not valid or the product does not exist then return
			return;
		}
		op.setProductId(pdao.getIdByName(product)); //if he product is valid, set the id corresponding to the product
		String quantity = getString(aux.substring(aux.indexOf(',') + 1)); //the quantity is the third part of the given string; from the second ',' until to the end
		if (!oValid.isQuantityValid(quantity)) { //if the quantity is not valid, then return
			return;
		}
		op.setQuantity(Integer.parseInt(quantity)); //if the quantity is valid, set the quantity
		List<Product> products = pdao.findById(op.getProductId());
		if (op.getQuantity() > products.get(0).getStock()) { //if the quantity is bigger than the stock, generate an under-stock messsage
			pdf.generateMessage("bill" + billNr, "Not enough products for the desired order");
			billNr++;
			return;
		}
		Product newP = products.get(0); //get the product with the given id
		newP.setStock(newP.getStock() - op.getQuantity()); //change its stock; decrement with quantity
		o.setTotal(op.getQuantity() * products.get(0).getPrice()); //set the total price
		
		insertOrUpdate(o, op); //insert or update in tables ordert and orderedProduct
		
		pdao.update(2, products.get(0), newP); //update the product's stock
		pdf.generateBill("bill" + billNr, o.getOrderId(), name,  product, op.getQuantity(), o.getTotal()); //generate bill
		billNr++;
	}
	
	/**
	 * Implements the insertion or update of an order
	 * @param o The Ordert object to be inserted/updated
	 * @param op The OrderedProduct object to be inserted/updated
	 */
	private void insertOrUpdate(Ordert o, OrderedProduct op) {
		if (odao.getIdByClientId(o.getClientId()) == 0) { //if the client did not ordered before
			o.setOrderId(odao.getAvailableId()); //set an available id in ordert
			op.setOrderId(o.getOrderId()); //set the order's id in orderdProduct
			op.setOrderedProductId(opdao.getAvailableId()); //set an available id in orderedProduct
			//execute insertions
			odao.insert(o);
			opdao.insert(op);
		}
		else { //if the client has ordered before
			o.setOrderId(odao.getIdByClientId(o.getClientId())); //set the previous order's id for ordert
			op.setOrderedProductId(opdao.getAvailableId()); //set available id in orderedProduct
			op.setOrderId(o.getOrderId()); //set the order's id in orderdProduct
			opdao.insert(op); //make insertion in orderedProduct
			List<Ordert> orders = odao.findById(odao.getIdByClientId(o.getClientId()));
			Ordert newO = o;
			newO.setTotal(o.getTotal() + orders.get(0).getTotal());
			odao.update(2,  o, newO); //update the total in ordert for the previous command
		}
	}
	
	/**
	 * Implements the "Report order" command
	 * @param name The name of the PDF report
	 */
	public void generateReport(String name) {
		OrderDAO dao = new OrderDAO();
		List<Ordert> orders = dao.findAll();
	
		List<String> headers = new ArrayList<String>(); //add the headers of the client table
		headers.add("ID");
		headers.add("Client ID");
		headers.add("Total");
		
		List<String> contents = new ArrayList<String>();
		for (Ordert o: orders) { //add the content; the fields of each object in the table
			contents.add("" + o.getOrderId());
			contents.add("" + o.getClientId());
			contents.add("" + o.getTotal());
		}
	
		pdf.generateReport(3, name, headers, contents);
	}
}
