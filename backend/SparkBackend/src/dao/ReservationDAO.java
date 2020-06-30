package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import model.Reservation;
import model.User;
import rest.Main;

public class ReservationDAO {
	
	private static ReservationDAO instance;
	
	private String RESERVATIONS_FILE_PATH = "data/reservations.json";
	
	private List<Reservation> reservations;
	
	private ReservationDAO() {
		loadData();
	}
	
	public static ReservationDAO getInstance() {
		if(instance == null)
			instance = new ReservationDAO();
		
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
	
	
	
	public List<Reservation> getMyReservations(String host) {
		List<Reservation> myReservations = new ArrayList<Reservation>();
		
		for(Reservation reservation : reservations) {
			if(ApartmentsDAO.getInstance().getHost(reservation.getApartment()).getUsername().equals(host)) {
				myReservations.add(reservation);
			}
		}
		
		return myReservations;
	}

}
