package Controllers;

import java.math.BigDecimal;
import java.util.ArrayList;

import Models.ItemModel;
import Models.OfferModel;
import Models.ProductModel;
import Utils.Response;

public class ItemController {

	public static Response<ItemModel> UploadItem(String Item_name, String Item_category, String Item_size, String Item_price) {
		Response<ItemModel> res = new Response<ItemModel>();
		
		if(Item_name.isEmpty()) {
			res.setMessages("Item name cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		} else if(Item_name.length() < 3) {
			res.setMessages("Iten name must at least be 3 character long!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		} else if(Item_category.isEmpty()) {
			res.setMessages("Item category cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		} else if(Item_category.length() < 3) {
			res.setMessages("Item category cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		} else if(Item_size.isEmpty()) {
			res.setMessages("Item size cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		} else if(Item_price.isEmpty()) {
			res.setMessages("Item price cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}
		
		try {
			if(Integer.parseInt(Item_price) == 0) {
				res.setMessages("Item price cannot be 0!");
				res.setIsSuccess(false);
				res.setData(null);
				return res;
			} 
			
		} catch (NumberFormatException e) {
			res.setMessages("Item price must be in number!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}
		
		return ItemModel.UploadItem(Item_name, Item_category, Item_size, new BigDecimal(Item_price));
	}
	
	public static Response<ItemModel> EditItem(String Item_id, String Item_name, String Item_category, String Item_size, String Item_price) {
		Response<ItemModel> res = new Response<ItemModel>();
		
		if(Item_name.isEmpty()) {
			res.setMessages("Item name cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		} else if(Item_name.length() < 3) {
			res.setMessages("Iten name must at least be 3 character long!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		} else if(Item_category.isEmpty()) {
			res.setMessages("Item category cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		} else if(Item_category.length() < 3) {
			res.setMessages("Item category cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		} else if(Item_size.isEmpty()) {
			res.setMessages("Item size cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		} else if(Item_price.isEmpty()) {
			res.setMessages("Item price cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}
		
		try {
			if(Integer.parseInt(Item_price) == 0) {
				res.setMessages("Item price cannot be 0!");
				res.setIsSuccess(false);
				res.setData(null);
				return res;
			} 
			
		} catch (NumberFormatException e) {
			res.setMessages("Item price must be in number!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}
		
		return ItemModel.EditItem(Item_id, Item_name, Item_category, Item_size, new BigDecimal(Item_price));
	}
	
	public static Response<ItemModel> DeleteItem(String Item_id) {		
		return ItemModel.DeleteItem(Item_id);
	}
	
	public static Response<ArrayList<ProductModel>> BrowseItem(String Item_name){
		return ItemModel.BrowseItem(Item_name);
	}
	
	public static Response<ArrayList<ProductModel>> ViewItem(){
		return ItemModel.ViewItem();
	}
	
	public static ArrayList<ItemModel> ViewSellerItem(String Seller_id){
		Response<ArrayList<ProductModel>> res = ItemModel.ViewSellerItem(Seller_id);
		ArrayList<ProductModel> data = res.getData();
		ArrayList<ItemModel> item = new ArrayList<ItemModel>();
		
		for (ProductModel product : data) {
			item.add(product.item());
		}
		
		return item;
	}
	
	public static Response<ArrayList<ProductModel>> ViewRequestItem(String Item_id, String Item_status){
		return ItemModel.ViewRequestItem();
	}
	
	public static Response<OfferModel> OfferPrice(String Item_id, String Buyer_id, String Item_price) {
		return ItemModel.OfferPrice(Item_id, Buyer_id, new BigDecimal(Item_price));
	}
	
	public static Response<OfferModel> AcceptOffer(String Item_id) {
		return ItemModel.AcceptOffer(Item_id);
	}
	
	public static Response<OfferModel> DeclineOffer(String Item_id, String Reason) {
		return ItemModel.DeclineOffer(Item_id, Reason);
	}
	
	public static Response<ItemModel> ApproveItem(String Item_id) {
		return ItemModel.ApproveItem(Item_id);
	}
	
	public static Response<ItemModel> DeclineItem(String Item_id, String Reason) {
		return ItemModel.DeclineItem(Item_id, Reason);
	}
	
	public static Response<ArrayList<ProductModel>> ViewAcceptedItem(){
		return ItemModel.ViewAcceptedItem();
	}
	
	public static Response<ArrayList<OfferModel>> ViewOfferItem(String Seller_id){
		return ItemModel.ViewOfferItem(Seller_id);
	}
	
	public ItemController() {
		// TODO Auto-generated constructor stub
	}

}
