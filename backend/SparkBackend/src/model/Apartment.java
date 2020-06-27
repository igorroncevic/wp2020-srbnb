package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.enums.ApartmentStatus;
import model.enums.ApartmentType;
import model.users.Host;

public class Apartment {

	private int id;
	private ApartmentType type;
	private int numberOfRooms;
	private int numberOfGuest;
	private Location location;
	private List<Date> daysForRent;
	private List<Date> availableDaysForRent;
	private String host;
	private List<Comment> comments;
	private List<String> images;
	private double pricePerNight;
	private int checkInTime = 14;
	private int checkOutTime = 10;
	private ApartmentStatus status;
	private List<Integer> amenities;
	
	public Apartment(int numberOfRooms, int numberOfGuest, Location location, List<Date> daysForRent,
			List<Date> availableDaysForRent, String host, double pricePerNight) {
		super();
		this.type = ApartmentType.Ful_Apartment;
		this.numberOfRooms = numberOfRooms;
		this.numberOfGuest = numberOfGuest;
		this.location = location;
		this.daysForRent = daysForRent;
		this.availableDaysForRent = availableDaysForRent;
		this.host = host;
		this.pricePerNight = pricePerNight;
		this.status = ApartmentStatus.Active;
		this.amenities = new ArrayList<Integer>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ApartmentType getType() {
		return type;
	}

	public void setType(ApartmentType type) {
		this.type = type;
	}

	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	public int getNumberOfGuest() {
		return numberOfGuest;
	}

	public void setNumberOfGuest(int numberOfGuest) {
		this.numberOfGuest = numberOfGuest;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<Date> getDaysForRent() {
		return daysForRent;
	}

	public void setDaysForRent(List<Date> daysForRent) {
		this.daysForRent = daysForRent;
	}

	public List<Date> getAvailableDaysForRent() {
		return availableDaysForRent;
	}

	public void setAvailableDaysForRent(List<Date> availableDaysForRent) {
		this.availableDaysForRent = availableDaysForRent;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public double getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(double pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public int getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(int checkInTime) {
		this.checkInTime = checkInTime;
	}

	public int getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(int checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public ApartmentStatus getStatus() {
		return status;
	}

	public void setStatus(ApartmentStatus status) {
		this.status = status;
	}

	public List<Integer> getAmenities() {
		return amenities;
	}

	public void setAmenities(List<Integer> amenities) {
		this.amenities = amenities;
	}

	

}
