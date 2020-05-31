package beans;

import java.util.Date;
import java.util.List;

import beans.users.Host;

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
	
	public Apartment(Type type, int numberOfRooms, int numberOfGuest, Location location, List<Date> daysForRent,
			List<Date> availableDaysForRent, Host host, double pricePerNight) {
		super();
		this.type = type;
		this.numberOfRooms = numberOfRooms;
		this.numberOfGuest = numberOfGuest;
		this.location = location;
		this.daysForRent = daysForRent;
		this.availableDaysForRent = availableDaysForRent;
		this.host = host;
		this.pricePerNight = pricePerNight;
	}

}
