package Models;

import java.util.ArrayList;

import Factory.ItemFactory;
import Factory.UserFactory;

public class SellerItemModel extends Model{
	private String Tablename = "seller_items";
	private Integer Id;
	private String Item_id;
	private String Seller_id;
	
	public SellerItemModel() {
		
	}

	public SellerItemModel(Integer id, String item_id, String seller_id) {
		super();
		Id = id;
		Item_id = item_id;
		Seller_id = seller_id;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
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

	public UserModel user() {
		return this.hasOne(UserModel.class, "users", this.Seller_id, "User_id");
	}
	
	public ItemModel item() {
		return this.hasOne(ItemModel.class, "items", this.Item_id, "Item_id");
	}

	public ArrayList<WishlistModel> wishlist() {
		return this.hasMany(WishlistModel.class, "wishlists", this.Item_id, "Item_id");
	}
	
	public ArrayList<SellerItemModel> all(){
		return super.all(SellerItemModel.class);
	}
	
	public ArrayList<SellerItemModel> where(String columnName, String operator, String key){
		return super.where(SellerItemModel.class, columnName, operator, key);
	}
}
