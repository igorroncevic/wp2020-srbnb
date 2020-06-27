package model;

public class Address {
	
	private String street;
	private String number;
	private String place;
	private int zipCode;
	
	public Address(String street, String number, String place, int zipCode) {
		this.street = street;
		this.number = number;
		this.place = place;
		this.zipCode = zipCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
	
	

}
