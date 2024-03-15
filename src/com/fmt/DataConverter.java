package com.fmt;
 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
 
/**
 *
 * @author Sandeshsubedi
 * @author Darius Banks Date: 2023-02-24 Assignment 2 - Project Phase I
 *
 *         This is a program which loads data from different csv file and
 *         iterate through each line on the file and distinguishes them
 *         according to their types. It consists of parser method which load the
 *         data from different csv file and output the result accordingly. It
 *         also has the main method in it which executes the whole program
 */
 
public class DataConverter {
 
	// Pass the Person data
	public static HashMap<String, Person> dataPersonsParser() {
 
		HashMap<String, Person> personResult = new HashMap<>();
		File personFile = new File("data/Persons.csv");
		try {
			Scanner s = new Scanner(personFile);
			String line = s.nextLine();
			int personRecords = Integer.parseInt(line);
			Person p = null;
 
			for (int i = 0; i < personRecords; i++) {
				line = s.nextLine();
				String[] data = line.split(",");
				List<String> emails = new ArrayList<>();
				String person = data[0];
				String lastName = data[1];
				String firstName = data[2];
 
				String street = data[3];
				String city = data[4];
				String state = data[5];
				String zip = data[6];
				String country = data[7];
 
				Address address = new Address(street, city, state, zip, country);
				// Creating a person
 
				for (int y = 8; y < data.length; y++) {
					emails.add(data[y]); // add an email to person's email list
				}
				p = new Person(person, lastName, firstName, address, emails);
				personResult.put(person, p);
			}
			s.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return personResult;
	}
 
	// Pass the Store data
	public static HashMap<String, Store> dataStoresParser(HashMap<String, Person> person) {
		HashMap<String, Store> storeResult = new HashMap<String, Store>();
 
		File storeFile = new File("data/Stores.csv");
		try {
			Scanner s = new Scanner(storeFile);
			String line = s.nextLine();
			int storeRecords = Integer.parseInt(line);
			// dataInvoicesParser();
			for (int a = 0; a < storeRecords; a++) {
				line = s.nextLine();
				String[] data = line.split(",");
				String storeCode = data[0];
				String managerCode = data[1];
 
				String street = data[2];
				String city = data[3];
				String state = data[4];
				String zip = data[5];
				String country = data[6];
 
				Person manager = null;
 
				for (Person k : person.values()) {
					if (k.getPersonCode().equals(managerCode)) {
						manager = k;
					}
				}
				Address address = new Address(street, city, state, zip, country);
				Store str = new Store(storeCode, manager, address);
 
				storeResult.put(storeCode, str);
			}
			s.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		return storeResult;
	}
 
	// Pass the item data
	public static HashMap<String, Item> dataItemsParser() {
 
		HashMap<String, Item> itemResult = new HashMap<>();
		File itemFile = new File("data/Items.csv");
		try {
			Scanner s = new Scanner(itemFile);
			Item e = null;
 
			String line = s.nextLine();
			int itemRecords = Integer.parseInt(line);
 
			for (int k = 0; k < itemRecords; k++) {
				line = s.nextLine();
				String[] data = line.split(",");
 
				String itemCode = data[0];
				String itemType = data[1];
				String itemName = data[2];
				// Checking the types of the given data
 
				if (itemType.equals("E")) {
					String model = data[3];
					e = new Equipment(itemCode, itemName, model);
 
				} else if (itemType.equals("P")) {
					String unit = data[3];
					double unitPrice = Double.parseDouble(data[4]);
					e = new Product(itemCode, itemName, unit, unitPrice);
 
				} else if (itemType.equals("S")) {
					double hourlyRate = Double.parseDouble(data[3]);
					e = new Service(itemCode, itemName, hourlyRate);
 
				}
				itemResult.put(itemCode, e);
			}
			s.close();
		} catch (IOException e) {
		}
		return itemResult;
	}
 
	public static HashMap<String, Invoice> dataInvoicesParser(HashMap<String, Store> store) {
		HashMap<String, Person> customer = DataConverter.dataPersonsParser();
		HashMap<String, Person> salesPerson = DataConverter.dataPersonsParser();
 
		Invoice invoice = null;
 
		HashMap<String, Invoice> invoiceResult = new HashMap<>();
		File invoiceFile = new File("data/Invoices.csv");
		try {
			Scanner s = new Scanner(invoiceFile);
			s.nextLine();
			while (s.hasNextLine()) {
				String line = s.nextLine();
				String[] data = line.split(",");
				String invoiceCode = data[0];
 
				Store storeCode = store.get(data[1]);
				Person customerCode = customer.get(data[2]);
				Person salesPersonCode = salesPerson.get(data[3]);
				LocalDate invoiceDate = LocalDate.parse(data[4]);
 
				invoice = new Invoice(invoiceCode, storeCode, customerCode, salesPersonCode, invoiceDate);
 
				invoiceResult.put(invoiceCode, invoice);
				storeCode.addInvoice(invoice);
 
			}
			s.close();
		} catch (IOException e) {
		}
		return invoiceResult;
 
	}
 
	public static List<Item> dataInvoiceItemsParser(HashMap<String, Invoice> invoice) {
 
		HashMap<String, Item> itemResult = DataConverter.dataItemsParser();
		List<Item> invoiceItemResult = new ArrayList<>();
 
		File invoiceItemFile = new File("data/InvoiceItems.csv");
		try (Scanner s = new Scanner(invoiceItemFile)) {
 
			String line = s.nextLine();
			int invoiceItemRecord = Integer.parseInt(line);
 
			for (int i = 0; i < invoiceItemRecord; i++) {
				String[] data = s.nextLine().split(",");
				Invoice invoiceCode = invoice.get(data[0]);
				// String itemCode = data[1];
 
				Item item = itemResult.get(data[1]);
 
				if (item instanceof Equipment) {
 
					String type = data[2];
 
					if (type.equals("P")) {
						double purchasePrice = Double.parseDouble(data[3]);
						item = new Purchase((Equipment) item, purchasePrice);
					} else {
						double leaseFee = Double.parseDouble(data[3]);
						LocalDate startLeaseDate = LocalDate.parse(data[4]);
						LocalDate endLeaseDate = LocalDate.parse(data[5]);
						item = new Lease((Equipment) item, startLeaseDate, endLeaseDate, leaseFee);
					}
				} else if (item instanceof Product) {
					double quantity = Double.parseDouble(data[2]);
					item = new Product((Product) item, quantity);
 
				} else {
					double serviceHours = Double.parseDouble(data[2]);
					item = new Service((Service) item, serviceHours);
				}
				invoiceItemResult.add(item);
				invoiceCode.addInvoiceItem(item);
			}
			s.close();
		} catch (IOException e) {
		}
		return invoiceItemResult;
	}
}