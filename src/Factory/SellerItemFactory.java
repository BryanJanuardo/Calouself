package Factory;

import Models.ItemModel;
import Models.ProductModel;
import Models.UserModel;

public class SellerItemFactory implements Factory{

	public SellerItemFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static ProductModel createSellerItem(String Product_id, String item_id, String seller_id) {
		return new ProductModel(Product_id, item_id, seller_id);
	}
	
}
