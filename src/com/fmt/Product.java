package com.fmt;
 
// This is a sub class which extends to the class Item and also initiates some variable used for the products
public class Product extends Item {
	private String unit;
	private double unitPrice;
	private double quantity;
 
	public Product(String itemCode, String itemName, String unit, double unitPrice) {
		super(itemCode, itemName);
		this.unit = unit;
		this.unitPrice = unitPrice;
	}
 
	public Product(Product item, double quantity) {
		super(item.getItemCode(), item.getItemName());
		this.unit = item.getUnit();
		this.unitPrice = item.getUnitPrice();
		this.quantity = quantity;
	}
 
	// getters for the initiated variables
	public String getUnit() {
		return unit;
	}
 
	public double getUnitPrice() {
		return unitPrice;
	}
 
	public double getQuantity() {
		return quantity;
	}
 
	@Override
	public double getTaxes() {
		return Math.round(getSubTotal() * 0.0715 * 100.0) / 100.0;
	}
 
	@Override
	public double getSubTotal() {
		return Math.round(quantity * unitPrice * 100.0) / 100.0;
	}
 
	@Override
	public double getGrandTotal() {
		return this.getTaxes() + this.getSubTotal();
	}
 
	@Override
	public String ItemInfoToString() {
		return (String.format("\n%s  (Product) %s\n   %.2f@ $%.2f / %s \n\t\t\t\t\t\t\t\t              $ %.2f",
				this.getItemCode(), this.getItemName(), this.getQuantity(), this.getUnitPrice(), this.getUnit(),
				this.getSubTotal()));
	}
}