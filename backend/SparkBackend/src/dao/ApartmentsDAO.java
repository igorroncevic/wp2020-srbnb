package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.Apartment;
import beans.users.Admin;
import beans.users.Guest;
import beans.users.Host;

public class ApartmentsDAO {
	
	private static ApartmentsDAO instance;
	
	private String APARTMENTS_FILE_PATH = "data/apartments.json";
	private Gson gson = new Gson();
	
	private List<Apartment> apartments;
	
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
			apartments = gson.fromJson(br, new TypeToken<List<Apartment>>(){}.getType());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void saveData() {
		String json = gson.toJson(apartments);
		
		try {
			FileWriter writer = new FileWriter(APARTMENTS_FILE_PATH);
			writer.write(json);
			writer.close();
			  
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
