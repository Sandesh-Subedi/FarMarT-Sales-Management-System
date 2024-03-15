package com.fmt;
 
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
 
/*
 * This class represents whether the item is leased.
 */
public class Lease extends Equipment {
	private LocalDate startLeaseDate;
	private LocalDate endLeaseDate;
	private double leaseFee;
 
	public Lease(Equipment equipment, LocalDate startLeaseDate, LocalDate endLeaseDate, double leaseFee) {
		super(equipment.getItemCode(), equipment.getItemName(), equipment.getModel());
		this.startLeaseDate = startLeaseDate;
		this.endLeaseDate = endLeaseDate;
		this.leaseFee = leaseFee;
	}
 
	// Getters
	public double getLeaseFee() {
		return leaseFee;
	}
 
	public LocalDate getStartLeaseDate() {
		return startLeaseDate;
	}
 
	public LocalDate getEndLeaseDate() {
		return endLeaseDate;
	}
 
	// Calculate the total tax of the Leased item.
	@Override
	public double getTaxes() {
		double taxes = 0.0;
		double fee = getSubTotal();
 
		if (fee <= 10000.0) {
			taxes += 0;
 
		} else if (fee > 10000.0 && fee < 100000.0) {
			taxes += 500.0;
 
		} else {
			taxes += 1500.0;
		}
		return taxes;
	}
 
	// Calculate total number of days that the item have been leased for.
	public int getDaysBetween() {
		return (int) ChronoUnit.DAYS.between(getStartLeaseDate(), getEndLeaseDate()) + 1;
	}
 
	// Calculate price of the leased item before the tax.
	@Override
	public double getSubTotal() {
		return (Math.round((((this.getDaysBetween()) / 30.0) * leaseFee) * 100.0) / 100.0);
	}
 
	// Calculate total price of the leased item.
	public double getGrandTotal() {
		return (Math.round(getTaxes() + getSubTotal()) * 100.0) / 100.0;
 
	}
 
	@Override
	public String ItemInfoToString() {
		return (String.format("\n%s (Lease) %s-%s\n %d (%s->%s) @ $%.2f / 30 days \n\t\t\t\t\t\t\t\t\t      $ %6.2f",
				this.getItemCode(), this.getItemName(), this.getModel(), this.getDaysBetween(),
				this.getStartLeaseDate(), this.getEndLeaseDate(), this.getLeaseFee(), this.getSubTotal()));
	}
}