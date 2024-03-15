package com.fmt;
 
/*
 * This is the super class which consists of items data that are used into different sub classes
 * like equipment, product and services.
 */
public abstract class Item {
	private String itemCode;
	protected String itemName;
 
	public Item(String itemCode, String itemName) {
		super();
		this.itemCode = itemCode;
		this.itemName = itemName;
	}
 
	// get methods for the initiated variables
	public String getItemCode() {
		return itemCode;
	}
 
	public String getItemName() {
		return itemName;
	}
 
	// Abstract method to be implemented in the respective child classes.
	public abstract double getTaxes();
 
	public abstract double getSubTotal();
 
	public abstract String ItemInfoToString();
 
	public double getGrandTotal() {
		return Math.round(getTaxes() + getSubTotal());
	}
}