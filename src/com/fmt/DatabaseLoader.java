package com.fmt;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
 
/*
 * This class loads the data from database and stores the data into respective hashMaps or List and 
 * gives the detail from respective tables required for reports execution.
 */
public class DatabaseLoader {
 
	private static final Logger LOGGER = LogManager.getLogger(ConnectionFactory.class);
 
	// Loads Person
	public static HashMap<Integer, Person> dataPersonLoader(HashMap<Integer, Address> addressMap) {
		HashMap<Integer, Person> personMap = new HashMap<>();
 
		String personQuery = "select addressId, personId, personCode, firstName, lastName from Person";
		try {
			Connection conn = ConnectionFactory.Connection();
			PreparedStatement personStatement = conn.prepareStatement(personQuery);
			ResultSet rs = personStatement.executeQuery();
 
			while (rs.next()) {
				int personId = rs.getInt("personId");
				String personCode = rs.getString("personCode");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				int addressId = rs.getInt("addressId");
				Address address = addressMap.get(addressId);
				Person person = new Person(personCode, firstName, lastName, address, dataEmailLoader(personId));
				personMap.put(personId, person);
			}
			rs.close();
			personStatement.close();
			ConnectionFactory.closeConnection(conn);
 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		LOGGER.info("Done Loading all the person ...............");
		return personMap;
	}
 
	// Loads list of emails.
	public static List<String> dataEmailLoader(int personId) {
		List<String> emailAddresses = new ArrayList<>();
 
		String emailQuery = "select emailAddress from Email where personId = ?";
		try {
			Connection conn = ConnectionFactory.Connection();
			PreparedStatement emailStatement = conn.prepareStatement(emailQuery);
			emailStatement.setInt(1, personId);
			ResultSet rs = emailStatement.executeQuery();
 
			while (rs.next()) {
				String emailAddress = rs.getString("emailAddress");
				emailAddresses.add(emailAddress);
			}
			rs.close();
			emailStatement.close();
			ConnectionFactory.closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emailAddresses;
	}
 
	// Loads Address.
	public static HashMap<Integer, Address> dataAddressLoader() {
		HashMap<Integer, Address> addressMap = new HashMap<>();
 
		String addressQuery = "select addressId, street, city, state, zipCode, country from Address";
		try {
			Connection conn = ConnectionFactory.Connection();
			PreparedStatement addressStatement = conn.prepareStatement(addressQuery);
			ResultSet rs = addressStatement.executeQuery();
 
			while (rs.next()) {
				String street = rs.getString("street");
				String city = rs.getString("city");
				String zipCode = rs.getString("zipCode");
				String state = rs.getString("state");
				String country = rs.getString("country");
				int addressId = rs.getInt("addressId");
				Address address = new Address(street, city, state, zipCode, country);
				addressMap.put(addressId, address);
			}
			rs.close();
			addressStatement.close();
			ConnectionFactory.closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		LOGGER.info("Done Loading all the Address ...............");
		return addressMap;
	}
 
	// Loads Item.
	public static HashMap<Integer, Item> dataItemLoader() {
		HashMap<Integer, Item> itemMap = new HashMap<>();
 
		String itemQuery = "select itemId, itemType, itemCode, itemName, itemModel, unit, unitPrice, hourlyRate from Item";
		try {
			Connection conn = ConnectionFactory.Connection();
			PreparedStatement itemStatement = conn.prepareStatement(itemQuery);
			ResultSet rs = itemStatement.executeQuery();
			Item item = null;
 
			while (rs.next()) {
				int itemId = rs.getInt("itemId");
				String itemCode = rs.getString("itemCode");
				String itemType = rs.getString("itemType");
				String itemName = rs.getString("itemname");
				String itemModel = rs.getString("itemModel");
				String unit = rs.getString("unit");
				float unitPrice = rs.getFloat("unitPrice");
				float hourlyRate = rs.getFloat("hourlyRate");
 
				item = itemMap.get(itemId);
				if (itemType.equals("E")) {
					item = new Equipment(itemCode, itemName, itemModel);
				} else if (itemType.equals("P")) {
					item = new Product(itemCode, itemName, unit, unitPrice);
				} else if (itemType.equals("S")) {
					item = new Service(itemCode, itemName, hourlyRate);
				}
				itemMap.put(itemId, item);
			}
			rs.close();
			itemStatement.close();
			ConnectionFactory.closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		LOGGER.info("Done Loading all the Item ...............");
		return itemMap;
	}
 
	// Loads Store.
	public static HashMap<Integer, Store> dataStoreLoader() {
		HashMap<Integer, Store> storeMap = new HashMap<>();
		HashMap<Integer, Address> addressMap = dataAddressLoader();
		HashMap<Integer, Person> personMap = dataPersonLoader(addressMap);
 
		String storeQuery = "select storeId, storeCode, managerId, addressId from Store";
		try {
			Connection conn = ConnectionFactory.Connection();
			PreparedStatement storeStatement = conn.prepareStatement(storeQuery);
			ResultSet rs = storeStatement.executeQuery();
 
			while (rs.next()) {
				int storeId = rs.getInt("storeId");
				String storeCode = rs.getString("storeCode");
				int managerId = rs.getInt("managerId");
				int addressId = rs.getInt("addressId");
				Address address = addressMap.get(addressId);
				Store store = new Store(storeCode, personMap.get(managerId), address);
				storeMap.put(storeId, store);
			}
			rs.close();
			storeStatement.close();
			ConnectionFactory.closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		LOGGER.info("Done Loading all the Store ...............");
		return storeMap;
	}
 
	// Loads Invoice
	public static HashMap<Integer, Invoice> dataInvoiceLoader(HashMap<Integer, Store> storeMap) {
		HashMap<Integer, Invoice> invoiceMap = new HashMap<>();
		HashMap<Integer, Address> addressMap = dataAddressLoader();
		HashMap<Integer, Person> personMap = dataPersonLoader(addressMap);
 
		String invoiceQuery = "select * from Invoice";
		try {
			Connection conn = ConnectionFactory.Connection();
			PreparedStatement invoiceStatement = conn.prepareStatement(invoiceQuery);
			ResultSet rs = invoiceStatement.executeQuery();
 
			while (rs.next()) {
				int invoiceId = rs.getInt("invoiceId");
				String invoiceCode = rs.getString("invoiceCode");
				Store store = storeMap.get(rs.getInt("storeId"));
				Person customer = personMap.get(rs.getInt("customerId"));
				Person salesPerson = personMap.get(rs.getInt("salesPersonId"));
				LocalDate invoiceDate = LocalDate.parse(rs.getString("invoiceDate"));
				Invoice invoice = new Invoice(invoiceCode, store, customer, salesPerson, invoiceDate);
				invoiceMap.put(invoiceId, invoice);
				store.addInvoice(invoice);
			}
			rs.close();
			invoiceStatement.close();
			ConnectionFactory.closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		LOGGER.info("Done Loading all the Invoice ...............");
 
		return invoiceMap;
	}
 
	// Loads InvoiceItem
	public static void dataInvoiceItemLoader(HashMap<Integer, Invoice> invoice) {
		HashMap<Integer, Item> itemMap = dataItemLoader();
 
		String invoiceQuery = "select * from InvoiceItem";
		try {
			Connection conn = ConnectionFactory.Connection();
			PreparedStatement invoiceItemStatement = conn.prepareStatement(invoiceQuery);
			ResultSet rs = invoiceItemStatement.executeQuery();
			Item item = null;
 
			while (rs.next()) {
				int invoiceId = rs.getInt("invoiceId");
				int itemId = rs.getInt("itemId");
				float purchasePrice = rs.getFloat("purchasePrice");
				float serviceHours = rs.getFloat("hoursBilled");
				float leaseFee = rs.getFloat("leaseFee");
				float quantityPurchase = rs.getFloat("quantityPurchase");
				String equipmentType = rs.getString("equipmentType");
				item = itemMap.get(itemId);
 
				if (item instanceof Equipment) {
					if (equipmentType.equals("P")) {
						item = new Purchase((Equipment) item, purchasePrice);
					} else if (equipmentType.equals("L")) {
						LocalDate startLeaseDate = LocalDate.parse(rs.getString("startLeaseDate"));
						LocalDate endLeaseDate = LocalDate.parse(rs.getString("endLeasedate"));
						item = new Lease((Equipment) item, startLeaseDate, endLeaseDate, leaseFee);
					}
				} else if (item instanceof Product) {
					item = new Product((Product) item, quantityPurchase);
				} else {
					item = new Service((Service) item, serviceHours);
				}
 
				invoice.get(invoiceId).addInvoiceItem(item);
			}
			rs.close();
			invoiceItemStatement.close();
			ConnectionFactory.closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		LOGGER.info("Done Loading all the InvoiceItem ..........");
	}
}