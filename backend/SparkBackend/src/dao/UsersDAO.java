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
	private String HOSTS_FILE_PATH = "data/hosts.json";
	private String ADMINS_FILE_PATH = "data/admins.json";
	private Gson gson = new Gson();
	
	private List<Admin> admins = new ArrayList<Admin>();
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
	
	private void loadData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(GUESTS_FILE_PATH));
			guests = gson.fromJson(br, new TypeToken<List<Guest>>(){}.getType());
			
			br = new BufferedReader(new FileReader(HOSTS_FILE_PATH));
			hosts = gson.fromJson(br, new TypeToken<List<Host>>(){}.getType());
			
			br = new BufferedReader(new FileReader(ADMINS_FILE_PATH));
			admins = gson.fromJson(br, new TypeToken<List<Admin>>(){}.getType());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
	
	public boolean addNewGuest(Guest newGuest) {
		for(Guest existingGuest : guests) {
			if(existingGuest.getUsername().equals(newGuest.getUsername()))
				return false;
		}
		guests.add(newGuest);
		saveGuests();
		return true;
	}
	
	public User login(Login login) {
		for(Guest guest : guests) {
			if(guest.getUsername().equals(login.getUsername()) && guest.getPassword().equals(login.getPassword())) {
				return guest;
			}
		}
		
		for(Host host : hosts) {
			if(host.getUsername().equals(login.getUsername()) && host.getPassword().equals(login.getPassword())) {
				return host;
			}
		}
		
		for(Admin admin : admins) {
			if(admin.getUsername().equals(login.getUsername()) && admin.getPassword().equals(login.getPassword())) {
				return admin;
			}
		}
		
		return null;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
