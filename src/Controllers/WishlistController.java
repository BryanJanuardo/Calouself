package Controllers;

import java.util.ArrayList;

import Models.WishlistModel;
import Utils.Response;

public class WishlistController {

	public Response<ArrayList<WishlistModel>> ViewWishlist(String User_id){
		return WishlistModel.ViewWishlist(User_id);
	}
	
	public Response<WishlistModel> AddWishlist(String Product_id, String User_id) {
		return WishlistModel.AddWishlist(Product_id, User_id);
	}
	
	public Response<WishlistModel> RemoveWishlist(String Wishlist_id) {
		return WishlistModel.RemoveWishlist(Wishlist_id);
	}
	
	public WishlistController() {
		// TODO Auto-generated constructor stub
	}

}
