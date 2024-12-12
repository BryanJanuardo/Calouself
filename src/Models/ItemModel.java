package Models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Factory.SellerItemFactory;
import Utils.ConnectionDB;
import Utils.Response;

public class ItemModel extends Model{
	private String Tablename = "items";
	private String Item_id;
	private String Item_name;
	private String Item_size;
	private String Item_price;
	private String Item_category;
	private String Item_status;
	private String Reason;
	
	public static Response<ItemModel> UploadItem(String Item_name, String Item_category, String Item_size, String Item_price) {
		Response<ItemModel> res = new Response<ItemModel>();
		
		String query = "INSERT INTO items (Item_id, Item_name, Item_size, Item_price, Item_category, Item_status)";
		return null;
	}
	
	public static Response<ItemModel>  EditItem(String Item_id, String Item_name, String Item_category, String Item_size, String Item_price) {
		
		return null;
	}
	
	public static Response<ItemModel>  DeleteItem(String Item_id) {
		
		return null;
	}
	
	
	//MAISH SALAH
	public static Response<ArrayList<SellerItemModel>> BrowseItem(String Item_name){
		Response<ArrayList<SellerItemModel>> res = new Response<ArrayList<SellerItemModel>>();
		
		String query = "SELECT * FROM seller_items WHERE";
		ArrayList<SellerItemModel> listSellerItems = new ArrayList<SellerItemModel>();
		
		try {
			ConnectionDB con = ConnectionDB.getInstance();
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setString(1, Item_name);
			
			ResultSet rs = con.execQuery(ps);
			
			if (!rs.next()) {
	            res.setMessages("Error: Items Not Found!");
	            res.setIsSuccess(false);
	            res.setData(null);
	            return res;
	        }
			
			while(rs.next()) {
				Integer id = rs.getInt("id");
				String itemId = rs.getString("Item_id");
				String sellerId = rs.getString("Seller_id");
				SellerItemModel sellerItem = new SellerItemModel(id, itemId, sellerId);
				
				listSellerItems.add(sellerItem);
			}
			
			res.setMessages("Success: Return All Browse Items!");
	        res.setIsSuccess(true);
	        res.setData(listSellerItems);
	        return res;
		} catch (SQLException e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
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
	
	public static Response<ArrayList<SellerItemModel>> ViewItem(){
		Response<ArrayList<SellerItemModel>> res = new Response<ArrayList<SellerItemModel>>();
		
		String query = "SELECT * FROM seller_items";
		ArrayList<SellerItemModel> listSellerItems = new ArrayList<SellerItemModel>();
		
		try {
			ConnectionDB con = ConnectionDB.getInstance();
			PreparedStatement ps = con.prepareStatement(query);
			
			ResultSet rs = con.execQuery(ps);
			
			if (!rs.next()) {
	            res.setMessages("Error: Items Not Found!");
	            res.setIsSuccess(false);
	            res.setData(null);
	            return res;
	        }
			
			while(rs.next()) {
				Integer id = rs.getInt("id");
				String itemId = rs.getString("Item_id");
				String sellerId = rs.getString("Seller_id");
				SellerItemModel sellerItem = new SellerItemModel(id, itemId, sellerId);
				
				listSellerItems.add(sellerItem);
			}
			
			res.setMessages("Success: User Authenticated!");
	        res.setIsSuccess(true);
	        res.setData(listSellerItems);
	        return res;
		} catch (SQLException e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
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
	
	public static Response<ItemModel> CheckItemValidation(String Item_name, String Item_category, String Item_size, String Item_price) {
		Response<ArrayList<SellerItemModel>> res = new Response<ArrayList<SellerItemModel>>();
		
		String query = "SELECT * FROM seller_items";
		ArrayList<SellerItemModel> listSellerItems = new ArrayList<SellerItemModel>();
		
		try {
			ConnectionDB con = ConnectionDB.getInstance();
			PreparedStatement ps = con.prepareStatement(query);
			
			ResultSet rs = con.execQuery(ps);
			
			if (!rs.next()) {
	            res.setMessages("Error: Items Not Found!");
	            res.setIsSuccess(false);
	            res.setData(null);
//	            return res;
	        }
			
			while(rs.next()) {
				Integer id = rs.getInt("id");
				String itemId = rs.getString("Item_id");
				String sellerId = rs.getString("Seller_id");
				SellerItemModel sellerItem = new SellerItemModel(id, itemId, sellerId);
				
				listSellerItems.add(sellerItem);
			}
			
			res.setMessages("Success: User Authenticated!");
	        res.setIsSuccess(true);
	        res.setData(listSellerItems);
//	        return res;
		} catch (SQLException e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
//	        return res;
	    } catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
//	        return res;
	    }
		return null;
	}
	
	public static Response<ArrayList<SellerItemModel>> ViewRequestItem(String Item_id, String Item_status){
		Response<ArrayList<SellerItemModel>> res = new Response<ArrayList<SellerItemModel>>();
		
		String query = "SELECT * FROM seller_items";
		ArrayList<SellerItemModel> listSellerItems = new ArrayList<SellerItemModel>();
		
		try {
			ConnectionDB con = ConnectionDB.getInstance();
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = con.execQuery(ps);
			
			if (!rs.next()) {
	            res.setMessages("Error: Items Not Found!");
	            res.setIsSuccess(false);
	            res.setData(null);
	            return res;
	        }
			
			while(rs.next()) {
				Integer id = rs.getInt("id");
				String itemId = rs.getString("Item_id");
				String sellerId = rs.getString("Seller_id");
				SellerItemModel sellerItem = new SellerItemModel(id, itemId, sellerId);
				
				listSellerItems.add(sellerItem);
			}
			
			ArrayList<ItemModel> itemsList = new ArrayList<ItemModel>();
			ArrayList<SellerItemModel> resultSellerItems = new ArrayList<SellerItemModel>();
			for (SellerItemModel sellerItem : listSellerItems) {
				ItemModel item = sellerItem.item();
				if(item.getItem_status().equals("Pending")) {
//					resultSellerItems.add();
				}
			}
			
			
			
			res.setMessages("Success: User Authenticated!");
	        res.setIsSuccess(true);
	        res.setData(listSellerItems);
	        return res;
		} catch (SQLException e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
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

	public String getReason() {
		return Reason;
	}

	public void setReason(String reason) {
		Reason = reason;
	}
	
	public String getTablename() {
		return Tablename;
	}

	public ArrayList<SellerItemModel> sellerItem() {
		return this.hasMany(SellerItemModel.class, "seller_items", this.getItem_id(), "Item_id");
	}
	
	public ArrayList<ItemModel> all(){
		return super.all(ItemModel.class);
	}
	
	public ArrayList<ItemModel> where(String columnName, String operator, String key){
		return super.where(ItemModel.class, columnName, operator, key);
	}
}