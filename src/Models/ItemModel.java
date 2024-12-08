package Models;

import java.util.ArrayList;

import Utils.Response;

public class ItemModel{
	private String Item_id;
	private String Item_name;
	private String Item_size;
	private String Item_price;
	private String Item_category;
	private String Item_status;
	private String Item_wishlist;
	private String item_offer_status;
	
	public static Response<ItemModel> UploadItem(String Item_name, String Item_category, String Item_size, String Item_price) {
		
		return null;
	}
	
	public static Response<ItemModel>  EditItem(String Item_id, String Item_name, String Item_category, String Item_size, String Item_price) {
		
		return null;
	}
	
	public static Response<ItemModel>  DeleteItem(String Item_id) {
		
		return null;
	}
	
	public static Response<ArrayList<ItemModel>> BrowseItem(String Item_name){
		
		return null;
	}
	
	public static Response<ArrayList<ItemModel>> ViewItem(){
		
		return null;
	}
	
	public static Response<ItemModel> CheckItemValidation(String Item_name, String Item_category, String Item_size, String Item_price) {
		
		return null;
	}
	
	public static Response<ArrayList<ItemModel>> ViewRequestItem(String Item_id, String Item_status){
		
		return null;
	}
	
	public static Response<ItemModel> OfferPrice(String Item_id, String Item_price) {
		
		return null;
	}
	
	public static Response<ItemModel> AcceptOffer(String Item_id) {
		
		return null;
	}
	
	public static Response<ItemModel> DeclineOffer(String Item_id) {
		
		return null;
	}
	
	public static Response<ItemModel> ApproveItem(String Item_id) {
		
		return null;
	}
	
	public static Response<ItemModel> DeclineItem(String Item_id) {
		
		return null;
	}
	
	public static Response<ArrayList<ItemModel>> ViewAcceptedItem(String Item_id){
		
		return null;
	}
	
	public static Response<ArrayList<ItemModel>> ViewOfferItem(String Item_id){
		
		return null;
	}
	
	public ItemModel() {
		// TODO Auto-generated constructor stub
	}
	
	public ItemModel(String item_id, String item_name, String item_size, String item_price, String item_category,
			String item_status, String item_wishlist, String item_offer_status) {
		super();
		Item_id = item_id;
		Item_name = item_name;
		Item_size = item_size;
		Item_price = item_price;
		Item_category = item_category;
		Item_status = item_status;
		Item_wishlist = item_wishlist;
		this.item_offer_status = item_offer_status;
	}

	public String getItem_id() {
		return Item_id;
	}

	public void setItem_id(String item_id) {
		Item_id = item_id;
	}

	public String getItem_name() {
		return Item_name;
	}

	public void setItem_name(String item_name) {
		Item_name = item_name;
	}

	public String getItem_size() {
		return Item_size;
	}

	public void setItem_size(String item_size) {
		Item_size = item_size;
	}

	public String getItem_price() {
		return Item_price;
	}

	public void setItem_price(String item_price) {
		Item_price = item_price;
	}

	public String getItem_category() {
		return Item_category;
	}

	public void setItem_category(String item_category) {
		Item_category = item_category;
	}

	public String getItem_status() {
		return Item_status;
	}

	public void setItem_status(String item_status) {
		Item_status = item_status;
	}

	public String getItem_wishlist() {
		return Item_wishlist;
	}

	public void setItem_wishlist(String item_wishlist) {
		Item_wishlist = item_wishlist;
	}

	public String getItem_offer_status() {
		return item_offer_status;
	}

	public void setItem_offer_status(String item_offer_status) {
		this.item_offer_status = item_offer_status;
	}

}
