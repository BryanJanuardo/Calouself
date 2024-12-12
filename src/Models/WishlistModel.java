package Models;

import java.util.ArrayList;

import Utils.Response;

public class WishlistModel extends Model{
	private String Tablename = "wishlists";
	private String Wishlist_id;
	private String Item_id;
	private String User_id;

	public static Response<ArrayList<WishlistModel>> ViewWishlist(String Wishlist_id, String User_id){
		
		return null;
	}
	
	public static Response<WishlistModel> AddWishlist(String Item_id, String User_id) {

		return null;
	}
	
	public static Response<WishlistModel>  RemoveWishlist(String Wishlist_id) {

		return null;
	}
	
	public WishlistModel() {
		// TODO Auto-generated constructor stub
	}

	public WishlistModel(String wishlist_id, String item_id, String user_id) {
		super();
		Wishlist_id = wishlist_id;
		Item_id = item_id;
		User_id = user_id;
	}

	public String getWishlist_id() {
		return Wishlist_id;
	}

	public void setWishlist_id(String wishlist_id) {
		Wishlist_id = wishlist_id;
	}

	public String getItem_id() {
		return Item_id;
	}

	public void setItem_id(String item_id) {
		Item_id = item_id;
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
	
	public UserModel user() {
		return this.hasOne(UserModel.class, "users", this.User_id, "User_id");
	}
	
	public ItemModel item() {
		return this.hasOne(ItemModel.class, "items", this.Item_id, "Item_id");
	}
	
	public ArrayList<WishlistModel> all(){
		return super.all(WishlistModel.class);
	}
	
	public ArrayList<WishlistModel> where(String columnName, String operator, String key){
		return super.where(WishlistModel.class, columnName, operator, key);
	}

}
