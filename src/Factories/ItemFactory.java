package Factories;

import java.math.BigDecimal;

import Models.ItemModel;

public class ItemFactory {

	public ItemFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static ItemModel createItem() {
		return new ItemModel();
	}
	
	public static ItemModel createItem(String Item_id, String Item_name, String Item_size, BigDecimal Item_price, String Item_category, String Item_status, String Reason) {
		return new ItemModel(Item_id, Item_name, Item_size, Item_price, Item_category, Item_status, Reason);
	}

}
