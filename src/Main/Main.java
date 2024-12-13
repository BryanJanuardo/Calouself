package Main;

import java.lang.reflect.Field;
import java.util.ArrayList;

import Helpers.IdGeneratorHelper;
import Managers.ViewManager;
import Models.ItemModel;
import Models.OfferModel;
import Models.ProductModel;
import Models.UserModel;
import Models.WishlistModel;
import javafx.application.Application;
import javafx.stage.Stage;

public final class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
    	
    	ViewManager viewManager = new ViewManager(stage);
    	System.out.println("Hello World2");
//    	UserModel user = UserModel.CheckAccountValidation("jane_seller", "12345", "+623213123", "Jalan kemanggisan").getData();
//    	ArrayList<ProductModel> list;
//    	System.out.println(user.getUser_id());
//    	System.out.println(user.getUsername());
//    	System.out.println(user.getPassword());
//    	System.out.println(user.getAddress());
//    	list = user.sellerItems();
//    	System.out.println("Tes Item");
//    	for (ProductModel itemModel : list) {
//    		System.out.println(itemModel.getProduct_id());
//		}	
//    	
//    	UserModel tes = list.get(0).user();
//    	System.out.println(tes.getUser_id());
//    	System.out.println(tes.getUsername());
//    	System.out.println(tes.getPassword());
//    	System.out.println(tes.getAddress());

    	
//    	ArrayList<ProductModel> list2;
//    	ProductModel items = new ProductModel();
//    	list2 = items.where("Item_id", "=", "IT0000000004");
//    	
//    	for (ProductModel itemModel : list2) {
//    		System.out.println(itemModel.getId());
//		}
    	
//    	ArrayList<WishlistModel> list3;
//    	WishlistModel wish = new WishlistModel();
//    	list3 = wish.where("Item_id", "=", "IT0000000004");
//    	
//    	for (WishlistModel wishlist : list3) {
//			System.out.println(wishlist.getUser_id());
//		}
    	
//    	UserModel user = new UserModel();
//    	user = user.latest();
    	
//    	wishlist = wishlist.latest();
//    	System.out.println(wishlist.getUser_id());
//    	user.setUsername("Bryan");
//    	user.set
    	
//    	WishlistModel wishlist = new WishlistModel();
//    	wishlist = wishlist.latest();
//    	System.out.println(wishlist.product().getSeller_id());	
    	
    	//INSERT
//    	WishlistModel wishlist = new WishlistModel();
//    	wishlist.setWishlist_id(IdGeneratorHelper.generateNewId(wishlist.latest().getWishlist_id(), "WS"));
//    	wishlist.setUser_id("US0000000002");
//    	wishlist.setItem_id("IT0000000004");
//    	wishlist.insert();
//    	System.out.println("Berhaisl!");
    	
    	//UPDATE
//    	WishlistModel wishlist = new WishlistModel();
//    	wishlist = wishlist.find("WS0000000006");
//    	wishlist.setUser_id("US0000000002");
//    	wishlist.update(wishlist.getWishlist_id());
    	
//    	OfferModel offer = new OfferModel();
//    	offer = offer.find("OF0000000003");
//    	ArrayList<UserModel> users = offer.users();
//
//    	for (UserModel user : users) {
//			System.out.println(user.getUsername());
//		}
    }

}