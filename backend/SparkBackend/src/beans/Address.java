package beans;

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

}
