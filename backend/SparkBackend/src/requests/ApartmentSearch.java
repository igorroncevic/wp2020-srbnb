package requests;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.enums.ApartmentStatus;
import model.enums.ApartmentType;
import rest.Main;
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
	private ApartmentType type = null;
	private List<Integer> amenities= null;
	private ApartmentStatus status = null;
	
	public ApartmentSearch(Request req) {
		String sDateStart = req.queryParams("startDate");
		if(sDateStart != null)
			try {
				this.checkInDate = Main.dateFormat.parse(sDateStart);
			} catch (ParseException e) {
				System.out.print("Error while parsing check in date");
			}
		
		String sDateEnd = req.queryParams("endDate");
		if(sDateEnd != null)
			try {
				this.checkOutDate = Main.dateFormat.parse(sDateEnd);
			} catch (ParseException e) {
				System.out.print("Error while parsing check out date");
			}
		
		this.location = req.queryParams("location");
		
		String sMinPrice = req.queryParams("minPrice");
		if(sMinPrice != null)
			this.minPrice = Double.parseDouble(sMinPrice);
		
		String sMaxPrice = req.queryParams("maxPrice");
		if(sMaxPrice != null)
			this.maxPrice = Double.parseDouble(sMaxPrice);
		
		String sMinRooms = req.queryParams("minRooms");
		if(sMinRooms != null)
			this.minRooms = Integer.parseInt(sMinRooms);
		
		String sMaxRooms = req.queryParams("maxRooms");
		if(sMaxRooms != null)
			this.maxRooms = Integer.parseInt(sMaxRooms);
		
		String sNumberOfGuests = req.queryParams("guests");
		if(sNumberOfGuests != null)
			this.numberOfGuests = Integer.parseInt(sNumberOfGuests);
		
		String sType = req.queryParams("type");
		if(sType != null && (sType.equals("Full_Apartment") || sType.equals("Room")))
			this.type = ApartmentType.valueOf(sType);
		
		String sAmenities = req.queryParams("amenities");
		if(sAmenities != null) {
			String[] values = sAmenities.split(",");
			this.amenities = new ArrayList<Integer>();
			for(String ameni : values) {
				int a = Integer.parseInt(ameni);
				this.amenities.add(a);
			}
		}
		
		String sStatus = req.queryParams("status");
		if(sStatus != null && (sStatus.equals("Active") || sStatus.equals("Inactive")))
			this.status = ApartmentStatus.valueOf(sStatus);
	}
	

	public ApartmentStatus getStatus() {
		return status;
	}


	public void setStatus(ApartmentStatus status) {
		this.status = status;
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

	public ApartmentType getType() {
		return type;
	}

	public void setType(ApartmentType type) {
		this.type = type;
	}

	public List<Integer> getAmenities() {
		return amenities;
	}

	public void setAmenities(List<Integer> amenities) {
		this.amenities = amenities;
	}
	
	

}
