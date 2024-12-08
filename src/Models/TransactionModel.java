package Models;

import java.util.ArrayList;

import Utils.Response;

public class TransactionModel {
	private String User_id;
	private String Item_id;
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

	public TransactionModel(String user_id, String item_id, String transaction_id) {
		super();
		User_id = user_id;
		Item_id = item_id;
		Transaction_id = transaction_id;
	}

	public String getUser_id() {
		return User_id;
	}

	public void setUser_id(String user_id) {
		User_id = user_id;
	}

	public String getItem_id() {
		return Item_id;
	}

	public void setItem_id(String item_id) {
		Item_id = item_id;
	}

	public String getTransaction_id() {
		return Transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		Transaction_id = transaction_id;
	}

}
