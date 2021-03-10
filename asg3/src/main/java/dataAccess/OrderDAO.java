package dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Ordert;

/**
 * Contains the specific methods for the type Ordert
 */
public class OrderDAO extends AbstractDAO<Ordert> {

	/**
	 * Gets the id of an order when we know the id of the client
	 * @param clientId The id of the client
	 * @return An int representing the id of the order from the given client
	 */
	public int getIdByClientId(int clientId){
		Connection conn = ConnectionFactory.getConnection();
		String query = createSelectQuery("clientId");
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(query);
			st.setInt(1, clientId);
			rs = st.executeQuery();
			rs.next();
			return rs.getInt("orderId");
		} 
		catch (Exception e) {	
		}
		return 0;
	}
	
	
}
