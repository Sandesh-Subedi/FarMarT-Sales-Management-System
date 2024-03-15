package com.fmt;
 
/*
 * This is the class that stores the address which have the details: street, city, 
 * state, zipCode code and country
 */
public class Address {
	private String street;
	private String city;
	private String state;
	private String zipCode;
	private String country;
 
	public Address(String street, String city, String state, String zipCode, String country) {
		super();
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.country = country;
	}
 
	// Getters for initiated variables
	public String getStreet() {
		return street;
	}
 
	public String getCity() {
		return city;
	}
 
	public String getState() {
		return state;
	}
 
	public String getZipCode() {
		return zipCode;
	}
 
	public String getCountry() {
		return country;
	}
}