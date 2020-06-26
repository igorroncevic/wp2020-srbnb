package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.users.Host;

public class Apartment {
	
	enum Type {
		Ful_Apartment,
		Room
	}

	enum ApartmentStatus {
		Active,
		Inactive
	}
	
	private Type type;
	private int numberOfRooms;
	private int numberOfGuest;
	private Location location;
	private List<Date> daysForRent;
	private List<Date> availableDaysForRent;
	private Host host;
	private List<String> imagePaths;
	private double pricePerNight;
	private int checkInTime = 14;
	private int checkOutTime = 10;
	private ApartmentStatus status;
	private List<Amenity> amenities;
	
	public Apartment(int numberOfRooms, int numberOfGuest, Location location, List<Date> daysForRent,
			List<Date> availableDaysForRent, Host host, double pricePerNight) {
		super();
		this.type = Type.Ful_Apartment;
		this.numberOfRooms = numberOfRooms;
		this.numberOfGuest = numberOfGuest;
		this.location = location;
		this.daysForRent = daysForRent;
		this.availableDaysForRent = availableDaysForRent;
		this.host = host;
		this.pricePerNight = pricePerNight;
		this.status = ApartmentStatus.Active;
		this.amenities = new ArrayList<Amenity>();
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
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

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public List<String> getImagePaths() {
		return imagePaths;
	}

	public void setImagePaths(List<String> imagePaths) {
		this.imagePaths = imagePaths;
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

	public List<Amenity> getAmenities() {
		return amenities;
	}

	public void setAmenities(List<Amenity> amenities) {
		this.amenities = amenities;
	}
	
	

}
