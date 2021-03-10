package businessLogic;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 *Contains the necessary methods for generating PDFs
 */
public class PDF {

	/**
	 * Represents a font used for writing messages
	 */
	private static Font font = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL);
	

	/**
	 * Creates a PDF which contains a message
	 * @param name The name of the PDF
	 * @param content The message to be written
	 */
	public void generateMessage(String name, String content) {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(name + ".pdf"));
			document.open();
			Chunk chunk = new Chunk(content, font);
			
			//add in the document the content with the given font
			document.add(chunk);
			document.close();
		} catch (Exception e) {
		}
	}
	
	/**
	 * Creates a PDF representing a bill
	 * Contains a table
	 * @param name The name of the PDF
	 * @param orderId The order's id
	 * @param clientName The name of the client who made the order
	 * @param productName The name of the product which was ordered
	 * @param quantity The quantity in which the product was bougt
	 * @param total The total cost of the order
	 */
	public void generateBill(String name, int orderId, String clientName, String productName, int quantity, float total) {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(name + ".pdf"));
			document.open();
			
			//put on the headers of the table the attributes of an order
			PdfPTable table = new PdfPTable(5); //generate a 5 column table
			List<String> headers = new ArrayList<String>();
			headers.add("Order Id");
			headers.add("Client name");
			headers.add("Ordered product");
			headers.add("Quantity");
			headers.add("Total");
			
			//for each header set the alignment, the background color
			for (String s: headers) {
				PdfPCell c = new PdfPCell(new Phrase(s));
		        c.setHorizontalAlignment(Element.ALIGN_CENTER);
		        c.setBackgroundColor(new BaseColor(243, 181, 239));
		        table.addCell(c);
			}
			
			//add the content
			table.addCell(orderId + "");
			table.addCell(clientName);
			table.addCell(productName);
			table.addCell(quantity + "");
			table.addCell(total + "");
		 
			document.add(table);
			document.close();
			
		} catch (Exception e) {
		}
	}
	
	/**
	 * Creates a PDF representing a report
	 * Contains a table
	 * @param nrOfColumns The number of columns for the table; The number of fields
	 * @param name The name of the PDF
	 * @param header The headers of the columns of the table
	 * @param content The contents of the table
	 */
	public void generateReport(int nrOfColumns, String name, List<String> header, List<String> content) {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(name + ".pdf"));
			document.open();
		 
			PdfPTable table = new PdfPTable(nrOfColumns); //the number of columns is given as parameter
		
			//add the headers of the table from the header parameter
			for (String s: header) {
				PdfPCell c = new PdfPCell(new Phrase(s));
		        c.setHorizontalAlignment(Element.ALIGN_CENTER);
		        c.setBackgroundColor(new BaseColor(240, 219, 52));
		        table.addCell(c);
			}
			//add the table's content from the content oarameter
			for (String s: content) {
				table.addCell(s);
			}
		 
			document.add(table);
			document.close();
		} catch (Exception e) {
		}
	}
	
	
	
	
}
