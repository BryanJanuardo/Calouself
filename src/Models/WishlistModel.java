package Models;

import java.util.ArrayList;

import Factories.WishlistFactory;
import Helpers.IdGeneratorHelper;
import Utils.Response;

public class WishlistModel extends Model{
	private final String Tablename = "wishlists";
	private final String Primarykey = "Wishlist_id";
	
	private String Wishlist_id;
	private String Product_id;
	private String User_id;

	public static Response<ArrayList<WishlistModel>> ViewWishlist(String User_id){
		Response<ArrayList<WishlistModel>> res = new Response<ArrayList<WishlistModel>>();

		try {
	    	ArrayList<WishlistModel> listWishlist = WishlistFactory.createWishlist().where("User_id", "=", User_id);
	        
	        res.setMessages("Success: All Wishlist Retrieved!");
	        res.setIsSuccess(true);
	        res.setData(listWishlist);
	        return res;
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}
	
	public static Response<WishlistModel> AddWishlist(String Product_id, String User_id) {
		Response<WishlistModel> res = new Response<WishlistModel>();

		try {
			WishlistModel wishlist = WishlistFactory.createWishlist(IdGeneratorHelper.generateNewId(WishlistFactory.createWishlist().latest().getWishlist_id(), "WS"), 
					Product_id, User_id);
			
			wishlist.insert();
			
	        res.setMessages("Success: Wishlist Added!");
	        res.setIsSuccess(true);
	        res.setData(wishlist);
	        return res;
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}
	
	public static Response<WishlistModel>  RemoveWishlist(String Wishlist_id) {
		Response<WishlistModel> res = new Response<WishlistModel>();

		try {
			Boolean wishlist = WishlistFactory.createWishlist().delete(Wishlist_id);
			
			if(!wishlist) {
				res.setMessages("Error: Deleteting Wishlist Failed!");
				res.setIsSuccess(wishlist);
				res.setData(null);
				return res;				
			}
			
	        res.setMessages("Success: Wishlist Removed!");
	        res.setIsSuccess(wishlist);
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
	
	public WishlistModel() {
		// TODO Auto-generated constructor stub
	}

	public WishlistModel(String wishlist_id, String product_id, String user_id) {
		super();
		Wishlist_id = wishlist_id;
		Product_id = product_id;
		User_id = user_id;
	}

	public String getWishlist_id() {
		return Wishlist_id;
	}

	public void setWishlist_id(String wishlist_id) {
		Wishlist_id = wishlist_id;
	}

	public String getProduct_id() {
		return Product_id;
	}

	public void setProduct_id(String product_id) {
		Product_id = product_id;
	}

	public String getUser_id() {
		return User_id;
	}

	public void setUser_id(String user_id) {
		User_id = user_id;
	}

	public String getTablename() {
		return Tablename;
	}
	
	public String getPrimarykey() {
		return Primarykey;
	}

	public UserModel user() {
		return this.hasOne(UserModel.class, "users", this.User_id, "User_id");
	}
	
	public ProductModel product() {
		return this.hasOne(ProductModel.class, "products", this.Product_id, "Product_id");
	}
	
	public ArrayList<WishlistModel> all(){
		return super.all(WishlistModel.class);
	}
	
	public ArrayList<WishlistModel> where(String columnName, String operator, String key){
		return super.where(WishlistModel.class, columnName, operator, key);
	}
	
	public WishlistModel update(String fromKey) {
		return super.update(WishlistModel.class, fromKey);
	}
	
	public WishlistModel insert() {
		return super.insert(WishlistModel.class);
	}
	
	public WishlistModel find(String fromKey) {
		return super.find(WishlistModel.class, fromKey);
	}
	
	public WishlistModel latest() {
		return super.latest(WishlistModel.class);
	}

	public Boolean delete(String fromKey) {
		return super.delete(WishlistModel.class, fromKey);
	}
	
	public ArrayList<WishlistModel> whereIn(String columnName, ArrayList<String> listValues){
		return super.whereIn(WishlistModel.class, columnName, listValues);
	}
}
