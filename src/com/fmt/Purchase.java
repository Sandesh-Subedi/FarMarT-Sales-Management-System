package com.fmt;
 
/*
 * This class represents the equipment that is purchased.
 */
public class Purchase extends Equipment {
	private double purchasePrice;
 
	public Purchase(String itemCode, String itemType, String itemName, String model, double purchasePrice) {
		super(itemCode, itemName, model);
		this.purchasePrice = purchasePrice;
	}
 
	public Purchase(Equipment item, double purchasePrice) {
		super(item.getItemCode(), item.getItemName(), item.getModel());
		this.purchasePrice = purchasePrice;
	}
 
	public double getPurchasePrice() {
		return this.purchasePrice;
	}
 
	@Override
	public double getSubTotal() {
		return this.purchasePrice;
	}
 
	@Override
	public double getTaxes() {
		return 0.0;
	}
 
	@Override
	public double getGrandTotal() {
		return getSubTotal();
	}
 
	public String ItemInfoToString() {
		return (String.format("\n%s\t\t(Purchase) %s-%s \n\t\t\t\t\t\t\t\t\t\t$ %.2f", this.getItemCode(),
				this.getItemName(), this.getModel(), this.getSubTotal()));
	}
}