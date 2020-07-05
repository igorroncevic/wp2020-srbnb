package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

import model.Reservation;
import model.User;
import model.enums.ReservationStatus;
import requests.ReservationSearch;
import rest.Main;

public class ReservationsDAO {
	
	private static ReservationsDAO instance;
	
	private String RESERVATIONS_FILE_PATH = "data/reservations.json";
	
	private Map<Integer, Reservation> reservations = new HashMap<Integer, Reservation>();
	
	private ReservationsDAO() {
		loadData();
	}
	
	public static ReservationsDAO getInstance() {
		if(instance == null)
			instance = new ReservationsDAO();
		
		return instance;
	}
	
	private void loadData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(RESERVATIONS_FILE_PATH));
			List<Reservation> data = Main.g.fromJson(br, new TypeToken<List<Reservation>>(){}.getType());
			for(Reservation reservation : data) {
				reservations.put(reservation.getId(), reservation);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void saveData() {
		String json = Main.g.toJson(reservations.values());
		
		try {
			FileWriter writer = new FileWriter(RESERVATIONS_FILE_PATH);
			writer.write(json);
			writer.close();
			  
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean addNewReservation(Reservation newReservation) {
		newReservation.setId(reservations.size());
		newReservation.setStatus(ReservationStatus.Created);
		newReservation.setTotalPrice(ApartmentsDAO.getInstance().getApartment(newReservation.getApartment()).getPricePerNight() * newReservation.getNightsStaying());
		Date startDate = newReservation.getCheckInDay();
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		c.add(Calendar.DAY_OF_MONTH, newReservation.getNightsStaying());
		Date endDate = c.getTime();
		if(ApartmentsDAO.getInstance().setDaysRented(newReservation.getApartment(), startDate, endDate)) {
			reservations.put(newReservation.getId(), newReservation);
			saveData();
			return true;
		} else {
			return false;
		}
	}
	
	
	
	public List<Reservation> getHostReservations(String host) {
		List<Reservation> hostReservations = new ArrayList<Reservation>();
		
		for(Reservation reservation : reservations.values()) {
			if(ApartmentsDAO.getInstance().getHost(reservation.getApartment()).getUsername().equals(host)) {
				hostReservations.add(reservation);
			}
		}
		
		return hostReservations;
	}
	
	public List<Reservation> getGuestReservations(String guest) {
		List<Reservation> guestReservations = new ArrayList<Reservation>();
		
		for(Reservation reservation : reservations.values()) {
			if(reservation.getGuest().equals(guest)) {
				guestReservations.add(reservation);
			}
		}
		
		return guestReservations;
	}
	
	public List<Reservation> getReservations() {
		return new ArrayList<Reservation>(reservations.values());
	}
	
	public Reservation getReservation(int id) {
		return reservations.get(id);
	}
	
	public List<Reservation> searchReservations(ReservationSearch search, List<Reservation> reservations) {
		List<Reservation> match = new ArrayList<Reservation>();
		
		for(Reservation reservation : reservations) {
			if(search.getGuest() != null && !reservation.getGuest().toLowerCase().contains(search.getGuest().toLowerCase())) continue;
			if(search.getStatus() != null && reservation.getStatus() != search.getStatus()) continue;
			
			match.add(reservation);
		}
		
		return match;
	}
	
	public boolean cancleReservation(int id) {
		if(reservations.get(id) == null)
			return false;
		else {
			if(reservations.get(id).getStatus() == ReservationStatus.Created || reservations.get(id).getStatus() == ReservationStatus.Accepted) {
				Date startDate = reservations.get(id).getCheckInDay();
				Calendar c = Calendar.getInstance();
				c.setTime(startDate);
				c.add(Calendar.DAY_OF_MONTH, reservations.get(id).getNightsStaying());
				Date endDate = c.getTime();
				ApartmentsDAO.getInstance().removeRentedDays(reservations.get(id).getApartment(), startDate, endDate);
				reservations.get(id).setStatus(ReservationStatus.Canceled);
				saveData();
				return true;
			}
			else
				return false;
		}
	}
	
	public boolean acceptReservation(int id) {
		if(reservations.get(id) == null)
			return false;
		else {
			if(reservations.get(id).getStatus() == ReservationStatus.Created) {
				reservations.get(id).setStatus(ReservationStatus.Accepted);
				saveData();
				return true;
			}
			else
				return false;
		}
	}
	
	public boolean declineReservation(int id) {
		if(reservations.get(id) == null)
			return false;
		else {
			if(reservations.get(id).getStatus() == ReservationStatus.Created || reservations.get(id).getStatus() == ReservationStatus.Accepted) {
				Date startDate = reservations.get(id).getCheckInDay();
				Calendar c = Calendar.getInstance();
				c.setTime(startDate);
				c.add(Calendar.DAY_OF_MONTH, reservations.get(id).getNightsStaying());
				Date endDate = c.getTime();
				ApartmentsDAO.getInstance().removeRentedDays(reservations.get(id).getApartment(), startDate, endDate);
				reservations.get(id).setStatus(ReservationStatus.Declined);
				saveData();
				return true;
			}
			else
				return false;
		}
	}
	
	public boolean completeReservation(int id) {
		if(reservations.get(id) == null)
			return false;
		else {
			if(reservations.get(id).getStatus() == ReservationStatus.Accepted) {
				reservations.get(id).setStatus(ReservationStatus.Completed);
				saveData();
				return true;
			}
			else
				return false;
		}
	}
	
	public boolean guestHasCompletedReservation(String guest, int apartment) {
		for(Reservation reservation : reservations.values())
			if(reservation.getApartment() == apartment && reservation.getGuest().equals(guest) && 
				(reservation.getStatus() == ReservationStatus.Declined || reservation.getStatus() == ReservationStatus.Completed))
				return true;
		
		return false;
	}

}
