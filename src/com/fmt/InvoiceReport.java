package com.fmt;
 
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
 
/*
 * @ author: Sandesh Subedi
 *           Darius Banks
 * @ Date: 2023-05-05
 * 
 * This class reports summary by total, sales made in the specific store, and list of
 * invoices with details i.e items, cost, quantity, unique code that represents invoices.
 * It reports the invoice sorted by customer Lastname/Firstname, invoice total and store code.
 */
public class InvoiceReport {
 
	// Reports details that is in the invoice.
	public static void summaryReport(MyLinkedList<Invoice> invoices) {
		HashMap<Integer, Store> getStore = DatabaseLoader.dataStoreLoader();
		HashMap<Integer, Invoice> invoice = DatabaseLoader.dataInvoiceLoader(getStore);
		DatabaseLoader.dataInvoiceItemLoader(invoice);
 
		int totalItems = 0;
		double Tax = 0.0;
		double totalGrandTotal = 0.0;
 
		System.out
				.println("+----------------------------------------------------------------------------------------+");
		System.out
				.println("|                  Summary Report - By Total                                             |");
		System.out
				.println("+----------------------------------------------------------------------------------------+");
		System.out.printf("%-10s %-10s %-20s %-10s      %-10s       %-11s  \n", " Invoice#", "  Store", "  Customer",
				"  Num Items", "Tax", "Total");
 
		for (Invoice inv : invoices) {
			Tax += inv.getTaxes();
			totalGrandTotal += inv.totalGrandTotal();
			totalItems += inv.getInvoiceItem().size();
 
			System.out.printf(" %-10s  %-10s %-20s %-10d $   %-10.2f   $   %-12.2f \n", inv.getInvoiceCode(),
					inv.getStore().getStoreCode(), inv.getCustomer().getName(), inv.getInvoiceItem().size(),
					inv.getTaxes(), inv.totalGrandTotal());
 
		}
		System.out
				.println("+----------------------------------------------------------------------------------------+");
		System.out.printf("%46d          $  %7.2f       $ %11.2f\n\n\n", totalItems, Tax, totalGrandTotal);
	}
 
	/**
	 * Reports number of sales and total money the store made also with the detail
	 * of manager.
	 */
	public static void storeSummaryReport() {
		HashMap<Integer, Store> getStore = DatabaseLoader.dataStoreLoader();
		HashMap<Integer, Invoice> getInvoice = DatabaseLoader.dataInvoiceLoader(getStore);
		DatabaseLoader.dataInvoiceItemLoader(getInvoice);
 
		List<Store> listStore = new ArrayList<>();
		for (int s : getStore.keySet()) {
			listStore.add(getStore.get(s));
		}
		Collections.sort(listStore);
		int numOfSales = 0;
		double totalGrandTotal = 0.0;
 
		System.out.println("+--------------------------------------------------------------------+");
		System.out.println("|                  Store Sales Summary Report                        |");
		System.out.println("+--------------------------------------------------------------------+");
		System.out.printf("%-11s %-20s %-20s %-20s\n", " Store#", "  Manager", "   # Sales", "Grand Total      ");
 
		for (Store str : listStore) {
			numOfSales += str.getInvoices().size();
			totalGrandTotal += str.totalGrandTotal();
 
			System.out.printf(" %-10s   %-18s %6s           $ %13.2f \n", str.getStoreCode(),
					str.getManager().getName(), str.getInvoices().size(), str.totalGrandTotal());
		}
		System.out.println("+--------------------------------------------------------------------+");
		System.out.printf("%39d           $%14.2f \n\n\n", numOfSales, totalGrandTotal);
	}
 
