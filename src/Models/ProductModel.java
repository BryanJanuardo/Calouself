package Models;

import java.util.ArrayList;

import Factory.ItemFactory;
import Factory.UserFactory;

public class ProductModel extends Model{
	private final String Tablename = "products";
	private final String Primarykey = "Product_id";
	
	private String Product_id;
	private String Item_id;
	private String Seller_id;
	
	public ProductModel() {
		
	}

	public ProductModel(String product_id, String item_id, String seller_id) {
		super();
		Product_id = product_id;
		Item_id = item_id;
		Seller_id = seller_id;
	}

	public String getProduct_id() {
		return Product_id;
	}

	public void setProduct_id(String product_id) {
		Product_id = product_id;
	}

	public String getItem_id() {
		return Item_id;
	}

	public void setItem_id(String item_id) {
		Item_id = item_id;
	}

	public String getSeller_id() {
		return Seller_id;
	}

	public void setSeller_id(String seller_id) {
		Seller_id = seller_id;
	}

	public String getTablename() {
		return Tablename;
	}

	public String getPrimarykey() {
		return Primarykey;
	}

	public UserModel user() {
		return this.hasOne(UserModel.class, "users", this.Seller_id, "User_id");
	}
	
	public ItemModel item() {
		return this.hasOne(ItemModel.class, "items", this.Item_id, "Item_id");
	}

	public ArrayList<WishlistModel> wishlist() {
		return this.hasMany(WishlistModel.class, "wishlists", this.Item_id, "Item_id");
	}
	
	public ArrayList<ProductModel> all(){
		return super.all(ProductModel.class);
	}
	
	public ArrayList<ProductModel> where(String columnName, String operator, String key){
		return super.where(ProductModel.class, columnName, operator, key);
	}
	
	public ProductModel update(String fromKey) {
		return super.update(ProductModel.class, fromKey);
	}
	
	public ProductModel insert() {
		return super.insert(ProductModel.class);
	}
	
	public ProductModel find(String id) {
		return super.find(ProductModel.class, id);
	}
	
	public ProductModel latest() {
		return super.latest(ProductModel.class);
	}
}
