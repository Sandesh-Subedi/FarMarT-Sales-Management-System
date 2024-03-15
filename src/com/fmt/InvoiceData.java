package com.fmt;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
public class InvoiceData {
 
	/**
	 * Removes all records from all tables in the database to avoid violating foreign key constraints.
	 */
	public static void clearDatabase() {
 
		Connection conn = ConnectionFactory.Connection();
		PreparedStatement ps = null;
		String query = null;
 
		try {
			query = "delete from InvoiceItem;";
			ps = conn.prepareStatement(query);
			ps.executeUpdate(query);
			ps.close();
 
			query = "delete from Item;";
			ps = conn.prepareStatement(query);
			ps.executeUpdate(query);
			ps.close();
 
			query = "delete from Invoice;";
			ps = conn.prepareStatement(query);
			ps.executeUpdate(query);
			ps.close();
 
			query = "delete from Store;";
			ps = conn.prepareStatement(query);
			ps.executeUpdate(query);
			ps.close();
 
			query = "delete from Email;";
			ps = conn.prepareStatement(query);
			ps.executeUpdate(query);
			ps.close();
 
			query = "delete from Person;";
			ps = conn.prepareStatement(query);
			ps.executeUpdate(query);
			ps.close();
 
			query = "delete from Address;";
			ps = conn.prepareStatement(query);
			ps.executeUpdate(query);
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
 
	/**
	 * Method to add a person record to the database with the provided data.
	 *
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city,
			String state, String zip, String country) {
		int addressId = InvoiceDataUtil.getAddress(street, city, state, zip, country);
		String personInsertQuery = "insert into Person(personCode, lastName, firstName, addressId) values (?, ?, ?, ?)";
 
		try {
			Connection conn = ConnectionFactory.Connection();
			PreparedStatement personInsertStatement = conn.prepareStatement(personInsertQuery,
					Statement.RETURN_GENERATED_KEYS);
			personInsertStatement.setString(1, personCode);
			personInsertStatement.setString(2, firstName);
			personInsertStatement.setString(3, lastName);
			personInsertStatement.setInt(4, addressId);
			personInsertStatement.executeUpdate();
			personInsertStatement.close();
			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1);
		}
	}
 
	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 *
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		int personId = InvoiceDataUtil.getPersonId(personCode);
 
		try {
			Connection conn = ConnectionFactory.Connection();
			String emailInsertQuery = "Insert into Email(emailAddress, personId) values (?,?)";
			PreparedStatement ps = conn.prepareStatement(emailInsertQuery, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, email);
			ps.setInt(2, personId);
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1);
		}
	}
 
	/**
	 * Adds a store record to the database managed by the person identified by the
	 * given code.
	 *
	 * @param storeCode
	 * @param managerCode
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addStore(String storeCode, String managerCode, String street, String city, String state,
			String zip, String country) {
		int personId = InvoiceDataUtil.getPersonId(managerCode);
		int addressId = InvoiceDataUtil.getAddress(street, city, state, zip, country);
 
		try {
			Connection conn = ConnectionFactory.Connection();
			String storeInsertQuery = "insert into Store(storeCode, managerId, addressId) values (?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(storeInsertQuery, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, storeCode);
			ps.setInt(2, personId);
			ps.setInt(3, addressId);
			ps.executeUpdate();
			ps.close();
			conn.close();
 
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1);
		}
	}
 
	/**
	 * Adds a product record to the database with the given <code>code</code>,
	 * <code>name</code> and <code>unit</code> and <code>pricePerUnit</code>.
	 *
	 * @param itemCode
	 * @param name
	 * @param unit
	 * @param pricePerUnit
	 */
	public static void addProduct(String code, String name, String unit, double pricePerUnit) {
		String prodcutInsertQuery = "insert into Item(itemCode, itemType, itemName, unit, unitPrice) values (?, ?, ?, ?, ?)";
 
		try {
			Connection conn = ConnectionFactory.Connection();
			PreparedStatement productInsertStatement = conn.prepareStatement(prodcutInsertQuery,
					Statement.RETURN_GENERATED_KEYS);
			productInsertStatement.setString(1, code);
			productInsertStatement.setString(2, "P");
			productInsertStatement.setString(3, name);
			productInsertStatement.setString(4, unit);
			productInsertStatement.setDouble(5, pricePerUnit);
 
			productInsertStatement.executeUpdate();
			productInsertStatement.close();
			conn.close();
 
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1);
		}
	}
 
	/**
	 * Adds an equipment record to the database with the given <code>code</code>,
	 * <code>name</code> and <code>modelNumber</code>.
	 *
	 * @param itemCode
	 * @param name
	 * @param modelNumber
	 */
	public static void addEquipment(String code, String name, String modelNumber) {
		String equipmentInsertQuery = "insert into Item(itemCode, itemType, itemName, itemModel) values (?, ?, ?, ?)";
 
		try {
			Connection conn = ConnectionFactory.Connection();
			PreparedStatement eqiupmentInsertStatement = conn.prepareStatement(equipmentInsertQuery,
					Statement.RETURN_GENERATED_KEYS);
			eqiupmentInsertStatement.setString(1, code);
			eqiupmentInsertStatement.setString(2, "E");
			eqiupmentInsertStatement.setString(3, name);
			eqiupmentInsertStatement.setString(4, modelNumber);
			eqiupmentInsertStatement.executeUpdate();
			eqiupmentInsertStatement.close();
			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1);
		}
	}
 
