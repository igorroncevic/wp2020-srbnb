package model;

import java.util.Date;

import model.enums.ReservationStatus;

public class Reservation {
	
	private int id;
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

	public int getApartment() {
		return apartment;
	}

	public void setApartment(int apartment) {
		this.apartment = apartment;
	}

	public Date getCheckInDay() {
		return checkInDay;
	}

	public void setCheckInDay(Date checkInDay) {
		this.checkInDay = checkInDay;
	}

	public int getNightsStaying() {
		return nightsStaying;
	}

	public void setNightsStaying(int nightsStaying) {
		this.nightsStaying = nightsStaying;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getReservationMessage() {
		return reservationMessage;
	}

	public void setReservationMessage(String reservationMessage) {
		this.reservationMessage = reservationMessage;
	}

	public String getGuest() {
		return guest;
	}

	public void setGuest(String guest) {
		this.guest = guest;
	}

	public ReservationStatus getStatus() {
		return status;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
