package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import model.Amenity;
import model.Reservation;
import rest.Main;

public class AmenitiesDAO {
	
	private static AmenitiesDAO instance;
	
	private String AMENITIES_FILE_PATH = "data/amenities.json";
	
	private List<Amenity> amenities;
	
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
			amenities = Main.g.fromJson(br, new TypeToken<List<Amenity>>(){}.getType());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void saveData() {
		String json = Main.g.toJson(amenities);
		
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
		amenities.add(newAmenity);
		saveData();
		return true;
	}
}
