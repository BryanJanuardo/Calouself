package Models;

import java.math.BigDecimal;
import java.util.ArrayList;

import Factories.ItemFactory;
import Factories.OfferFactory;
import Factories.ProductFactory;
import Helpers.IdGeneratorHelper;
import Utils.Response;

public class ItemModel extends Model{
	private final String Tablename = "items";
	private final String Primarykey = "Item_id";
	
	private String Item_id;
	private String Item_name;
	private String Item_size;
	private BigDecimal Item_price;
	private String Item_category;
	private String Item_status;
	private String Reason;
	
	public static Response<ItemModel> UploadItem(String Seller_id, String Item_name, String Item_category, String Item_size, BigDecimal Item_price) {
		Response<ItemModel> res = new Response<ItemModel>();
		
		try {
			res = ItemModel.CheckItemValidation(Item_name, Item_category, Item_size, Item_price);
			
			if(!res.getIsSuccess()) {
				return res;				
			}
			
			ItemModel item = ItemFactory.createItem(IdGeneratorHelper.generateNewId(ItemFactory.createItem().latest().getItem_id(), "IT"), 
					Item_name, Item_size, Item_price, Item_category, "Pending", null);
			item.insert();
			
			ProductModel product = ProductFactory.createProduct(IdGeneratorHelper.generateNewId(ProductFactory.createProduct().latest().getProduct_id(), "PR"), item.getItem_id(), Seller_id);
			product.insert();
			
			res.setMessages("Success: Item Uploaded!");
			res.setIsSuccess(true);
			res.setData(item);
			return res;
		} catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}
	
	public static Response<ItemModel>  EditItem(String Item_id, String Item_name, String Item_category, String Item_size, BigDecimal Item_price) {
		Response<ItemModel> res = new Response<ItemModel>();
		
		try {
			res = ItemModel.CheckItemValidation(Item_id, Item_name, Item_category, Item_size, Item_price);

			if(!res.getIsSuccess()) {
				return res;
			}
			
			ItemModel item = res.getData();
			item.setItem_name(Item_name);
			item.setItem_category(Item_category);
			item.setItem_size(Item_size);
			item.setItem_price(Item_price);
			
			item.update(item.getItem_id());
			res.setMessages("Success: Item Updated!");
			res.setIsSuccess(true);
			res.setData(item);
			return res;
		} catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}
	
