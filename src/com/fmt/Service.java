package com.fmt;

// This is a sub class which extends to the class Item and initiates hourly Rate required for the services.
public class Service extends Item {
	private double hourlyRate;
	private double serviceHours;

	public Service(String itemCode, String itemName, double hourlyRate) {
		super(itemCode, itemName);
		this.hourlyRate = hourlyRate;
	}

	public Service(Service item, double serviceHours) {
		super(item.getItemCode(), item.getItemName());
		this.hourlyRate = item.getHourlyRate();
		this.serviceHours = serviceHours;
	}

	// Getters
	public double getHourlyRate() {
		return this.hourlyRate;
	}

	@Override
	public double getSubTotal() {
		return Math.round((this.serviceHours * this.hourlyRate) * 100.0) / 100.0;
	}

	@Override
	public double getTaxes() {
		return Math.round(((getSubTotal() * 0.0345)) * 100.0) / 100.0;
	}

	public double getServiceHours() {
		return this.serviceHours;
	}

	@Override
	public double getGrandTotal() {
		return (getTaxes() + getSubTotal());
	}

	@Override
	public String ItemInfoToString() {
		return (String.format("\n%s  (Service) %s\n   %.2f@ $%.2f/ hr\n\t\t\t\t\t\t\t\t\t      $ %.2f",
				this.getItemCode(), this.getItemName(), this.getServiceHours(), this.getHourlyRate(),
				this.getSubTotal()));
	}
}