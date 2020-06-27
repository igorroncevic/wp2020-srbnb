package model;

import java.util.Date;

import model.enums.ReservationStatus;
import model.users.Guest;

public class Reservation {
	
	private int apartment;
	private Date checkInDay;
	private int nightsStaying = 1;
	private double totalPrice;
	private String reservationMessage;
	private String guest;
	private ReservationStatus status;
	
	public Reservation() {
		
	}
	
	public Reservation(int apartment, Date checkInDay, int nightsStaying, String guest) {
		super();
		this.apartment = apartment;
		this.checkInDay = checkInDay;
		this.nightsStaying = nightsStaying;
		this.guest = guest;
		this.status = ReservationStatus.Created;
	}

}
