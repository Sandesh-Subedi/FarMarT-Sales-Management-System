package com.fmt;
 
import java.util.List;
 
/*
 * This is a person class which contains the details of the person.
 */
public class Person {
 
	private List<String> emailAddress;
	private String personCode;
	private String lastName;
	private String firstName;
	private Address address;
 
	public Person(String personCode, String lastName, String firstName, Address address, List<String> emailAddress) {
		this.personCode = personCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.emailAddress = emailAddress;
	}
 
	public Person(String personCode, String lastName, String firstName, Address address) {
		this.personCode = personCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
	}
 
	// getters for the initiated variables
	public String getPersonCode() {
		return personCode;
	}
 
	public String getLastName() {
		return lastName;
	}
 
	public String getFirstName() {
		return firstName;
	}
 
	public Address getAddress() {
		return address;
	}
 
	public List<String> getEmailAddress() {
		return emailAddress;
	}
 
	public String getName() {
		return (this.lastName + ", " + this.firstName);
	}
 
	public void addEmail(String email) {
		this.emailAddress.add(email);
	}
 
	@Override
	public String toString() {
		return String.format("%s, %-10s(%s : %s)\n \t %s \n \t %s %s %s %s\n", lastName, firstName, personCode,
				getEmailAddress(),address.getStreet(),address.getCity(),address.getState(),address.getZipCode(),address.getCountry());
 
		}
}