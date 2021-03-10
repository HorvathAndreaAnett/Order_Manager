package dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Client;

/**
 * Contains the specific methods for the type Client
 */
public class ClientDAO extends AbstractDAO<Client> {

	/**
	 * Gets the id of a client when the name is known
	 * @param name The name of the client
	 * @return An int representing the id of the client with the given name
	 */
	public int getIdByName(String name){
		Connection conn = ConnectionFactory.getConnection();
		String query = createSelectQuery("clientName");
		PreparedStatement st;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(query);
			st.setString(1, name);
		
			rs = st.executeQuery();
			
			rs.next();
			return rs.getInt("clientId");
		} 
		catch (Exception e) {	
		}
		return 0;
	}
	
}
