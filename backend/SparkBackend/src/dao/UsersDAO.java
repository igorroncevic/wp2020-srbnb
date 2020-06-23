package dao;

import beans.users.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;

public class UsersDAO {
	
	private static UsersDAO instance;
	
	private String GUESTS_FILE_PATH = "data/guests.json";
	private Gson gson = new Gson();
	
	private List<Administrator> administrators = new ArrayList<Administrator>();
	private List<Guest> guests = new ArrayList<Guest>();
	private List<Host> hosts = new ArrayList<Host>();

	
	private UsersDAO() {
		loadData();
	}
	
	public static UsersDAO getInstance() {
		if(instance == null)
			instance = new UsersDAO();
		
		return instance;
	}
	
	public boolean saveNewGuest(Guest newGuest) {
		for(Guest existingGuest : guests) {
			if(existingGuest.getUsername().equals(newGuest.getUsername()))
				return false;
		}
		guests.add(newGuest);
		saveGuests();
		return true;
	}
	
	private void saveGuests() {
		String json = gson.toJson(guests);
		
		try {
			FileWriter writer = new FileWriter(GUESTS_FILE_PATH);
			writer.write(json);
			writer.close();
			  
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(GUESTS_FILE_PATH));
			guests = gson.fromJson(br, new TypeToken<List<Guest>>(){}.getType());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
