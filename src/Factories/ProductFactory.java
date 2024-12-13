package Factories;

import Models.ItemModel;
import Models.ProductModel;
import Models.UserModel;

public class ProductFactory implements Factory{

	public ProductFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static ProductModel createProduct() {
		return new ProductModel();
	}
	
	public static ProductModel createProduct(String product_id, String item_id, String seller_id) {
		return new ProductModel(product_id, item_id, seller_id);
	}
	
}
