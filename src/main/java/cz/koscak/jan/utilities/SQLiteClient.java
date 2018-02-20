package cz.koscak.jan.utilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLiteClient {
	
	private SQLiteClient() {};

	private static String pathToDB;
	
	public static void initialize(String pathToDB) {
		SQLiteClient.pathToDB = pathToDB;
	}
	
	public static String getPathToDB() {
		if (pathToDB == null || pathToDB.isEmpty()) {
			throw new IllegalArgumentException("ERROR: Invalid path to DB ('" + pathToDB + "'). Please provide valid path to DB...");
			// Solution guide: use initialize() method + configuration.properties file
		}
		return pathToDB;
	}
	
	/**
	 * Connect to database
	 * 
	 * @return the Connection connection
	 */
	private static Connection connect() {
		
		// Check if drive is in build path
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			System.err.println("ERROR: SQLite JDBC drive not found! Please add it into build path.");
			e1.printStackTrace();
		}
		// SQLite connection string
		String url = "jdbc:sqlite:" + getPathToDB();
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.err.println("ERROR: " + e.getMessage());
		}
		return conn;
	}
	
	/**
	 * select all rows in the warehouses table
	 */
	/*public void selectAll() {
		String sql = "SELECT id, payload FROM message";
		
		try {
			Connection conn	= connect();
			Statement stmt	= conn.createStatement();
			ResultSet rs	= stmt.executeQuery(sql);
			
			// loop through the result set
			while (rs.next()) {
				System.out.println(rs.getInt("id") +  "\t" + 
								   //rs.getString("name") + "\t" +
								   rs.getString("payload"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}*/
	
	/**
	 * insert row in the payload table
	 */
	/*public void insert(String payload) {
		String sql = "INSERT OR REPLACE INTO message (payload) VALUES ('" + payload + "')";   	
		try {
			Connection conn = connect();   
			Statement stmt  = conn.createStatement();
			stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}*/
	
	public static void createTable(String sql) {
		
		try {
			
			Connection connection = connect();   
			Statement statement  = connection.createStatement();
			statement.executeQuery(sql);
			statement.close();
			connection.close();	
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());		
		}
		
	}

	/* OLD style
	public static void select(String sql) {
		
		try {
			
			Connection connection	= connect();
			Statement statement	= connection.createStatement();
			ResultSet rs	= statement.executeQuery(sql);
			
			// loop through the result set
			while (rs.next()) {
				System.out.println(rs.getInt("id") +  "\t" + 
								   rs.getString("date") + "\t" +
								   rs.getString("temperature") + "\t" +
								   rs.getString("humidity"));
			}
			
			statement.close();
			connection.close();	
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
	}
	*/
	
	public static List<String[]> selectAndReturnAsList(String sql) {
		
		List<String[]> result = new ArrayList<String[]>();
		
		try {
			
			Connection connection = connect();
			Statement statement	= connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			int columnCount = rs.getMetaData().getColumnCount();
			
			while(rs.next()) {
			    String[] row = new String[columnCount];
			    for (int i=0; i <columnCount ; i++)
			    {
			       row[i] = rs.getString(i + 1);
			    }
			    result.add(row);
			}
			
			statement.close();
			connection.close();		
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			result = null;
		}
		
		return result;
		
	}
	
	public static String[] selectFirstRow(String sql) {
		return selectAndReturnAsList(sql).get(0);
	}
	
	public static String selectString(String sql) {
		return selectFirstRow(sql)[0];
	}
		
	public static Integer selectInteger(String sql) {
		Integer result = null;
		try {
			result = Integer.valueOf(selectString(sql));
		} catch (NumberFormatException exception) {
		}
		return result;
	}
	
	// EXAMPLE1: String sql = "INSERT OR REPLACE INTO message_table (headers, payload) VALUES ('" + headers + "', '" + payload + "')";
	/* EXAMPLE2: insert or replace into Book (ID, Name, TypeID, Level, Seen) values
	((select ID from Book where Name = "SearchName"), "SearchName", ...);*/
	public static void insert(String sql) {
		try {
			Connection connection = connect();   
			Statement statement  = connection.createStatement();
			statement.executeUpdate(sql);
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}	

}
