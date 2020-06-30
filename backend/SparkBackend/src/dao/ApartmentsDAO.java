package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import model.Apartment;
import model.User;
import requests.ApartmentSearch;
import rest.Main;

public class ApartmentsDAO {
	
	private static ApartmentsDAO instance;
	
	private String APARTMENTS_FILE_PATH = "data/apartments.json";
	
	private Map<Integer, Apartment> apartments = new HashMap<Integer, Apartment>();;
	
	private ApartmentsDAO() {
		loadData();
	}
	
	public static ApartmentsDAO getInstance() {
		if(instance == null)
			instance = new ApartmentsDAO();
		
		return instance;
	}
	
	private void loadData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(APARTMENTS_FILE_PATH));
			List<Apartment> data = Main.g.fromJson(br, new TypeToken<List<Apartment>>(){}.getType());
			for(Apartment apartment : data) {
				this.apartments.put(apartment.getId(), apartment);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void saveData() {
		String json = Main.g.toJson(apartments.values());
		
		try {
			FileWriter writer = new FileWriter(APARTMENTS_FILE_PATH);
			writer.write(json);
			writer.close();
			  
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean addNewApartment(Apartment newApartment) {
		newApartment.setId(apartments.size());
		apartments.put(newApartment.getId(), newApartment);
		saveData();
		return true;
	}
	
	public List<Apartment> searchApartments(ApartmentSearch search) {
		List<Apartment> searchResult = new ArrayList<Apartment>();
		
		for(Apartment apartment : apartments.values()) {
			//if(search.getCheckInDate() != null && UslovNijeIspunjen) continue;
			//if(search.getCheckOutDate() != null && UslovNijeIspunjen) continue;
			if(search.getLocation() != null && !apartment.getLocation().getAddress().getPlace().equals(search.getLocation())) continue;
			if(search.getMinPrice() != -1 && apartment.getPricePerNight() < search.getMinPrice()) continue;
			if(search.getMaxPrice() != -1 && apartment.getPricePerNight() > search.getMaxPrice()) continue;
			if(search.getMinRooms() != -1 && apartment.getNumberOfRooms() < search.getMinRooms()) continue;
			if(search.getMaxRooms() != -1 && apartment.getNumberOfRooms() > search.getMaxRooms()) continue;
			if(search.getNumberOfGuests() != -1 && apartment.getNumberOfGuest() < search.getNumberOfGuests()) continue;
			
			searchResult.add(apartment);
		}
		
		return searchResult;
	}
	
	public User getHost(int apartmentId) {
		return UsersDAO.getInstance().getUser(apartments.get(apartmentId).getHost());
	}

}
