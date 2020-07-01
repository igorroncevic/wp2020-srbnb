package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import model.Reservation;
import model.User;
import rest.Main;

public class ReservationsDAO {
	
	private static ReservationsDAO instance;
	
	private String RESERVATIONS_FILE_PATH = "data/reservations.json";
	
	private List<Reservation> reservations;
	
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
			reservations = Main.g.fromJson(br, new TypeToken<List<Reservation>>(){}.getType());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void saveData() {
		String json = Main.g.toJson(reservations);
		
		try {
			FileWriter writer = new FileWriter(RESERVATIONS_FILE_PATH);
			writer.write(json);
			writer.close();
			  
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean addNewReservation(Reservation newReservations) {
		reservations.add(newReservations);
		saveData();
		return true;
	}
	
	
	
	public List<Reservation> getHostReservations(String host) {
		List<Reservation> hostReservations = new ArrayList<Reservation>();
		
		for(Reservation reservation : reservations) {
			if(ApartmentsDAO.getInstance().getHost(reservation.getApartment()).getUsername().equals(host)) {
				hostReservations.add(reservation);
			}
		}
		
		return hostReservations;
	}
	
	public List<Reservation> getGuestReservations(String guest) {
		List<Reservation> guestReservations = new ArrayList<Reservation>();
		
		for(Reservation reservation : reservations) {
			if(reservation.getGuest().equals(guest)) {
				guestReservations.add(reservation);
			}
		}
		
		return guestReservations;
	}
	
	public Collection<Reservation> getReservations() {
		return reservations;
	}

}
