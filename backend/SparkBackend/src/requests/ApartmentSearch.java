package requests;

import java.text.DateFormat;
import java.util.Date;

import spark.Request;

public class ApartmentSearch {
	
	private Date checkInDate = null;
	private Date checkOutDate = null;
	private String location = null;
	private double minPrice = -1;
	private double maxPrice = -1;
	private int minRooms = -1;
	private int maxRooms = -1;
	private int numberOfGuests = -1;
	
	public ApartmentSearch() {
		
	}
	
	public ApartmentSearch(Date checkInDate, Date checkOutDate, String location, double minPrice, double maxPrice,
			int minRooms, int maxRooms, int numberOfGuests) {
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.location = location;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.minRooms = minRooms;
		this.maxRooms = maxRooms;
		this.numberOfGuests = numberOfGuests;
	}
	
	public ApartmentSearch(Request req) {
		this.location = req.queryParams("location");
		this.numberOfGuests = Integer.parseInt(req.queryParams("guests"));
		//this.checkInDate = Date.parse(req.queryParams("startDate"));
		//this.checkOutDate =
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}

	public double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public int getMinRooms() {
		return minRooms;
	}

	public void setMinRooms(int minRooms) {
		this.minRooms = minRooms;
	}

	public int getMaxRooms() {
		return maxRooms;
	}

	public void setMaxRooms(int maxRooms) {
		this.maxRooms = maxRooms;
	}

	public int getNumberOfGuests() {
		return numberOfGuests;
	}

	public void setNumberOfGuests(int numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
	}

}
