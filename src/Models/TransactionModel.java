package Models;

import java.util.ArrayList;

import Utils.Response;

public class TransactionModel extends Model{
	private final String Tablename = "transactions";
	private final String Primarykey = "Transaction_id";
	
	private String User_id;
	private String Product_id;
	private String Transaction_id;	
	
	public static Response<TransactionModel> PurchaseItem(String User_id, String Item_id) {

		return null;
	}
	
	public static Response<ArrayList<TransactionModel>> ViewHistory(String User_id){
		
		return null;
	}
	
	public static Response<TransactionModel> CreateTransaction(String Transaction_id) {

		return null;
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
	
	public TransactionModel find(String id) {
		return super.find(TransactionModel.class, id);
	}
	
	public TransactionModel latest() {
		return super.latest(TransactionModel.class);
	}
}
