package Models;

import java.util.ArrayList;

import Utils.Response;

public class WishlistModel {
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

}
