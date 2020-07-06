package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
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

import model.Amenity;
import model.Apartment;
import model.Reservation;
import model.User;
import model.enums.ApartmentStatus;
import requests.ApartmentSearch;
import rest.Main;

public class ApartmentsDAO {
	
	private static ApartmentsDAO instance;
	
	private String APARTMENTS_FILE_PATH = "data/apartments.json";
	
	private Map<Integer, Apartment> apartments = new HashMap<Integer, Apartment>();
	
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
		newApartment.setAvailableDaysForRent(new ArrayList<Date>(newApartment.getDaysForRent()));
		newApartment.setStatus(ApartmentStatus.Active);
		apartments.put(newApartment.getId(), newApartment);
		saveData();
		return true;
	}
	
	public boolean updateApartment(Apartment newData) {
		if(apartments.get(newData.getId()) == null)
			return false;
		else {
			newData.setAvailableDaysForRent(new ArrayList<Date>(newData.getDaysForRent()));
			apartments.replace(newData.getId(), newData);
			saveData();
			return true;
		}
	}
	
	public boolean deleteApartment(int id) {
		Apartment apartment = apartments.get(id);
		if(apartment == null)
			return false;
		else {
			apartment.setStatus(ApartmentStatus.Inactive);
			saveData();
			return true;
		}
	}
	
	public User getHost(int apartmentId) {
		return UsersDAO.getInstance().getUser(apartments.get(apartmentId).getHost());
	}
	
	public List<Apartment> searchApartments(ApartmentSearch search, List<Apartment> apartments) {
		List<Apartment> searchResult = new ArrayList<Apartment>();
		
		search:
		for(Apartment apartment : apartments) {
			if(search.getCheckInDate() != null && search.getCheckOutDate() != null && !isApartmentAvaliable(apartment.getId(), search.getCheckInDate(), search.getCheckOutDate())) continue;
			if(search.getLocation() != null && !apartment.getLocation().getAddress().getPlace().toLowerCase().contains(search.getLocation().toLowerCase())) continue;
			if(search.getMinPrice() != -1 && apartment.getPricePerNight() < search.getMinPrice()) continue;
			if(search.getMaxPrice() != -1 && apartment.getPricePerNight() > search.getMaxPrice()) continue;
			if(search.getMinRooms() != -1 && apartment.getNumberOfRooms() < search.getMinRooms()) continue;
			if(search.getMaxRooms() != -1 && apartment.getNumberOfRooms() > search.getMaxRooms()) continue;
			if(search.getNumberOfGuests() != -1 && apartment.getNumberOfGuest() < search.getNumberOfGuests()) continue;
			if(search.getType() != null && apartment.getType() != search.getType()) continue;
			if(search.getAmenities() != null)
				for(int amenity : search.getAmenities())
					if(!apartment.getAmenities().contains(amenity))
						continue search;
			if(search.getStatus() != null && apartment.getStatus() != search.getStatus()) continue;
			
			searchResult.add(apartment);
		}
		
		return searchResult;
	}
	
	public List<Apartment> getActiveApartments() {
		List<Apartment> active = new ArrayList<Apartment>();
		
		for(Apartment apartment : apartments.values())
			if(apartment.getStatus() == ApartmentStatus.Active)
				active.add(apartment);
		
		return active;
	}
	
	public List<Apartment> getMyApartments(String host) {
		List<Apartment> myApartments = new ArrayList<Apartment>();
		
		for(Apartment apartment : apartments.values())
			if(apartment.getHost().equals(host))
				myApartments.add(apartment);
		
		return myApartments;
	}
	
	public List<Apartment> getApartments() {
		return new ArrayList<Apartment>(apartments.values());
	}
	
	public Apartment getApartment(int id) {
		return apartments.get(id);
	}
	
	public void deleteAmenity(int id) {
		for(Apartment apartment : apartments.values())
			if(apartment.getAmenities().contains(id))
				apartment.getAmenities().remove(id);
	}
	
	public boolean setDaysRented(int apartmentId, Date startDate, Date endDate) {
		Apartment apartment = apartments.get(apartmentId);
		for(int i = 0; i < apartment.getAvailableDaysForRent().size(); i = i + 2) {
			Date start = apartment.getAvailableDaysForRent().get(i);
			Date end = apartment.getAvailableDaysForRent().get(i+1);
			if(startDate.compareTo(start) == 0 && endDate.compareTo(end) == 0) {
				apartment.getAvailableDaysForRent().remove(i);
				apartment.getAvailableDaysForRent().remove(i+1);
				saveData();
				return true;
			} else if(startDate.compareTo(start) == 0 && endDate.compareTo(end) < 0) {
				apartment.getAvailableDaysForRent().remove(i);
				apartment.getAvailableDaysForRent().add(i, endDate);
				saveData();
				return true;
			} else if(startDate.compareTo(start) > 0 && endDate.compareTo(end) == 0) {
				apartment.getAvailableDaysForRent().remove(i+1);
				apartment.getAvailableDaysForRent().add(i+1, startDate);
				saveData();
				return true;
			} else if(startDate.compareTo(start) > 0 && endDate.compareTo(end) < 0) {
				apartment.getAvailableDaysForRent().add(i+1, startDate);
				apartment.getAvailableDaysForRent().add(i+2, endDate);
				saveData();
				return true;
			}
		}
		return false;
	}
	
	public boolean isApartmentAvaliable(int apartmentId, Date startDate, Date endDate) {
		Apartment apartment = apartments.get(apartmentId);
		for(int i = 0; i < apartment.getAvailableDaysForRent().size(); i = i + 2) {
			Date start = apartment.getAvailableDaysForRent().get(i);
			Date end = apartment.getAvailableDaysForRent().get(i+1);
			if(startDate.compareTo(start) == 0 && endDate.compareTo(end) == 0) {
				return true;
			} else if(startDate.compareTo(start) == 0 && endDate.compareTo(end) < 0) {
				return true;
			} else if(startDate.compareTo(start) > 0 && endDate.compareTo(end) == 0) {
				return true;
			} else if(startDate.compareTo(start) > 0 && endDate.compareTo(end) < 0) {
				return true;
			}
		}
		return false;
	}
	
	public void removeRentedDays(int apartmentId, Date startDate, Date endDate) {
		Apartment apartment = apartments.get(apartmentId);
		for(int i = 1; i < apartment.getAvailableDaysForRent().size(); i = i + 2) {
			Date start = apartment.getAvailableDaysForRent().get(i);
			Date end = apartment.getAvailableDaysForRent().get(i+1);
			if(startDate.compareTo(start) == 0 && endDate.compareTo(end) == 0) {
				apartment.getAvailableDaysForRent().remove(i);
				apartment.getAvailableDaysForRent().remove(i+1);
				saveData();
				break;
			} else if(startDate.compareTo(start) == 0 && endDate.compareTo(end) < 0) {
				apartment.getAvailableDaysForRent().remove(i);
				apartment.getAvailableDaysForRent().add(i, endDate);
				saveData();
				break;
			} else if(startDate.compareTo(start) > 0 && endDate.compareTo(end) == 0) {
				apartment.getAvailableDaysForRent().remove(i+1);
				apartment.getAvailableDaysForRent().add(i+1, startDate);
				saveData();
				break;
			} else if(startDate.compareTo(start) > 0 && endDate.compareTo(end) < 0) {
				apartment.getAvailableDaysForRent().add(i+1, startDate);
				apartment.getAvailableDaysForRent().add(i+2, endDate);
				saveData();
				break;
			}
		}
	}
	

}
