package Controllers;

import java.math.BigDecimal;
import java.util.ArrayList;

import Models.ItemModel;
import Models.OfferModel;
import Models.ProductModel;
import Utils.Response;

public class ItemController {

	public static Response<ItemModel> UploadItem(String Seller_id, String Item_name, String Item_category, String Item_size, String Item_price) {
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
			if(new BigDecimal(Item_price).compareTo(BigDecimal.ZERO) == 0) {
				res.setMessages("Item price cannot be 0!");
				res.setIsSuccess(false);
				res.setData(null);
				return res;
			} 
			
		} catch (Exception e) {
			res.setMessages("Item price must be in number!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}
		
		return ItemModel.UploadItem(Seller_id, Item_name, Item_category, Item_size, new BigDecimal(Item_price));
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
			if(new BigDecimal(Item_price).compareTo(BigDecimal.ZERO) == 0) {
				res.setMessages("Item price cannot be 0!");
				res.setIsSuccess(false);
				res.setData(null);
				return res;
			} 
			
		} catch (Exception e) {
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
		Response<ArrayList<ProductModel>> res = ItemModel.BrowseItem(Item_name);
		return res;
	}
	
	public static Response<ArrayList<ProductModel>> ViewItem(){
		Response<ArrayList<ProductModel>> res = ItemModel.ViewItem();
		return res;
	}
	
	public static Response<ArrayList<ItemModel>> ViewSellerItem(String Seller_id){
		Response<ArrayList<ProductModel>> res = ItemModel.ViewSellerItem(Seller_id);
		ArrayList<ProductModel> data = res.getData();
		ArrayList<ItemModel> item = new ArrayList<ItemModel>();
		
		for (ProductModel product : data) {
			item.add(product.item());
		}
		
		Response<ArrayList<ItemModel>> resResult = new Response<ArrayList<ItemModel>>();
		resResult.setMessages(res.getMessages());
		resResult.setIsSuccess(res.getIsSuccess());
		resResult.setData(item);
		return resResult;
	}
	
	public static Response<ArrayList<ItemModel>> ViewRequestItem(String Item_status){
		Response<ArrayList<ProductModel>> res = ItemModel.ViewRequestItem();
		ArrayList<ProductModel> data = res.getData();
		ArrayList<ItemModel> item = new ArrayList<ItemModel>();
		
		for (ProductModel product : data) {
			item.add(product.item());
		}
		
		Response<ArrayList<ItemModel>> resResult = new Response<ArrayList<ItemModel>>();
		resResult.setMessages(res.getMessages());
		resResult.setIsSuccess(res.getIsSuccess());
		resResult.setData(item);
		return resResult;
	}
	
	public static Response<OfferModel> OfferPrice(String Product_id, String Buyer_id, String Item_price) {
		Response<OfferModel> res = new Response<OfferModel>();
		try {
			if(new BigDecimal(Item_price).compareTo(BigDecimal.ZERO) == 0) {
				res.setMessages("Item price cannot be 0!");
				res.setIsSuccess(false);
				res.setData(null);
				return res;
			} 
			
		} catch (Exception e) {
			res.setMessages("Item price must be in number!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}
		return ItemModel.OfferPrice(Product_id, Buyer_id, new BigDecimal(Item_price));
	}
	
	public static Response<OfferModel> AcceptOffer(String Offer_id) {
		return ItemModel.AcceptOffer(Offer_id);
	}
	
	public static Response<OfferModel> DeclineOffer(String Offer_id, String Reason) {
		Response<OfferModel> res = new Response<OfferModel>();
		if(Reason.isEmpty()) {
			res.setMessages("Item price cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}
		
		return ItemModel.DeclineOffer(Offer_id, Reason);
	}
	
	public static Response<ItemModel> ApproveItem(String Item_id) {
		return ItemModel.ApproveItem(Item_id);
	}
	
	public static Response<ItemModel> DeclineItem(String Item_id, String Reason) {
		Response<ItemModel> res = new Response<ItemModel>();
		if(Reason.isEmpty()) {
			res.setMessages("Item price cannot be empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}
		
		return ItemModel.DeclineItem(Item_id, Reason);
	}
	
	public static Response<ArrayList<ProductModel>> ViewAcceptedItem(){
		return ItemModel.ViewAcceptedItem();
	}
	
	public static Response<ArrayList<OfferModel>> ViewOfferedItem(String Seller_id){
		return ItemModel.ViewOfferedItem(Seller_id);
	}

	public static Response<ArrayList<OfferModel>> ViewOfferItem(String Seller_id){
		return ItemModel.ViewOfferItem(Seller_id);
	}
	
	public ItemController() {
		// TODO Auto-generated constructor stub
	}

}
