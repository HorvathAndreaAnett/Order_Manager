package businessLogic;

import java.util.ArrayList;
import java.util.List;

import bllValidators.ClientValidator;
import dataAccess.ClientDAO;
import model.Client;

/**
 * Contains the methods which implement the client related commands:
 * -insert client
 * -delete client
 * -generate report
 */
public class ClientBll {
	
	private ClientValidator v = new ClientValidator();
	private ClientDAO dao = new ClientDAO();
	private PDF pdf = new PDF();
	
	/**
	 * Eliminates extra spaces form a string
	 * @param s The original string
	 * @return The string without extra spaces
	 */
	private String getString(String s) {
		String client = s.replaceAll("\\s+", " "); //eliminate extra spaces
		if (client.startsWith(" ")) {
			client = client.substring(1); //eliminate space at the begining
		}
		if (client.endsWith(" ")) {
			client = client.substring(0, client.length() - 1); //eliminate space at the end
		}
		return client;
	}
	
	/**
	 * Implements the "Insert client" command
	 * @param s The arguments of the command
	 */
	public void insertClient(String s) {
		String client = getString(s);
		Client c = new Client();
		String name = client.substring(0, client.indexOf(',')); //the name is the first part of the given string; until ','
		if (!v.isNameValid(name)) { //if the name is not valid, return
			return;
		}
		c.setClientName(getString(name)); //if the name is valid, set the name
		String address = client.substring(client.indexOf(',') + 1); //the address is the second part of the given string; form ',' until the end
		if (!v.isAddressValid(address)) { //if the address is not valid, return
			return;
		}
		c.setAddress(getString(address)); //if the address is valid, set the address
		c.setClientId(dao.getAvailableId()); //set the client id as the next available id
		dao.insert(c); //insert
	}
	
	/**
	 * Implements the "Delete client" command
	 * @param s The arguments of the command
	 */
	public void deleteClient(String s) {
		String client = getString(s);
		Client c = new Client();

		String name = client.substring(0, client.indexOf(',')); //the name is the first part of the given string; until ',' 
		if (!v.isNameValid(name)) { //if the name is not valid, return
			return;
		}
		c.setClientName(getString(name)); //if the name is valid, set the name
		String address = client.substring(client.indexOf(',') + 1); //the address is the second part of the given string; form ',' until the end
		if (!v.isAddressValid(address)) { //if the address is not valid, return
			return;
		}
		c.setAddress(getString(address)); //if the address is valid, set the address
		c.setClientId(dao.getIdByName(name)); //set the client id as the next available id
		List<Client> existent = dao.findById(c.getClientId()); //search the client with that name
		if (!existent.isEmpty()) { //delete it only if the addresses match
			if ((existent.get(0).getAddress()).equals(c.getAddress())) {
				dao.delete(c);
			}
		}
	}
	
	/**
	 * Implements the "Report client" command
	 * @param name The name of the PDF report
	 */
	public void generateReport(String name) {
		List<Client> clients = dao.findAll();
		List<String> headers = new ArrayList<String>(); //add the headers of the client table
		headers.add("ID");
		headers.add("Name");
		headers.add("Address");
		
		List<String> contents = new ArrayList<String>();
		for (Client c: clients) { //add the content; the fields of each object in the table
			contents.add("" + c.getClientId());
			contents.add(c.getClientName());
			contents.add(c.getAddress());
		}
		
		pdf.generateReport(3, name, headers, contents);
	}

}
