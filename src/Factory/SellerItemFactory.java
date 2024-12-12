package Factory;

import Models.ItemModel;
import Models.SellerItemModel;
import Models.UserModel;

public class SellerItemFactory implements Factory{

	public SellerItemFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static SellerItemModel createSellerItem(Integer id, String item_id, String seller_id) {
		return new SellerItemModel(id, item_id, seller_id);
	}
	
}
