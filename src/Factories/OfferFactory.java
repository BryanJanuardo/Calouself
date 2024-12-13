package Factories;

import java.math.BigDecimal;

import Models.OfferModel;

public class OfferFactory {

	public OfferFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static OfferModel createOffer() {
		return new OfferModel();
	}

	public static OfferModel createOffer(String offer_id, String product_id, String buyer_id, BigDecimal item_offer_price, String item_offer_status, String reason) {
		return new OfferModel(offer_id, product_id, buyer_id, item_offer_price, item_offer_status, reason);
	}
	
}
