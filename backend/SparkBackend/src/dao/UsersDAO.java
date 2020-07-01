package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;

import model.Reservation;
import model.User;
import model.enums.UserType;
import requests.Login;
import rest.Main;

public class UsersDAO {
	
	private static UsersDAO instance;
	
	private String USERS_FILE_PATH = "data/users.json";
	
	private Map<String, User> users = new HashMap<String, User>();
	private List<User> admins = new ArrayList<User>();
	private List<User> guests = new ArrayList<User>();
	private List<User> hosts = new ArrayList<User>();
	
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
			BufferedReader br = new BufferedReader(new FileReader(USERS_FILE_PATH));
			List<User> data = Main.g.fromJson(br, new TypeToken<List<User>>(){}.getType());
			for(User user : data) {
				this.users.put(user.getUsername(), user);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void saveData() {
		String json = Main.g.toJson(users.values());
		
		try {
			FileWriter writer = new FileWriter(USERS_FILE_PATH);
			writer.write(json);
			writer.close();
			  
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean addNewUser(User newUser) {
		if(users.containsKey(newUser.getUsername()))
			return false;
		
		users.put(newUser.getUsername(), newUser);
		saveData();
		return true;
	}
	
	public User login(Login login) {
		User user = users.get(login.getUsername());
		if(user != null && user.getPassword().equals(login.getPassword()))
			return user;			
		
		return null;
	}
	
	
	public boolean updateUserData(User newData) {
		User user = users.get(newData.getUsername());
		if(user == null)
			return false;
		else {
			user.setPassword(newData.getPassword());
			user.setName(newData.getName());
			user.setLastname(newData.getLastname());
			user.setGender(newData.getGender());
			saveData();
			return true;
		}
				
	}
	
	public List<User> getAllUsers() {
		return (List<User>) users.values();
	}
	
	public List<User> searchUsers(String search) {
		List<User> match = new ArrayList<User>();
		
		for(User user : users.values()) {
			if(user.getName().contains(search) || user.getLastname().contains(search))
				match.add(user);
		}
		
		return match;
	}
	
	public User getUser(String username) {
		return users.get(username);
	}
	
	public List<User> getMyGuests(String host) {
		List<User> myGuests = new ArrayList<User>();
		
		for(Reservation reservation : ReservationsDAO.getInstance().getHostReservations(host)) {
			myGuests.add(users.get(reservation.getGuest()));
		}
		
		return myGuests;
	}
	
	public List<User> searchMyGuests(String host, String search) {
		List<User> match = new ArrayList<User>();
		
		for(User user : getMyGuests(host)) {
			if(user.getName().contains(search) || user.getLastname().contains(search))
				match.add(user);
		}
		
		return match;
	}
	
}
