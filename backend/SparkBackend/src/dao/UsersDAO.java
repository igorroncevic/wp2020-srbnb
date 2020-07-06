package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
import requests.UserSearch;
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
		if (instance == null)
			instance = new UsersDAO();

		return instance;
	}

	private void loadData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(USERS_FILE_PATH));
			List<User> data = Main.g.fromJson(br, new TypeToken<List<User>>() {
			}.getType());
			for (User user : data) {
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
		if (users.containsKey(newUser.getUsername()))
			return false;

		users.put(newUser.getUsername(), newUser);
		saveData();
		return true;
	}

	public User login(Login login) {
		User user = users.get(login.getUsername());
		if (user != null && user.getPassword().equals(login.getPassword()) && !user.isBlocked())
			return user;

		return null;
	}

	public boolean updateUserData(User newData) {
		if (users.get(newData.getUsername()) == null)
			return false;
		else {
			newData.setType(getUserType(newData.getUsername()));
			users.replace(newData.getUsername(), newData);
			saveData();
			return true;
		}

	}
	
	public boolean blockUser(String username) {
		if (users.get(username) == null)
			return false;
		else {
			users.get(username).setBlocked(true);
			saveData();
			return true;
		}
	}
	
	public boolean doesUserExists(String username) {
		if (users.get(username) == null)
			return false;
		else {
			return true;
		}
	}

	public List<User> getAllUsers() {
		return new ArrayList<User>(users.values());
	}

	public List<User> searchUsers(UserSearch search, List<User> users) {
		List<User> match = new ArrayList<User>();

		for (User user : users) {
			if (search.getType() != null && user.getType() != search.getType())
				continue;
			if (search.getGender() != null && user.getGender() != search.getGender())
				continue;
			if (search.getUsername() != null && !user.getUsername().contains(search.getUsername().toLowerCase()))
				continue;

			match.add(user);
		}

		return match;
	}

	public User getUser(String username) {
		return users.get(username);
	}

	public UserType getUserType(String username) {
		return users.get(username).getType();
	}

	public List<User> getMyGuests(String host) {
		List<User> myGuests = new ArrayList<User>();

		for (Reservation reservation : ReservationsDAO.getInstance().getHostReservations(host)) {
			if (!myGuests.contains(users.get(reservation.getGuest()))) {	//unikatni gosti, ne rezervacije
				myGuests.add(users.get(reservation.getGuest()));
			}
		}

		return myGuests;
	}

	public boolean isMyGuest(String host, String guest) {
		for (Reservation reservation : ReservationsDAO.getInstance().getHostReservations(host))
			if (reservation.getGuest().equals(guest))
				return true;

		return false;
	}

}
