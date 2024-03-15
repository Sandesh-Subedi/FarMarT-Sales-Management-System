package com.fmt;

import java.util.ArrayList;
import java.util.List;

//This is a store class which holds different data of the store
public class Store implements Comparable<Store> {
	private String storeCode;
	private Person manager;
	private Address address;
	private List<Invoice> invoices;

	public Store(String storeCode, Person manager, Address address) {
		this.storeCode = storeCode;
		this.address = address;
		this.manager = manager;
		this.invoices = new ArrayList<Invoice>();
	}

// get methods for the initiated variables
	public String getStoreCode() {
		return storeCode;
	}

	public Person getManager() {
		return manager;
	}

	public Address getAddress() {
		return address;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void addInvoice(Invoice inv) {
		this.invoices.add(inv);
	}

	// Calculates total tax from the sales made at that store.
	public double getTaxes() {
		double tax = 0.0;
		for (Invoice invoice : invoices) {
			tax += invoice.getTaxes();
		}
		return tax;
	}

	// Calculates the total money the store made without the taxes.
	public double totalSubTotal() {
		double subTotal = 0.0;
		for (Invoice invoice : invoices) {
			subTotal += invoice.totalSubTotal();
		}
		return subTotal;
	}

	// Calculates total money the store made with the taxes.
	public double totalGrandTotal() {
		double totalGrandTotal = 0.0;
		for (Invoice invoice : invoices) {
			totalGrandTotal += invoice.totalSubTotal() + invoice.getTaxes();
		}

		return totalGrandTotal;
	}

	@Override
	public String toString() {
		return "Store [storeCode=" + storeCode + ", manager=" + manager + ", address=" + address + "]";
	}

	// Comparator to sort.
	@Override
	public int compareTo(Store that) {
		int compareNum = this.getManager().getLastName().compareToIgnoreCase(that.getManager().getLastName());
		if (compareNum == 0) {
			compareNum = this.getManager().getFirstName().compareToIgnoreCase(that.getManager().getFirstName());
		}
		if (compareNum == 0) {
			compareNum = (int) (that.totalSubTotal() - this.totalSubTotal());
		}
		return compareNum;
	}
}