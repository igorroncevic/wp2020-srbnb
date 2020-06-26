package model;

import java.util.Date;

import model.users.Guest;

public class Reservation {
	
	enum ReservationStatus {
		Created,
		Declined,
		Canceled,
		Accepted,
		Completed
	}
	
	private Apartment apartment;
	private Date checkInDay;
	private int nightsStaying = 1;
	private double totalPrice;
	private String reservationMessage;
	private Guest guest;
	private ReservationStatus reservationStatus;
	
	public Reservation() {
		
	}
	
	public Reservation(Apartment apartment, Date checkInDay, int nightsStaying, Guest guest) {
		super();
		this.apartment = apartment;
		this.checkInDay = checkInDay;
		this.nightsStaying = nightsStaying;
		this.guest = guest;
		this.reservationStatus = ReservationStatus.Created;
	}

}