	/*
	 * Reports the sale storing data into the linked list and in a sorted manner.
	 */
	public static void salesReport(MyLinkedList<Invoice> invoices, String type) {
		StringBuilder sb = new StringBuilder();
		// Loads the data
		HashMap<Integer, Store> getStore = DatabaseLoader.dataStoreLoader();
		HashMap<Integer, Invoice> invoice = DatabaseLoader.dataInvoiceLoader(getStore);
		DatabaseLoader.dataInvoiceItemLoader(invoice);
 
		sb.append("+-------------------------------------------------------------------------------+\n");
		sb.append("| Sales By " + type + "		                                                |\n");
		sb.append("+-------------------------------------------------------------------------------+\n");
		sb.append(String.format("%-10s %-10s %-22s %-13s            %-10s  \n", " Sale", "  Store", "  Customer",
				"SalesPerson", "Total"));
 
		for (Invoice inv : invoices) {
			sb.append(String.format(" %-10s  %-10s %-20s %-20s $   %-11.2f  \n", inv.getInvoiceCode(),
					inv.getStore().getStoreCode(), inv.getCustomer().getName(), inv.getSalesPerson().getName(),
					inv.totalGrandTotal()));
		}
		System.out.println(sb);
	}
 
	public static void main(String[] args) {
 
		HashMap<Integer, Store> getStore = DatabaseLoader.dataStoreLoader();
		HashMap<Integer, Invoice> invoice = DatabaseLoader.dataInvoiceLoader(getStore);
		DatabaseLoader.dataInvoiceItemLoader(invoice);
 
		/*
		 * Comparator that helps in sorting the invoice on the basis of customer last
		 * name/ firstName.
		 */
		Comparator<Invoice> cmpByCustomer = new Comparator<Invoice>() {
			public int compare(Invoice i1, Invoice i2) {
				// comparing i1's customer lastName i2's customer lastName.
				int result = i1.getCustomer().getLastName().compareTo(i2.getCustomer().getLastName());
				if (result == 0) {
					// comparing i1's customer FirstName i2's customer FirstName if the LastName
					// matches.
					result = i1.getCustomer().getFirstName().compareTo(i2.getCustomer().getFirstName());
				}
				return result;
			}
		};
 
		// Passing the sorted data into the linkedList we created.
		MyLinkedList<Invoice> salesByCustomer = new MyLinkedList<Invoice>(cmpByCustomer);
		for (Integer i : invoice.keySet()) {
			salesByCustomer.addElement(invoice.get(i));
		}
 
		/*
		 * Comparator that helps in sorting the invoice on the basis of invoice total
		 * amount
		 */
		Comparator<Invoice> cmpByTotal = new Comparator<Invoice>() {
			public int compare(Invoice i1, Invoice i2) {
				// comparing i1's grand total to i2's
				if (i1.totalGrandTotal() < i2.totalGrandTotal()) {
					return 1;
				} else if (i1.totalGrandTotal() > i2.totalGrandTotal()) {
					return -1;
				} else {
					return 0;
				}
			}
		};
 
		// Passing the sorted data into the linkedList we created.
		MyLinkedList<Invoice> salesByTotal = new MyLinkedList<Invoice>(cmpByTotal);
		for (Integer i : invoice.keySet()) {
			salesByTotal.addElement(invoice.get(i));
		}
 
		/**
		 * Comparator that helps in sorting the invoice on the basis of Store code of
		 * that invoice.
		 */
		Comparator<Invoice> cmpByStore = new Comparator<Invoice>() {
			public int compare(Invoice i1, Invoice i2) {
				// comparing i1's StoreCode i2's customer StoreCode.
				int result = i1.getStore().getStoreCode().compareTo(i2.getStore().getStoreCode());
				if (result == 0) {
					// comparing i1's customer lastName i2's customer lastName.
					result = i1.getSalesPerson().getLastName().compareTo(i2.getSalesPerson().getLastName());
					if (result == 0) {
						// comparing i1's customer FirstNAme i2's customer FirstName.
						result = i1.getSalesPerson().getFirstName().compareTo(i2.getSalesPerson().getFirstName());
					}
				}
				return result;
			}
		};
 
		// Passing the sorted data into the linkedList we created.
		MyLinkedList<Invoice> salesByStore = new MyLinkedList<Invoice>(cmpByStore);
		for (Integer i : invoice.keySet()) {
			salesByStore.addElement(invoice.get(i));
		}
 
		InvoiceReport.salesReport(salesByCustomer, "Customer");
		InvoiceReport.salesReport(salesByTotal, "Total");
		InvoiceReport.salesReport(salesByStore, "Store");
	}
}