package com.fmt;
 
/* This is a sub-class which extends to the class Item and
*  initiate the different variables related to the equipment
*/
public class Equipment extends Item {
	private String model;
 
	public Equipment(String itemCode, String itemName, String model) {
		super(itemCode, itemName);
		this.model = model;
	}
 
	public String ItemInfoToString() {
		return null;
	}
 
	public String getModel() {
		return model;
	}
 
	@Override
	public double getTaxes() {
		return 0.0;
	}
 
	public double getSubTotal() {
		return 0.0;
	}
 
	public double getGrandTotal() {
		return getTaxes() + getSubTotal();
	}
}