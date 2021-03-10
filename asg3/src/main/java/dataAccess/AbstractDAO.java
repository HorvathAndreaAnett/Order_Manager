package dataAccess;


import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Generic class that defines the basic operations on databases:
 * -insert
 * -delete
 * -update
 * -select
 * -list all
 * -creating objects from table's instances
 *
 * @param <T> Java Model Class mapped to a table in the database 
 */
public class AbstractDAO<T> {

	protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

	private final Class<T> type;

	/**
	 * Obtains the class of the type T
	 */
	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	/**
	 * Creates a SELECT query
	 * @param field The condition field
	 * @return A string containing the query
	 */
	public String createSelectQuery(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM " + type.getSimpleName() + " WHERE " + field + " = ?");
		return sb.toString();
	}
	
	/**
	 * Gets the entire content of a table
	 * @return A list of objects of type T corresponding to the instances of the table
	 */
	public List<T> findAll() {
		Connection conn = ConnectionFactory.getConnection();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM " + type.getSimpleName());
		//statement without a condition which returns the entire table
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(sb.toString());
			rs = st.executeQuery();
		}
		catch (Exception e) {
		}
		// convert the result set to a list of objects
		return createObjects(rs);
	}
	
	/**
	 * Performs an INSERT query
	 * @param t The object to be inserted
	 */
	public void insert(T t) {
		Connection conn = ConnectionFactory.getConnection();
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO " + type.getSimpleName() + " (");
		
		Field[] fields = type.getDeclaredFields();
		int noFields = fields.length;
		
		//insert the names of the fields of the right type
		sb.append(fields[0].getName());
		for (int i = 1; i < noFields; i++) {
			sb.append(", " + fields[i].getName());
		}
		
		//insert as many '?' as the number of fields
		sb.append(") VALUES ( ?");
		for (int i = 1; i < noFields; i++) {
			sb.append(", ?");
		}
		sb.append(")");
		
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(sb.toString());
			for (int i = 1; i <= noFields; i++) {
				fields[i - 1].setAccessible(true); //needed to access the fields
				st.setObject(i, fields[i - 1].get(t)); //place the values of t in the places of the '?'
			}
			st.execute();
		}
		catch (Exception e) {
		}
		finally {
			ConnectionFactory.close(st);
			ConnectionFactory.close(conn);
		}
	}
	
	/**
	 * Performs a DELETE query
	 * @param t The object to be deleted
	 */
	public void delete(T t) {
		Connection conn = ConnectionFactory.getConnection();
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM " + type.getSimpleName() + " WHERE ");
		
		Field[] fields = type.getDeclaredFields();
		
		//the condition is chosen to be the first column which is the id
		sb.append(fields[0].getName());
		sb.append(" = ?");
		
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(sb.toString());
			fields[0].setAccessible(true);
			st.setObject(1, fields[0].get(t));
			st.execute();
		}
		catch (Exception e) {
		}	
		finally {
			ConnectionFactory.close(st);
			ConnectionFactory.close(conn);
		}
	}
	
	/**
	 * Performs an UPDATE query
	 * @param fieldToSet The field to be modified
	 * @param oldT The object to be updated
	 * @param newT The new values of the object
	 */
	public void update(int fieldToSet, T oldT, T newT) {
		Connection conn = ConnectionFactory.getConnection();
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE " + type.getSimpleName() + " SET ");
		
		Field[] fields = type.getDeclaredFields();
		
		//choose the name of the field with the number fieldToSet
		sb.append(fields[fieldToSet].getName() + " = ? WHERE ");
		//choose the id as condition
		sb.append(fields[0].getName());
		sb.append(" = ?");
		
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(sb.toString());
			fields[0].setAccessible(true);
			fields[fieldToSet].setAccessible(true);
			st.setObject(1, fields[fieldToSet].get(newT));
			st.setObject(2, fields[0].get(oldT) );
			st.execute();
		}
		catch (Exception e) {
		}
		finally {
			ConnectionFactory.close(st);
			ConnectionFactory.close(conn);
		}
	}
	
	/**
	 * Constructs a SELECT MAX query
	 * @param field The field of which the maximum value is wanted
	 * @return The string with the query
	 */
	private String createMaxQuery(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT MAX(" + field + ") FROM " + type.getSimpleName());
		return sb.toString();
	}
	
	/**
	 * Gets the next available id
	 * @return An int with the next available id
	 */
	public int getAvailableId(){
		Connection conn = ConnectionFactory.getConnection();
		Field[] fields = type.getDeclaredFields();
		//search for the biggest value on the id column (the first one)
		String query = createMaxQuery(fields[0].getName());
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(query);
			rs = st.executeQuery();
			rs.first();
		
			//returns the next available value for id, i.e. the maximum id + 1
			return rs.getInt(1) + 1;
		} catch (Exception e) {
		}
		finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(st);
			ConnectionFactory.close(conn);
		}
		return 0;
	}
	
	/**
	 * Gets the objects with a specific id
	 * @param id The id for which we want the object
	 * @return A list of objects with the given id
	 */
	public List<T> findById(int id) {
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement st = null;
		ResultSet resultSet = null;
		
		Field[] fields = type.getDeclaredFields();
		// execute a SELECT query for the given
		String query = createSelectQuery(fields[0].getName());
		try {
			
			st = conn.prepareStatement(query);
			st.setInt(1, id);
			resultSet = st.executeQuery();
			return createObjects(resultSet);
			
		} 
		catch (SQLException e) {	
		} 
		finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(st);
			ConnectionFactory.close(conn);
		}
		return null;
	}
	
	/**
	 * Creates a list of objects out of a result set
	 * @param resultSet The result set to be converted
	 * @return The list of objects corresponding to the result set
	 */
	private List<T> createObjects(ResultSet resultSet) {
		List<T> list = new ArrayList<T>();

		//converts a result set into a list of objects
		try {
			while (resultSet.next()) {
				T instance = type.newInstance();
				for (Field field : type.getDeclaredFields()) {
					Object value = resultSet.getObject(field.getName());
					//each object of the result set is mapped into an object of type T
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
					Method method = propertyDescriptor.getWriteMethod();
					method.invoke(instance, value);
				}
				list.add(instance);
			}
		} 
		catch (Exception e) {	
		} 
		
		return list;
	}

}
