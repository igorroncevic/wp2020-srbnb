package dao;

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

import model.enums.UserType;
import model.users.*;
import requests.Login;
import rest.Main;

public class UsersDAO {
	
	private static UsersDAO instance;
	
	private String USERS_FILE_PATH = "data/users.json";
	
	private List<User> users;
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
			users = Main.g.fromJson(br, new TypeToken<List<User>>(){}.getType());
			
			for(User user : users) {
				if(user.getType() == UserType.Guest)
					guests.add(user);
				else if(user.getType() == UserType.Host)
					hosts.add(user);
				else
					admins.add(user);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void saveData() {
		String json = Main.g.toJson(guests);
		
		try {
			FileWriter writer = new FileWriter(USERS_FILE_PATH);
			writer.write(json);
			writer.close();
			  
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean addNewUser(User newUser) {
		for(User existingUser : users) {
			if(existingUser.getUsername().equals(newUser.getUsername()))
				return false;
		}
		users.add(newUser);
		if(newUser.getType() == UserType.Guest)
			guests.add(newUser);
		else if(newUser.getType() == UserType.Host)
			hosts.add(newUser);
		else
			admins.add(newUser);
		saveData();
		return true;
	}
	
	public User login(Login login) {
		for(User user : users)
			if(user.getUsername().equals(login.getUsername()) && user.getPassword().equals(login.getPassword()))
				return user;
		
		return null;
	}
	
	
	public boolean updateUserData(User newData) {
		for(User user : users)
			if(user.getUsername().equals(newData.getUsername())) {
				user.setPassword(newData.getPassword());
				user.setName(newData.getName());
				user.setLastname(newData.getLastname());
				user.setGender(newData.getGender());
				saveData();
				return true;
			}
				
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
