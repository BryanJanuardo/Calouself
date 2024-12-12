package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ConnectionDB {
	private static ConnectionDB connect;

	private final String USERNAME = "root";
	private final String PASSWORD = "";
	private final String DATABASE = "calouself";
	private final String HOST = "localhost:3306";
	private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
	
	private Connection con;
	
	public ResultSet rs;
	public ResultSetMetaData rsm;
	
	public static synchronized ConnectionDB getInstance() {
		if(connect == null) {
			connect = new ConnectionDB();
		}
		return connect;
	}
	
	public ResultSet execQuery(PreparedStatement ps) {
		try {
			rs = ps.executeQuery();
			rsm = rs.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public Integer execUpdate(PreparedStatement ps) {
		try {
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	public PreparedStatement prepareStatement(String query) {
		try {
			return con.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private ConnectionDB() {
		try {
//			Class.forName("com.mysql.jdbc.Driver"); // versi 1 (Bluejack)
			Class.forName("com.mysql.cj.jdbc.Driver"); // versi 2 (PC Binus)
			con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
