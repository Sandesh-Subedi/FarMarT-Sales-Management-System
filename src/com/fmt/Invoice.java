package com.fmt;
 
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
 
/*
 * This class represents the details of the invoice and performs some math like 
 * calculating the total taxes and the amounts of the invoice..
 */
public class Invoice implements Comparable<Invoice> {
	private String invoiceCode;
	private Store store;
	private Person customer;
	private Person salesPerson;
	private LocalDate invoiceDate;
	private List<Item> invoiceItem;
 
	public Invoice(String invoiceCode, Store store, Person customer, Person salesPerson, LocalDate invoiceDate) {
		this.invoiceCode = invoiceCode;
		this.store = store;
		this.customer = customer;
		this.salesPerson = salesPerson;
		this.invoiceDate = invoiceDate;
		this.invoiceItem = new ArrayList<>();
	}
 
	public String getInvoiceCode() {
		return invoiceCode;
	}
 
	public Store getStore() {
		return store;
	}
 
	public Person getSalesPerson() {
		return salesPerson;
	}
 
	public Person getCustomer() {
		return customer;
	}
 
	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}
 
	public List<Item> getInvoiceItem() {
		return invoiceItem;
	}
 
	// Getting the tax in the invoice.
	public double getTaxes() {
		double tax = 0.0;
		for (Item item : invoiceItem) {
			tax += item.getTaxes();
		}
		return tax;
	}
 
	// Getting the cost of the items before the tax.
	public double totalSubTotal() {
		double subTotal = 0.0;
		for (Item item : invoiceItem) {
			subTotal += item.getSubTotal();
		}
		return subTotal;
	}
 
	// Getting the total cost of that item in the invoice.
	public double totalGrandTotal() {
		return getTaxes() + totalSubTotal();
	}
 
	public void addInvoiceItem(Item item) {
		invoiceItem.add(item);
	}
 
	@Override
	public String toString() {
 
		StringBuilder sb = new StringBuilder();
 
		sb.append("\nInvoice # " + this.getInvoiceCode().toString() + "\n");
		sb.append("Store # " + this.store.getStoreCode() + "\n");
		sb.append("Date: " + this.invoiceDate + "\n");
		sb.append("Customer:\n");
		sb.append(this.getCustomer().toString() + "\n");
		sb.append("\nSales Person:\n");
		sb.append(this.getSalesPerson().toString() + "\n");
		sb.append(("Item                                                                              Total\n"));
		sb.append("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-                                          =-=-=-=-=-=-=-=-");
 
		for (Item i : this.getInvoiceItem()) {
			sb.append(i.ItemInfoToString());
		}
		sb.append("\n\n                                                                       =-=-=-=-=-=-=-=-\n");
		sb.append(String.format("\n%70s %15.2f\n", "   SubTotal $", totalSubTotal()));
		sb.append(String.format("%70s %15.2f\n", "          Tax $", getTaxes()));
		sb.append(String.format("%70s %15.2f\n", "  Grand Total $", totalSubTotal() + getTaxes()));
 
		return sb.toString();
	}
 
	// Compare method to get sorted order.
	@Override
	public int compareTo(Invoice that) {
		int numCompare = (int) (that.totalSubTotal() - this.totalSubTotal());
		return numCompare;
	}	
}