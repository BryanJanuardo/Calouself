package Factories;

import Models.TransactionModel;

public class TransactionFactory {

	public TransactionFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static TransactionModel createTransaction() {
		return new TransactionModel();
	}
	
	public static TransactionModel createTransaction(String user_id, String product_id, String transaction_id) {
		return new TransactionModel(user_id, product_id, transaction_id);
	}

}
