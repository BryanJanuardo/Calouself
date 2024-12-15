package Models;

import java.util.ArrayList;

import Factories.UserFactory;
import Helpers.IdGeneratorHelper;
import Utils.Response;

public class UserModel extends Model{
	private final String Tablename = "users";
	private final String Primarykey = "User_id";
	
	private String User_id;
	private String Username;
	private String Password;
	private String Phone_number;
	private String Address;
	private String Role;
	
	public static Response<UserModel> Login(String Username, String Password) {
	    Response<UserModel> res = new Response<UserModel>();
	    
	    try {
	    	ArrayList<UserModel> users = UserFactory.createUser().where("Username", "=", Username);
	        if(users.isEmpty()) {
	        	res.setMessages("Error: User Not Found!");
	            res.setIsSuccess(false);
	            res.setData(null);
	            return res;
	        }
	    	
	    	UserModel foundUser = users.get(0);
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
	        
	    }  catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}
	
	public static Response<UserModel> Register(String Username, String Password, String Phone_Number, String Address, String Role) {
		Response<UserModel> res = new Response<UserModel>();
	    
	    try {
	    	UserModel user = UserFactory.createUser(IdGeneratorHelper.generateNewId(UserFactory.createUser().latest().getUser_id(), "US"),
	    			Username, Password, Phone_Number, Address, Role);
	    	user.insert();
	        
	        res.setMessages("Success: User Registered!");
	        res.setIsSuccess(true);
	        res.setData(user);
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
	    try {
	    	ArrayList<UserModel> users = UserFactory.createUser().where("Username", "=", Username);
	    	if(users.isEmpty()) {
	    		res.setMessages("Error: User Not Found!");
	    		res.setIsSuccess(false);
	    		res.setData(null);
	    		return res;
	    	}
	    	
	    	res.setMessages("Success: User Found!");
	    	res.setIsSuccess(true);
	    	res.setData(users.get(0));
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

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPhone_number() {
		return Phone_number;
	}

	public void setPhone_number(String phone_number) {
		Phone_number = phone_number;
	}
	
	public String getTablename() {
		return Tablename;
	}
	
	public String getPrimarykey() {
		return Primarykey;
	}

	public ArrayList<ProductModel> products(){
		return this.hasMany(ProductModel.class, "products", this.User_id, "Seller_id");
	}
	
	public ArrayList<WishlistModel> wishlists(){
		return this.hasMany(WishlistModel.class, "wishlists", this.User_id, "User_id");
	}
	
	public ArrayList<TransactionModel> transactions(){
		return this.hasMany(TransactionModel.class, "transactions", this.User_id, "User_id");
	}
	
	public ArrayList<OfferModel> offers(){
		return this.hasMany(OfferModel.class, "offers", this.User_id, "Buyer_id");
	}
	
	public ArrayList<UserModel> all(){
		return super.all(UserModel.class);
	}
	
	public ArrayList<UserModel> where(String columnName, String operator, String key){
		return super.where(UserModel.class, columnName, operator, key);
	}
	
	public UserModel update(String fromKey) {
		return super.update(UserModel.class, fromKey);
	}
	
	public UserModel insert() {
		return super.insert(UserModel.class);
	}
	
	public UserModel find(String fromKey) {
		return super.find(UserModel.class, fromKey);
	}
	
	public UserModel latest() {
		return super.latest(UserModel.class);
	}
	
	public Boolean delete(String fromKey) {
		return super.delete(UserModel.class, fromKey);
	}
	
	public ArrayList<UserModel> whereIn(String columnName, ArrayList<String> listValues){
		return super.whereIn(UserModel.class, columnName, listValues);
	}
}
