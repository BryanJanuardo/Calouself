package Factories;

import Models.WishlistModel;

public class WishlistFactory {

	public WishlistFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static WishlistModel createWishlist() {
		return new WishlistModel();
	}
	
	public static WishlistModel createWishlist(String wishlist_id, String product_id, String user_id) {
		return new WishlistModel(wishlist_id, product_id, user_id);
	}

}
