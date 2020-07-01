package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

import model.Amenity;
import model.Apartment;
import model.Reservation;
import rest.Main;

public class AmenitiesDAO {
	
	private static AmenitiesDAO instance;
	
	private String AMENITIES_FILE_PATH = "data/amenities.json";
	
	private Map<Integer, Amenity> amenities = new HashMap<Integer, Amenity>();
	
	private AmenitiesDAO() {
		loadData();
	}
	
	public static AmenitiesDAO getInstance() {
		if(instance == null)
			instance = new AmenitiesDAO();
		
		return instance;
	}
	
	private void loadData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(AMENITIES_FILE_PATH));
			List<Amenity> data = Main.g.fromJson(br, new TypeToken<List<Amenity>>(){}.getType());
			for(Amenity amenity : data) {
				this.amenities.put(amenity.getId(), amenity);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void saveData() {
		String json = Main.g.toJson(amenities.values());
		
		try {
			FileWriter writer = new FileWriter(AMENITIES_FILE_PATH);
			writer.write(json);
			writer.close();
			  
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean addNewAmenity(Amenity newAmenity) {
		newAmenity.setId(amenities.size());
		amenities.put(newAmenity.getId(), newAmenity);
		saveData();
		return true;
	}
	
	public boolean updateAmenity(Amenity newData) {
		Amenity amenity = amenities.get(newData.getId());
		if(amenity == null)
			return false;
		else {
			amenity = newData;
			saveData();
			return true;
		}
	}
	
	public boolean deleteAmenity(Amenity toDelete) {
		Amenity amenity = amenities.get(toDelete.getId());
		if(amenity == null)
			return false;
		else {
			amenities.remove(amenity.getId());
			saveData();
			return true;
		}
	}
	
	public List<Amenity> getAmenities() {
		return (List<Amenity>) amenities.values();
	}
}
