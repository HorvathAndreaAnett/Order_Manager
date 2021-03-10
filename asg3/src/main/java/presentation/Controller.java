package presentation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import businessLogic.*;

/**
 * Parses the input file
 * Distributes the commands to other methods
 * Contains the main method
 */
public class Controller {
	
	private File input;
	private Scanner scan;
	
	/**
	 * Parses the input file
	 * Distributes the commands to other methods
	 * @param inputs The input file's name
	 * @throws FileNotFoundException
	 */
	public Controller(String inputs) throws FileNotFoundException {
		
		this.input = new File(inputs);
		scan = new Scanner(input);
		
		ClientBll client = new ClientBll();
		ProductBll product = new ProductBll();
		OrderBll order = new OrderBll();
		//used for generating distinct names for the reports
		int clientReportNr = 1; 
		int productReportNr = 1;
		int orderReportNr = 1;
		
		//reading line by line and distinguish between the commands
		while (scan.hasNextLine()) {
			String line = getString(scan.nextLine());
			String lowerCaseLine = line.toLowerCase();
			if (lowerCaseLine.startsWith("insert client")) {
				client.insertClient(line.substring(line.indexOf(':') + 1));
			}
			else if (lowerCaseLine.startsWith("delete client")) {
				client.deleteClient(line.substring(line.indexOf(':') + 1));
			}
			else if (lowerCaseLine.startsWith("insert product")) {
				product.insertProduct(line.substring(line.indexOf(':') + 1));
			}
			else if (lowerCaseLine.startsWith("delete product")) {
				product.deleteProduct(line.substring(line.indexOf(':') + 1));
			}
			else if (lowerCaseLine.startsWith("order")) {
				order.addOrder(line.substring(line.indexOf(':') + 1));
			}
			else if (lowerCaseLine.startsWith("report client")) {
				client.generateReport("clientReport" + clientReportNr);
				clientReportNr++;
			}
			else if (lowerCaseLine.startsWith("report order")) {
				order.generateReport("orderReport" + orderReportNr);
				orderReportNr++;
			}
			else if (lowerCaseLine.startsWith("report product")) {
				product.generateReport("productReport" + productReportNr);
				productReportNr++;
			}
		}
	}
	
	/**
	 * Eliminates extra spaces form a string
	 * @param s The original string
	 * @return The string without extra spaces
	 */
	private String getString(String s) {
		s = s.replaceAll("\\s+", " ");
		if (s.startsWith(" ")) {
			s = s.substring(1);
		}
		if (s.endsWith(" ")) {
			s = s.substring(0,s.length() - 1);
		}
		return s;
	}
	
	/**
	 * Initializes the execution
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			//Controller c = new Controller("in.txt");
			Controller c = new Controller(args[0]);
		} catch (Exception e) {	
		}
	}

}
