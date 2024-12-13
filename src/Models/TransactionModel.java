package Models;

import java.util.ArrayList;

import Factories.TransactionFactory;
import Factories.WishlistFactory;
import Helpers.IdGeneratorHelper;
import Utils.Response;

public class TransactionModel extends Model{
	private final String Tablename = "transactions";
	private final String Primarykey = "Transaction_id";
	
	private String User_id;
	private String Product_id;
	private String Transaction_id;	
	
	public static Response<TransactionModel> PurchaseItem(String Buyer_id, String Product_id) {
		Response<TransactionModel> res = new Response<TransactionModel>();
	    
	    try {
	    	TransactionModel transaction = TransactionFactory.createTransaction(Buyer_id, Product_id, 
	    			IdGeneratorHelper.generateNewId(TransactionFactory.createTransaction().latest().getTransaction_id(), "TR"));
	    	
	    	transaction.insert();
	        
	    	ArrayList<WishlistModel> wishlists = WishlistFactory.createWishlist().where("Product_id", "=", Product_id);
	    	for (WishlistModel wishlist : wishlists) {
				WishlistModel.RemoveWishlist(wishlist.getWishlist_id());
			}
	    	
	        res.setMessages("Success: Item Purchased!");
	        res.setIsSuccess(true);
	        res.setData(transaction);
	        return res;
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}
	
	public static Response<ArrayList<TransactionModel>> ViewHistory(String User_id){
		Response<ArrayList<TransactionModel>> res = new Response<ArrayList<TransactionModel>>();
		
		try {
			ArrayList<TransactionModel> listTransaction = TransactionFactory.createTransaction().where("User_id", "=", User_id);
			
			res.setMessages("Success: All Transaction History Retrieved!");
			res.setIsSuccess(true);
			res.setData(listTransaction);
			return res;
		} catch (Exception e) {
	        e.printStackTrace();
	        res.setMessages("Error: " + e.getMessage() + "!");
	        res.setIsSuccess(false);
	        res.setData(null);
	        return res;
	    }
	}
	
	
	
	public TransactionModel() {
		// TODO Auto-generated constructor stub
	}

	public TransactionModel(String user_id, String product_id, String transaction_id) {
		super();
		User_id = user_id;
		Product_id = product_id;
		Transaction_id = transaction_id;
	}

	public String getUser_id() {
		return User_id;
	}

	public void setUser_id(String user_id) {
		User_id = user_id;
	}

	public String getProduct_id() {
		return Product_id;
	}

	public void setProduct_id(String product_id) {
		Product_id = product_id;
	}

	public String getTransaction_id() {
		return Transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		Transaction_id = transaction_id;
	}

	public String getTablename() {
		return Tablename;
	}
	
	public String getPrimarykey() {
		return this.Primarykey;
	}

	public ProductModel product() {
		return this.hasOne(ProductModel.class, "products", this.getProduct_id(), "Product_id");
	}
	
	public UserModel user() {
		return this.hasOne(UserModel.class, "users", this.getUser_id(), "User_id");
	}
	
	public ArrayList<TransactionModel> all(){
		return super.all(TransactionModel.class);
	}
	
	public ArrayList<TransactionModel> where(String columnName, String operator, String key){
		return super.where(TransactionModel.class, columnName, operator, key);
	}

	public TransactionModel update(String fromKey) {
		return super.update(TransactionModel.class, fromKey);
	}
	
	public TransactionModel insert() {
		return super.insert(TransactionModel.class);
	}
	
	public TransactionModel find(String fromKey) {
		return super.find(TransactionModel.class, fromKey);
	}
	
	public TransactionModel latest() {
		return super.latest(TransactionModel.class);
	}
	
	public Boolean delete(String fromKey) {
		return super.delete(TransactionModel.class, fromKey);
	}
	
	public ArrayList<TransactionModel> whereIn(String columnName, ArrayList<String> listValues){
		return super.whereIn(TransactionModel.class, columnName, listValues);
	}
}
