package Main;

import java.lang.reflect.Field;
import java.util.ArrayList;

import Models.ItemModel;
import Models.SellerItemModel;
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
//    	UserModel user = UserModel.CheckAccountValidation("jane_seller", "12345", "+623213123", "Jalan kemanggisan").getData();
//    	ArrayList<SellerItemModel> list;
//    	System.out.println(user.getUser_id());
//    	System.out.println(user.getUsername());
//    	System.out.println(user.getPassword());
//    	System.out.println(user.getAddress());
//    	list = user.sellerItems();
//    	System.out.println("Tes Item");
//    	for (SellerItemModel itemModel : list) {
//    		System.out.println(itemModel.getItem_id());
//		}	
//    	
//    	UserModel tes = list.get(0).user();
//    	System.out.println(tes.getUser_id());
//    	System.out.println(tes.getUsername());
//    	System.out.println(tes.getPassword());
//    	System.out.println(tes.getAddress());

    	
//    	ArrayList<SellerItemModel> list2;
//    	SellerItemModel items = new SellerItemModel();
//    	list2 = items.where("Item_id", "=", "IT0000000004");
//    	
//    	for (SellerItemModel itemModel : list2) {
//    		System.out.println(itemModel.getId());
//		}
    	
//    	ArrayList<WishlistModel> list3;
//    	WishlistModel wish = new WishlistModel();
//    	list3 = wish.where("Item_id", "=", "IT0000000004");
//    	
//    	for (WishlistModel wishlist : list3) {
//			System.out.println(wishlist.getUser_id());
//		}
    }

}