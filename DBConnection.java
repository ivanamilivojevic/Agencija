package agencija;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private final static String dburl = "jdbc:sqlite:skijaliste.db";
	private static Connection conn;
	

	private DBConnection() { }
	
	public static Connection getConnection() throws SQLException {
		if(conn==null || conn.isClosed())
			conn = DriverManager.getConnection(dburl);
		return conn;
	}
	
	public static void close() throws SQLException {
		if(conn!=null && !conn.isClosed())
			conn.close();
	}
	

	public static void closeQuietly() {
		try {
			close();
		} catch (SQLException e) {
			System.err.println("Nemoguce zatvoriti konekciju");
			e.printStackTrace();
		}
	}
}
