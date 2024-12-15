package Controllers;

import java.util.ArrayList;

import Models.TransactionModel;
import Utils.Response;

public class TransactionController {

	public Response<TransactionModel> PurchaseItem(String User_id, String Item_id) {
		return TransactionModel.PurchaseItem(User_id, Item_id);
	}
	
	public Response<ArrayList<TransactionModel>> ViewHistory(String User_id){
		return TransactionModel.ViewHistory(User_id);
	}
	
	public TransactionController() {
		// TODO Auto-generated constructor stub
	}

}
