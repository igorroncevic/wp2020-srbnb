package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

import model.Reservation;
import model.User;
import model.enums.ReservationStatus;
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
		reservations.put(newReservation.getId(), newReservation);
		saveData();
		return true;
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
	
	public Collection<Reservation> getReservations() {
		return reservations.values();
	}
	
	public boolean cancleReservation(Reservation reservation) {
		if(reservations.get(reservation.getId()) == null)
			return false;
		else {
			if(reservations.get(reservation.getId()).getStatus() == ReservationStatus.Created || reservations.get(reservation.getId()).getStatus() == ReservationStatus.Accepted) {
				reservations.get(reservation.getId()).setStatus(ReservationStatus.Canceled);
				saveData();
				return true;
			}
			else
				return false;
		}
	}
	
	public boolean acceptReservation(Reservation reservation) {
		if(reservations.get(reservation.getId()) == null)
			return false;
		else {
			if(reservations.get(reservation.getId()).getStatus() == ReservationStatus.Created) {
				reservations.get(reservation.getId()).setStatus(ReservationStatus.Accepted);
				saveData();
				return true;
			}
			else
				return false;
		}
	}
	
	public boolean declineReservation(Reservation reservation) {
		if(reservations.get(reservation.getId()) == null)
			return false;
		else {
			if(reservations.get(reservation.getId()).getStatus() == ReservationStatus.Created || reservations.get(reservation.getId()).getStatus() == ReservationStatus.Accepted) {
				reservations.get(reservation.getId()).setStatus(ReservationStatus.Declined);
				saveData();
				return true;
			}
			else
				return false;
		}
	}
	
	public boolean completeReservation(Reservation reservation) {
		if(reservations.get(reservation.getId()) == null)
			return false;
		else {
			if(reservations.get(reservation.getId()).getStatus() == ReservationStatus.Accepted) {
				reservations.get(reservation.getId()).setStatus(ReservationStatus.Completed);
				saveData();
				return true;
			}
			else
				return false;
		}
	}

}
