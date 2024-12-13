package Models;

import java.math.BigDecimal;
import java.util.ArrayList;

public class OfferModel extends Model{
	private final String Tablename = "offers";
	private final String Primarykey = "Offer_id";
	
	private String Offer_id;
	private String Product_id;
	private String Buyer_id;
	private BigDecimal Item_offer_price;
	private String Item_offer_status;
	private String Reason;
	
	public OfferModel() {
		// TODO Auto-generated constructor stub
	}

	public OfferModel(String offer_id, String product_id, String buyer_id, BigDecimal item_offer_price,
			String item_offer_status, String reason) {
		super();
		Offer_id = offer_id;
		Product_id = product_id;
		Buyer_id = buyer_id;
		Item_offer_price = item_offer_price;
		Item_offer_status = item_offer_status;
		Reason = reason;
	}

	public String getOffer_id() {
		return Offer_id;
	}

	public void setOffer_id(String offer_id) {
		Offer_id = offer_id;
	}

	public String getProduct_id() {
		return Product_id;
	}

	public void setProduct_id(String product_id) {
		Product_id = product_id;
	}

	public String getBuyer_id() {
		return Buyer_id;
	}

	public void setBuyer_id(String buyer_id) {
		Buyer_id = buyer_id;
	}

	public BigDecimal getItem_offer_price() {
		return Item_offer_price;
	}

	public void setItem_offer_price(BigDecimal item_offer_price) {
		Item_offer_price = item_offer_price;
	}

	public String getItem_offer_status() {
		return Item_offer_status;
	}

	public void setItem_offer_status(String item_offer_status) {
		Item_offer_status = item_offer_status;
	}

	public String getReason() {
		return Reason;
	}

	public void setReason(String reason) {
		Reason = reason;
	}	
	
	public String getTablename() {
		return Tablename;
	}

	public String getPrimarykey() {
		return Primarykey;
	}

	public ProductModel product() {
		return this.hasOne(ProductModel.class, "products", this.getProduct_id(), "Product_id");
	}
	
	public UserModel user(){
		return this.hasOne(UserModel.class, "users", this.getBuyer_id(), "User_id");
	}
	
	public ArrayList<OfferModel> all(){
		return super.all(OfferModel.class);
	}
	
	public ArrayList<OfferModel> where(String columnName, String operator, String key){
		return super.where(OfferModel.class, columnName, operator, key);
	}
	
	public OfferModel update(String fromKey) {
		return super.update(OfferModel.class, fromKey);
	}
		
	public OfferModel insert() {
		return super.insert(OfferModel.class);
	}
	
	public OfferModel find(String fromKey) {
		return super.find(OfferModel.class, fromKey);
	}
	
	public OfferModel latest() {
		return super.latest(OfferModel.class);
	}

	public Boolean delete(String fromKey) {
		return super.delete(OfferModel.class, fromKey);
	}
	
	public ArrayList<OfferModel> whereIn(String columnName, ArrayList<String> listValues){
		return super.whereIn(OfferModel.class, columnName, listValues);
	}
}
