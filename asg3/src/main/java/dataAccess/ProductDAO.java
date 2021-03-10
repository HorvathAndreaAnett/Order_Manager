package dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Product;

/**
 *Contains the specific methods for the type Product
 */
public class ProductDAO extends AbstractDAO<Product> {
	
	/**
	 * Gets the id of a product when the name is known
	 * @param name The name of the product
	 * @return An int representing the id of the product with the given name
	 */
	public int getIdByName(String name){
		Connection conn = ConnectionFactory.getConnection();
		String query = createSelectQuery("productName");
		PreparedStatement st;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(query);
			st.setString(1, name);
			rs = st.executeQuery();
			
			rs.next();
			return rs.getInt("productId");
		} 
		catch (Exception e) {	
		}
		return 0;
	}

}