	/**
	 * Adds a service record to the database with the given <code>code</code>,
	 * <code>name</code> and <code>costPerHour</code>.
	 *
	 * @param itemCode
	 * @param name
	 * @param modelNumber
	 */
	public static void addService(String code, String name, double costPerHour) {
		String serviceInsertQuery = "insert into Item(itemCode, itemType, itemName, hourlyRate) values (?, ?, ?, ?)";
 
		try {
			Connection conn = ConnectionFactory.Connection();
			PreparedStatement serviceInsertStatement = conn.prepareStatement(serviceInsertQuery,
					Statement.RETURN_GENERATED_KEYS);
			serviceInsertStatement.setString(1, code);
			serviceInsertStatement.setString(2, "S");
			serviceInsertStatement.setString(3, name);
			serviceInsertStatement.setDouble(4, costPerHour);
			serviceInsertStatement.executeUpdate();
			serviceInsertStatement.close();
			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1);
		}
	}
 
	/**
	 * Adds an invoice record to the database with the given data.
	 *
	 * @param invoiceCode
	 * @param storeCode
	 * @param customerCode
	 * @param salesPersonCode
	 * @param invoiceDate
	 */
	public static void addInvoice(String invoiceCode, String storeCode, String customerCode, String salesPersonCode,
			String invoiceDate) {
		String invoiceInsertQuery = "insert into Invoice(invoiceCode, storeId, customerId, salesPersonId, invoiceDate) values (?, ?, ?, ?, ?)";
		int customerId = InvoiceDataUtil.getPersonId(customerCode);
		int salesPersonId = InvoiceDataUtil.getPersonId(salesPersonCode);
		int storeId = InvoiceDataUtil.getStoreId(storeCode);
 
		try {
			Connection conn = ConnectionFactory.Connection();
			PreparedStatement invoiceInsertStatement = conn.prepareStatement(invoiceInsertQuery,
					Statement.RETURN_GENERATED_KEYS);
			invoiceInsertStatement.setString(1, invoiceCode);
			invoiceInsertStatement.setInt(2, storeId);
			invoiceInsertStatement.setInt(3, customerId);
			invoiceInsertStatement.setInt(4, salesPersonId);
			invoiceInsertStatement.setString(5, invoiceDate);
			invoiceInsertStatement.executeUpdate();
			ResultSet invoiceResult = invoiceInsertStatement.getGeneratedKeys();
			invoiceResult.close();
			invoiceInsertStatement.close();
			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1);
		}
	}
 
	/**
	 * Adds a particular product (identified by <code>itemCode</code>) to a
	 * particular invoice (identified by <code>invoiceCode</code>) with the
	 * specified quantity.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param quantity
	 */
	public static void addProductToInvoice(String invoiceCode, String itemCode, int quantity) {
		int invoiceId = InvoiceDataUtil.getInvoiceId(invoiceCode);
		int itemId = InvoiceDataUtil.getItemId(itemCode);
 
		try {
			Connection conn = ConnectionFactory.Connection();
			String productInsertQuery = "insert into InvoiceItem(invoiceId, itemId, quantityPurchase, equipmentType) values (?, ?, ?, ?)";
			PreparedStatement productInsertStatement = conn.prepareStatement(productInsertQuery,
					Statement.RETURN_GENERATED_KEYS);
			productInsertStatement.setInt(1, invoiceId);
			productInsertStatement.setInt(2, itemId);
			productInsertStatement.setInt(3, quantity);
			productInsertStatement.setString(4, null);
			productInsertStatement.executeUpdate();
			productInsertStatement.close();
			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1);
		}
	}
 
	/**
	 * Adds a particular equipment <i>purchase</i> (identified by
	 * <code>itemCode</code>) to a particular invoice (identified by
	 * <code>invoiceCode</code>) at the given <code>purchasePrice</code>.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param purchasePrice
	 */
	public static void addEquipmentToInvoice(String invoiceCode, String itemCode, double purchasePrice) {
		int invoiceId = InvoiceDataUtil.getInvoiceId(invoiceCode);
		int itemId = InvoiceDataUtil.getItemId(itemCode);
 
		try {
			Connection conn = ConnectionFactory.Connection();
			String equipmentInsertQuery = "insert into InvoiceItem (invoiceId, itemId, purchasePrice, equipmentType) values (?, ?, ?, ?)";
			PreparedStatement equipmentInsertStatement = conn.prepareStatement(equipmentInsertQuery,
					Statement.RETURN_GENERATED_KEYS);
			equipmentInsertStatement.setInt(1, invoiceId);
			equipmentInsertStatement.setInt(2, itemId);
			equipmentInsertStatement.setDouble(3, purchasePrice);
			equipmentInsertStatement.setString(4, "P");
			equipmentInsertStatement.executeUpdate();
			equipmentInsertStatement.close();
			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1);
		}
	}
 
	/**
	 * Adds a particular equipment <i>lease</i> (identified by
	 * <code>itemCode</code>) to a particular invoice (identified by
	 * <code>invoiceCode</code>) with the given 30-day <code>periodFee</code> and
	 * <code>beginDate/endDate</code>.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param amount
	 */
	public static void addEquipmentToInvoice(String invoiceCode, String itemCode, double periodFee, String beginDate,
			String endDate) {
		int invoiceId = InvoiceDataUtil.getInvoiceId(invoiceCode);
		int itemId = InvoiceDataUtil.getItemId(itemCode);
 
		try {
			Connection conn = ConnectionFactory.Connection();
			String equipmentinsertQuery = "insert into InvoiceItem (invoiceId, itemId, leaseFee, startLeaseDate, endLeaseDate, equipmentType) values (?, ?, ?, ?, ?, ?)";
			PreparedStatement equipmentInsertStatement = conn.prepareStatement(equipmentinsertQuery,
					Statement.RETURN_GENERATED_KEYS);
			equipmentInsertStatement.setInt(1, invoiceId);
			equipmentInsertStatement.setInt(2, itemId);
			equipmentInsertStatement.setDouble(3, periodFee);
			equipmentInsertStatement.setString(4, beginDate);
			equipmentInsertStatement.setString(5, endDate);
			equipmentInsertStatement.setString(6, "L");
			equipmentInsertStatement.executeUpdate();
			equipmentInsertStatement.close();
			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1);
		}
	}
 
	/**
	 * Adds a particular service (identified by <code>itemCode</code>) to a
	 * particular invoice (identified by <code>invoiceCode</code>) with the
	 * specified number of hours.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param billedHours
	 */
	public static void addServiceToInvoice(String invoiceCode, String itemCode, double billedHours) {
		int invoiceId = InvoiceDataUtil.getInvoiceId(invoiceCode);
		int itemId = InvoiceDataUtil.getItemId(itemCode);
 
		try {
			Connection conn = ConnectionFactory.Connection();
			String serviceInsertQuery = "insert into InvoiceItem (invoiceId, itemId, hoursBilled, equipmentType) values (?, ?, ?, ?)";
			PreparedStatement serviceInsertStatement = conn.prepareStatement(serviceInsertQuery,
					Statement.RETURN_GENERATED_KEYS);
			serviceInsertStatement.setInt(1, invoiceId);
			serviceInsertStatement.setInt(2, itemId);
			serviceInsertStatement.setDouble(3, billedHours);
			serviceInsertStatement.setString(4, null);
			serviceInsertStatement.executeUpdate();
			serviceInsertStatement.close();
			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1);
		}
	}
}