	public static Response<ItemModel>  DeleteItem(String Item_id) {
		Response<ItemModel> res = new Response<ItemModel>();
		
		try {
			Boolean item = ItemFactory.createItem().find(Item_id).delete(Item_id);
			
			if(!item) {
				res.setMessages("Error: Deleting Item Failed!");
				res.setIsSuccess(false);
				res.setData(null);
				return res;
			}
			
			res.setMessages("Success: Item Deleted!");
			res.setIsSuccess(true);
			res.setData(null);
			return res;
		} catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }		
	}
	
	
	public static Response<ArrayList<ProductModel>> BrowseItem(String Item_name){
		Response<ArrayList<ProductModel>> res = new Response<ArrayList<ProductModel>>();
		
		try {
			ArrayList<ProductModel> listProduct = ProductFactory.createProduct().all();
			ArrayList<String> ids = new ArrayList<String>();
			
			for (ProductModel product : listProduct) {
				ItemModel item = product.item();
				if(item.getItem_name().toLowerCase().contains(Item_name.toLowerCase()) && item.getItem_status().equals("Approved")) {
					ids.add(product.getProduct_id());
				}
			}
			
			listProduct = ProductFactory.createProduct().whereIn("Product_id", ids);
			
			res.setMessages("Success: Retrived All Browsed items!");
			res.setIsSuccess(true);
			res.setData(listProduct);
			return res;
		} catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}
	
	public static Response<ArrayList<ProductModel>> ViewItem(){
		Response<ArrayList<ProductModel>> res = new Response<ArrayList<ProductModel>>();
		try {
			ArrayList<ProductModel> listProduct = ProductFactory.createProduct().all();
			ArrayList<String> ids = new ArrayList<String>();
			
			for (ProductModel product : listProduct) {
				ItemModel item = product.item();
				if(item.getItem_status().equals("Approved")) {
					ids.add(product.getProduct_id());
				}
			}
			
			listProduct = ProductFactory.createProduct().whereIn("Product_id", ids);
			res.setMessages("Success: Retrived All Browsed items!");
			res.setIsSuccess(true);
			res.setData(listProduct);
			return res;
		} catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}
	
	public static Response<ArrayList<ProductModel>> ViewSellerItem(String Seller_id){
		Response<ArrayList<ProductModel>> res = new Response<ArrayList<ProductModel>>();
		
		try {
			ArrayList<ProductModel> listProduct = ProductFactory.createProduct().where("Seller_id", "=", Seller_id);
			
			res.setMessages("Success: Retrived All Seller Items!");
			res.setIsSuccess(true);
			res.setData(listProduct);
			return res;
		} catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}
	
	public static Response<OfferModel> CheckItemValidation(String Product_id, BigDecimal Item_price) {
		Response<OfferModel> res = new Response<OfferModel>();
		ProductModel product = ProductFactory.createProduct().find(Product_id);
		
		if(product == null) {
			res.setMessages("Error: Product Not Found!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		} else if(Item_price == null) {
			res.setMessages("Error: Item Price Cannot Be Empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		} else if(Item_price.compareTo(BigDecimal.ZERO) <= 0) {
			res.setMessages("Error: Item Price Cannot Be 0!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		} else if(!(Item_price instanceof BigDecimal)) {
			res.setMessages("Error: Item Price Must Be In Number");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}
		
		res.setMessages("Success: Item Validated!");
		res.setIsSuccess(true);
		res.setData(null);
		return res;
	}
	
	public static Response<ItemModel> CheckItemValidation(String Item_name, String Item_category, String Item_size, BigDecimal Item_price) {
		Response<ItemModel> res = new Response<ItemModel>();
		
		if(Item_name.isEmpty()) {
			res.setMessages("Error: Item Name Cannot Be Empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Item_name.length() < 3) {
			res.setMessages("Error: Item Name Must At Least Be 3 Character Long!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Item_category.isEmpty()) {
			res.setMessages("Error: Item Category Cannot Be Empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Item_category.length() < 3) {
			res.setMessages("Error: Item Category Must At Least Be 3 Character Long!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Item_size.isEmpty()) {
			res.setMessages("Error: Item Size Cannot Be Empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Item_price == null) {
			res.setMessages("Error: Item Price Cannot Be Empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Item_price.compareTo(BigDecimal.ZERO) <= 0) {
			res.setMessages("Error: Item Price Cannot Be 0!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if (!(Item_price instanceof BigDecimal)) {
			res.setMessages("Error: Item Price Must Be In Number");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}
		
		res.setMessages("Success: Item Validated!");
		res.setIsSuccess(true);
		res.setData(null);
		return res;
	}
	
	public static Response<ItemModel> CheckItemValidation(String Item_id, String Item_name, String Item_category, String Item_size, BigDecimal Item_price) {
		Response<ItemModel> res = new Response<ItemModel>();
		ItemModel item = ItemFactory.createItem().find(Item_id);
		
		if(item == null) {
			res.setMessages("Error: Item Not Found!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Item_name.isEmpty()) {
			res.setMessages("Error: Item Name Cannot Be Empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Item_name.length() < 3) {
			res.setMessages("Error: Item Name Must At Least Be 3 Character Long!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Item_category.isEmpty()) {
			res.setMessages("Error: Item Category Cannot Be Empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Item_category.length() < 3) {
			res.setMessages("Error: Item Category Must At Least Be 3 Character Long!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Item_size.isEmpty()) {
			res.setMessages("Error: Item Size Cannot Be Empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Item_price == null) {
			res.setMessages("Error: Item Price Cannot Be Empty!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if(Item_price.compareTo(BigDecimal.ZERO) <= 0) {
			res.setMessages("Error: Item Price Cannot Be 0!");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}else if (!(Item_price instanceof BigDecimal)) {
			res.setMessages("Error: Item Price Must Be In Number");
			res.setIsSuccess(false);
			res.setData(null);
			return res;
		}
		
		res.setMessages("Success: Item Validated!");
		res.setIsSuccess(true);
		res.setData(item);
		return res;
	}
	
	public static Response<ArrayList<ProductModel>> ViewRequestItem(){
		Response<ArrayList<ProductModel>> res = new Response<ArrayList<ProductModel>>();
		
		try {
			ArrayList<ProductModel> listProduct = ProductFactory.createProduct().all();
			ArrayList<String> ids = new ArrayList<String>();
			
			for (ProductModel product : listProduct) {
				if(product.item().getItem_status().equals("Pending")) {
					ids.add(product.getProduct_id());
				}
			}
			
			listProduct = ProductFactory.createProduct().whereIn("Product_id", ids);
			
			res.setMessages("Success: Retrived All Browsed items!");
			res.setIsSuccess(true);
			res.setData(listProduct);
			return res;
		} catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}
	
	public static Response<OfferModel> OfferPrice(String Product_id, String Buyer_id, BigDecimal Item_price) {
		Response<OfferModel> res = new Response<OfferModel>();
		
		try {
			res = CheckItemValidation(Product_id, Item_price);
			if(!res.getIsSuccess()) {
				return res;
			}
			
			ArrayList<OfferModel> offers = OfferFactory.createOffer().where("Product_id", "=", Product_id);
			OfferModel buyerOffer = null;
			for (OfferModel offer : offers){
				if(offer.getBuyer_id().equals(Buyer_id)) {
					buyerOffer = offer;
					break;
				}
			}
			
			if(buyerOffer == null) {
				buyerOffer = OfferFactory.createOffer(IdGeneratorHelper.generateNewId(OfferFactory.createOffer().latest().getOffer_id(), "OF")
						, Product_id, Buyer_id, Item_price, "Pending", null);
				
				buyerOffer.insert();				
			}else {
				if(buyerOffer.getItem_offer_price().compareTo(Item_price) >= 0) {
					res.setMessages("Item Price Cannot Be Lower Than The Highest Offer");
					res.setIsSuccess(false);
					res.setData(null);
					return res;
				}
				
				buyerOffer.setItem_offer_price(Item_price);
				buyerOffer.update(buyerOffer.getOffer_id());
			}
			
			res.setMessages("Success: Item Offered!");
			res.setIsSuccess(true);
			res.setData(buyerOffer);
			return res;
		} catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}
	
	public static Response<OfferModel> AcceptOffer(String Offer_id) {
		Response<OfferModel> res = new Response<OfferModel>();
		
		try {
			OfferModel offer = OfferFactory.createOffer().find(Offer_id);
			
			if(offer == null) {
				res.setMessages("Error: Offer Not Found!");
				res.setIsSuccess(false);
				res.setData(null);
				return res;
			}
			
			offer.setItem_offer_status("Accepted");
			offer.update(Offer_id);
			
			TransactionModel.PurchaseItem(offer.getBuyer_id(), offer.getProduct_id());
			res.setMessages("Success: Offer Accepted!");
			res.setIsSuccess(true);
			res.setData(offer);
			return res;
		} catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}
	
	public static Response<OfferModel> DeclineOffer(String Offer_id, String Reason) {
		Response<OfferModel> res = new Response<OfferModel>();
		
		try {
			OfferModel offer = OfferFactory.createOffer().find(Offer_id);
			
			if(offer == null) {
				res.setMessages("Error: Offer Not Found!");
				res.setIsSuccess(false);
				res.setData(null);
				return res;
			}
			
			offer.setItem_offer_status("Declined");
			offer.setReason(Reason);
			offer.update(Offer_id);
			
			res.setMessages("Success: Offer Declined!");
			res.setIsSuccess(true);
			res.setData(offer);
			return res;
		} catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}
	
	public static Response<ItemModel> ApproveItem(String Item_id) {
		Response<ItemModel> res = new Response<ItemModel>();
		
		try {
			ItemModel item = ItemFactory.createItem().find(Item_id);
			
			if(item == null) {
				res.setMessages("Error: Item Not Found!");
				res.setIsSuccess(false);
				res.setData(null);
				return res;
			}

			item.setItem_status("Approved");
			item.update(Item_id);
			
			res.setMessages("Success: Item Approved!");
			res.setIsSuccess(true);
			res.setData(item);
			return res;
		} catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}
	
	public static Response<ItemModel> DeclineItem(String Item_id, String Reason) {
		Response<ItemModel> res = new Response<ItemModel>();
		
		try {
			ItemModel item = ItemFactory.createItem().find(Item_id);
			
			if(item == null) {
				res.setMessages("Error: Item Not Found!");
				res.setIsSuccess(false);
				res.setData(null);
				return res;
			}

			item.setItem_status("Declined");
			item.setReason(Reason);
			item.update(Item_id);
			
			res.setMessages("Success: Item Declined!");
			res.setIsSuccess(true);
			res.setData(item);
			return res;
		} catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}
	
	public static Response<ArrayList<ProductModel>> ViewAcceptedItem(){
		Response<ArrayList<ProductModel>> res = new Response<ArrayList<ProductModel>>();
		
		try {
			ArrayList<ProductModel> listProduct = ProductFactory.createProduct().all();
			ArrayList<String> ids = new ArrayList<String>();
			
			for (ProductModel product : listProduct) {
				if(product.item().getItem_status().equals("Approved")) {
					ids.add(product.getProduct_id());
				}
			}
			
			listProduct = ProductFactory.createProduct().whereIn("Product_id", ids);
			
			res.setMessages("Success: Retrived All Approved items!");
			res.setIsSuccess(true);
			res.setData(listProduct);
			return res;
		} catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}
	
	public static Response<ArrayList<OfferModel>> ViewOfferItem(String User_id){
		Response<ArrayList<OfferModel>> res = new Response<ArrayList<OfferModel>>();
		
		try {
			ArrayList<ProductModel> listProduct = ProductFactory.createProduct().where("Seller_id", "=", User_id);
			ArrayList<OfferModel> listOffer = new ArrayList<OfferModel>();
			
			for (ProductModel product : listProduct) {
				for (OfferModel offer : product.offers()) {
					if(offer.getItem_offer_status().equals("Offered")) {
						listOffer.add(offer);											
					}
				}
			}
			
			res.setMessages("Success: Retrived All Offered items!");
			res.setIsSuccess(true);
			res.setData(listOffer);
			return res;
		} catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}
	
	public ItemModel() {
		// TODO Auto-generated constructor stub
	}

	public ItemModel(String item_id, String item_name, String item_size, BigDecimal item_price, String item_category,
			String item_status, String reason) {
		super();
		Item_id = item_id;
		Item_name = item_name;
		Item_size = item_size;
		Item_price = item_price;
		Item_category = item_category;
		Item_status = item_status;
		Reason = reason;
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

	public BigDecimal getItem_price() {
		return Item_price;
	}

	public void setItem_price(BigDecimal item_price) {
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

	public ArrayList<ProductModel> product() {
		return this.hasMany(ProductModel.class, "products", this.getItem_id(), "Item_id");
	}
	
	public ArrayList<ItemModel> all(){
		return super.all(ItemModel.class);
	}
	
	public ArrayList<ItemModel> where(String columnName, String operator, String key){
		return super.where(ItemModel.class, columnName, operator, key);
	}
	
	public ItemModel update(String fromKey) {
		return super.update(ItemModel.class, fromKey);
	}
	
	public ItemModel insert() {
		return super.insert(ItemModel.class);
	}
	
	public ItemModel find(String fromKey) {
		return super.find(ItemModel.class, fromKey);
	}
	
	public ItemModel latest() {
		return super.latest(ItemModel.class);
	}
	
	public Boolean delete(String fromKey) {
		return super.delete(ItemModel.class, fromKey);
	}
	
	public ArrayList<ItemModel> whereIn(String columnName, ArrayList<String> listValues){
		return super.whereIn(ItemModel.class, columnName, listValues);
	}
}