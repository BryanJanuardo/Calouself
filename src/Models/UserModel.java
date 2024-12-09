package Models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Helper.IdGeneratorHelper;
import Utils.ConnectionDB;
import Utils.Response;

public class UserModel {

	private String User_id;
	private String Username;
	private String Password;
	private String Phone_number;
	private String Address;
	private String Role;
	
	public static Response<UserModel> Login(String Username, String Password) {
	    Response<UserModel> res = new Response<UserModel>();
	    
	    String query = "SELECT * FROM users WHERE Username = ?";
	    
	    try {
	        ConnectionDB con = ConnectionDB.getInstance();
	        PreparedStatement ps = con.prepareStatement(query);
	        ps.setString(1, Username);
	        
	        ResultSet rs = con.execQuery(ps);  
	        
	        if (!rs.next()) {
	            res.setMessages("Error: User Not Found!");
	            res.setIsSuccess(false);
	            res.setData(null);
	            return res;
	        }
	        
	        String user_id = rs.getString("User_id");
	        String username = rs.getString("Username");
	        String password = rs.getString("Password");
	        String phone_Number = rs.getString("Phone_number");
	        String address = rs.getString("Address");
	        String role = rs.getString("Role");
	        
	        UserModel foundUser = new UserModel(user_id, username, password, phone_Number, address, role);  

	        if (!foundUser.getPassword().equals(Password)) {
	            res.setMessages("Error: Wrong Password!");
	            res.setIsSuccess(false);
	            res.setData(null);
	            return res;
	        }
	        
	        res.setMessages("Success: User Authenticated!");
	        res.setIsSuccess(true);
	        res.setData(foundUser);
	        return res;
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    } catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}
	
	public static Response<UserModel> Register(String Username, String Password, String Phone_Number, String Address, String Role) {
		Response<UserModel> res = new Response<UserModel>();
	    
	    String query = "INSERT INTO users (User_id, Username, Password, Phone_number, Address, Role) VALUES (?, ?, ?, ?, ?, ?)";
	    try {
	    	UserModel user = CheckAccountValidation(Username, Password, Phone_Number, Address).getData();
	    	String newUserId = "";
	    	
	    	if(user == null) {
	    		newUserId = "US0000000001";
	    	}else {
	    		newUserId = IdGeneratorHelper.generateNewId(user.getUser_id(), "US");
	    	}
	    			
	        ConnectionDB con = ConnectionDB.getInstance();
	        PreparedStatement ps = con.prepareStatement(query);
	        ps.setString(1, newUserId);
	        ps.setString(2, Username);
	        ps.setString(3, Password);
	        ps.setString(4, Phone_Number);
	        ps.setString(5, Address);
	        ps.setString(6, Role);

	        int rowsInserted = con.execUpdate(ps);
	        if (rowsInserted > 0) {
	            res.setMessages("Success: User Registered!");
	            res.setIsSuccess(true);
	            res.setData(null);
	            return res;
	        }
	        
	        res.setMessages("Error: Register Failed!");
	        res.setIsSuccess(true);
	        res.setData(null);
	        return res;
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    } catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}
	
	public static Response<UserModel> CheckAccountValidation(String Username, String Password, String Phone_Number, String Address) {
		Response<UserModel> res = new Response<UserModel>();
	    
	    String query = "SELECT * FROM users WHERE Username = ?";
	    
	    try {
	        ConnectionDB con = ConnectionDB.getInstance();
	        PreparedStatement ps = con.prepareStatement(query);
	        ps.setString(1, Username);
	        
	        ResultSet rs = con.execQuery(ps);  
	        
	        if (!rs.next()) {
	            res.setMessages("Error: User Not Found!");
	            res.setIsSuccess(false);
	            res.setData(null);
	            return res;
	        }
	        
	        String user_id = rs.getString("User_id");
	        String username = rs.getString("Username");
	        String password = rs.getString("Password");
	        String phone_Number = rs.getString("Phone_number");
	        String address = rs.getString("Address");
	        String role = rs.getString("Role");
	        
	        UserModel foundUser = new UserModel(user_id, username, password, phone_Number, address, role);  
	        
	        res.setMessages("Success: User Found!");
	        res.setIsSuccess(true);
	        res.setData(foundUser);
	        return res;
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    } catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}
	
	public UserModel() {
		// TODO Auto-generated constructor stub
	}

	public UserModel(String user_id, String username, String password, String phone_Number, String address,
			String role) {
		super();
		User_id = user_id;
		Username = username;
		Password = password;
		Phone_number = phone_Number;
		Address = address;
		Role = role;
	}

	public String getUser_id() {
		return User_id;
	}

	public void setUser_id(String user_id) {
		User_id = user_id;
	}

	public String getUserName() {
		return Username;
	}

	public void setUserName(String userName) {
		Username = userName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getPhone_Number() {
		return Phone_number;
	}

	public void setPhone_Number(String phone_Number) {
		Phone_number = phone_Number;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getRole() {
		return Role;
	}

	public void setRole(String role) {
		Role = role;
	}

}
