package com.fmt;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
/**
 * This is the helper class that works to get the different required id's that
 * we are using to add the data in our InvoiceData
 * 
 * @author Sandesh Subedi 
 *         Darius Banks
 * 
 *         Date: 2023-04-28
 */
public class InvoiceDataUtil {
 
	// Helper method to get the address Id
	public static int getAddress(String street, String city, String state, String zip, String country) {
		int addressId = 0;
 
		try {
			Connection conn = ConnectionFactory.Connection();
			String addressInsertQuery = "insert into Address (street, city, state, zipCode, country) values (?, ?, ?, ?, ?)";
			PreparedStatement addressInsertStatement = conn.prepareStatement(addressInsertQuery,
					Statement.RETURN_GENERATED_KEYS);
			addressInsertStatement.setString(1, street);
			addressInsertStatement.setString(2, city);
			addressInsertStatement.setString(3, state);
			addressInsertStatement.setString(4, zip);
			addressInsertStatement.setString(5, country);
			addressInsertStatement.executeUpdate();
			ResultSet generatedKeys = addressInsertStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				addressId = generatedKeys.getInt(1);
			}
			generatedKeys.close();
			addressInsertStatement.close();
			conn.close();
 
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return addressId;
	}
 
	// Helper method to get the personId
	public static int getPersonId(String personCode) {
		String query = "select personId from Person where personCode = ?";
		int personId = 0;
 
		try {
			Connection conn = ConnectionFactory.Connection();
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, personCode);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				personId = rs.getInt("personId");
			} else {
				throw new RuntimeException("Invalid Person" + personCode);
			}
			rs.close();
			statement.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return personId;
	}
 
	// Helper method to get the storeId
	public static int getStoreId(String storeCode) {
		String query = "select storeId from Store where storeCode = ?";
		int storeId = 0;
 
		try {
			Connection conn = ConnectionFactory.Connection();
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, storeCode);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				storeId = rs.getInt("storeId");
			} else {
				throw new RuntimeException("Invalid Store" + storeCode);
			}
			rs.close();
			statement.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return storeId;
	}
 
	// Helper method to get the invoiceId
	public static int getInvoiceId(String invoiceCode) {
		String query = "select invoiceId from Invoice where invoiceCode = ?";
		int invoiceId = 0;
 
		try {
			Connection conn = ConnectionFactory.Connection();
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, invoiceCode);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				invoiceId = rs.getInt("invoiceId");
			} else {
				throw new RuntimeException("Invalid Invoice" + invoiceCode);
			}
			rs.close();
			statement.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return invoiceId;
	}
 
	// Helper method to get the itemId
	public static int getItemId(String itemCode) {
		String query = "select itemId from Item where itemCode = ?";
		int itemId = 0;
 
		try {
			Connection conn = ConnectionFactory.Connection();
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, itemCode);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				itemId = rs.getInt("itemId");
			} else {
				throw new RuntimeException("Invalid Item" + itemCode);
			}
			rs.close();
			statement.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return itemId;
	}
}