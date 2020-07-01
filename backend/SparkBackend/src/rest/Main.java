package rest;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.before;
import static spark.Spark.halt;
import static spark.Spark.port;
import static spark.Spark.staticFiles;

import java.io.File;
import java.util.List;
import java.util.Date;
import java.security.Key;
import io.jsonwebtoken.security.Keys;
import model.*;
import model.enums.UserType;
import requests.ApartmentSearch;
import requests.Login;

import org.eclipse.jetty.security.UserAuthentication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import dao.AmenitiesDAO;
import dao.ApartmentsDAO;
import dao.ReservationDAO;
import dao.UsersDAO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Main {

	public static JsonSerializer<Date> ser = (src, typeOfSrc, context) -> src == null ? null : new JsonPrimitive(src.getTime());
	public static JsonDeserializer<Date> deser = (jSon, typeOfT, context) -> jSon == null ? null : new Date(jSon.getAsLong());
	public static Gson g = new GsonBuilder()
			   .registerTypeAdapter(Date.class, ser)
			   .registerTypeAdapter(Date.class, deser).create();
	
	static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public static void main(String[] args) throws Exception {
		
		port(8080);
		
		staticFiles.externalLocation(new File("./WebContent").getCanonicalPath());
		
		get("/test", (req, res) -> {
			
			return g.toJson(AmenitiesDAO.getInstance().getAmenities());
		});
		
		post("/register", (req, res) -> {
			
			String payload = req.body();
			User user = g.fromJson(payload, User.class);
			user.setType(UserType.Guest);
			
			boolean successful = UsersDAO.getInstance().addNewUser(user);
			
			if(successful) {
				res.status(200);
				return "User successfully registered";
			}
			else {
				res.status(400);
				return "Username already exists";
			}
			
		});
		
		post("/login", (req, res) -> {
			
			String payload = req.body();
			Login login = g.fromJson(payload, Login.class);
			
			User user = UsersDAO.getInstance().login(login);
			
			if(user == null) {
				res.status(400);
				return "Failed to login";
			} else {
				return Jwts.builder().setSubject(user.getUsername()).claim("Type", user.getType()).setIssuedAt(new Date()).signWith(key).compact();
			}
			
		});
		
		post("/updateUserData", (req, res) -> {
			String auth = req.headers("Authorization");
			if ((auth != null) && (auth.contains("Bearer "))) {
				String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
				try {
				    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
				    // ako nije bacio izuzetak, onda je OK
				    String payload = req.body();
					User newData = g.fromJson(payload, User.class);
					UsersDAO.getInstance().updateUserData(newData);
					return "";
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			
			return "Error";
		});
		
		get("/users", (req, res) -> {
			String auth = req.headers("Authorization");
			if ((auth != null) && (auth.contains("Bearer "))) {
				String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
				try {
				    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
				    if(!claims.getBody().get("Type").equals("Admin")) {
				    	return "Error";
				    }
				    else {
				    	String search = req.queryParams("search");
						if(search == null)
							return g.toJson(UsersDAO.getInstance().getAllUsers());
						else
							return g.toJson(UsersDAO.getInstance().searchUsers(search));
				    }
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			return "Error";
		});
		
		get("/myGuests", (req, res) -> {
			String auth = req.headers("Authorization");
			if ((auth != null) && (auth.contains("Bearer "))) {
				String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
				try {
				    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
				    if(!claims.getBody().get("Type").equals("Host")) {
				    	return "Error";
				    }
				    else {
				    	String search = req.queryParams("search");
				    	String host = claims.getBody().getSubject();
						if(search == null)
							return g.toJson(UsersDAO.getInstance().getMyGuests(host));
						else
							return g.toJson(UsersDAO.getInstance().searchMyGuests(host, search));
				    }
				} catch (Exception e) {
					System.out.println("You dont have right permission");
				}
			}
			
			return "Error";
		});
		
		get("/apartments", (req, res) -> {
			String auth = req.headers("Authorization");
			if ((auth != null) && (auth.contains("Bearer "))) {
				String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
				try {
				    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
				    if(claims.getBody().get("Type").equals("Host")) {
				    	//Host
				    	if(req.queryParams().isEmpty()) {
							return g.toJson(ApartmentsDAO.getInstance().getMyActiveApartments(claims.getBody().getSubject()));
						} else {
							ApartmentSearch search = new ApartmentSearch(req);
							return g.toJson(ApartmentsDAO.getInstance().searchApartments(search, ApartmentsDAO.getInstance().getMyActiveApartments(claims.getBody().getSubject())));
						}
				    } else if(claims.getBody().get("Type").equals("Admin")) {
				    	//Admin
				    	if(req.queryParams().isEmpty()) {
							return g.toJson(ApartmentsDAO.getInstance().getActiveApartments());
						} else {
							ApartmentSearch search = new ApartmentSearch(req);
							return g.toJson(ApartmentsDAO.getInstance().searchApartments(search, ApartmentsDAO.getInstance().getActiveApartments()));
						}
				    }
				} catch (Exception e) {
					System.out.println("You dont have right permission");
				}
			}
			//Guest and unregistered
			if(req.queryParams().isEmpty()) {
				return g.toJson(ApartmentsDAO.getInstance().getActiveApartments());
			} else {
				ApartmentSearch search = new ApartmentSearch(req);
				return g.toJson(ApartmentsDAO.getInstance().searchApartments(search, ApartmentsDAO.getInstance().getActiveApartments()));
			}
		});
		
		get("/apartments/inactive", (req, res) -> {
			String auth = req.headers("Authorization");
			if ((auth != null) && (auth.contains("Bearer "))) {
				String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
				try {
				    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
				    if(claims.getBody().get("Type").equals("Host")) {
				    	return g.toJson(ApartmentsDAO.getInstance().getMyInactiveApartments(claims.getBody().getSubject()));
				    } else if(claims.getBody().get("Type").equals("Admin")) {
				    	return g.toJson(ApartmentsDAO.getInstance().getInactiveApartments());
				    }
				} catch (Exception e) {
					System.out.println("You dont have right permission");
				}
			}
			
			return "Error";
		});
		
		post("/apartments/update", (req, res) -> {
			String auth = req.headers("Authorization");
			if ((auth != null) && (auth.contains("Bearer "))) {
				String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
				try {
				    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
				    if(claims.getBody().get("Type").equals("Host")) {
				    	String payload = req.body();
						Apartment newData = g.fromJson(payload, Apartment.class);
						if(!newData.getHost().equals(claims.getBody().getSubject()))
							return "You dont have permission to update this apartment";
				    	if(ApartmentsDAO.getInstance().updateApartment(newData))
				    		return "Success";
				    	else
				    		return "Error";
				    } else if(claims.getBody().get("Type").equals("Admin")) {
				    	String payload = req.body();
						Apartment newData = g.fromJson(payload, Apartment.class);
						if(ApartmentsDAO.getInstance().updateApartment(newData))
				    		return "Success";
				    	else
				    		return "Error";
				    } else {
				    	return "You dont have right permission";
				    }
				} catch (Exception e) {
					System.out.println("You dont have right permission");
				}
			}
			return "Error";
		});
		
		post("/apartments/delete", (req, res) -> {
			String auth = req.headers("Authorization");
			if ((auth != null) && (auth.contains("Bearer "))) {
				String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
				try {
				    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
				    if(claims.getBody().get("Type").equals("Host")) {
				    	String payload = req.body();
						Apartment toDelete = g.fromJson(payload, Apartment.class);
						if(!toDelete.getHost().equals(claims.getBody().getSubject()))
							return "You dont have permission to delete this apartment";
				    	if(ApartmentsDAO.getInstance().deleteApartment(toDelete))
				    		return "Success";
				    	else
				    		return "Error";
				    } else if(claims.getBody().get("Type").equals("Admin")) {
				    	String payload = req.body();
						Apartment toDelete = g.fromJson(payload, Apartment.class);
						if(ApartmentsDAO.getInstance().deleteApartment(toDelete))
				    		return "Success";
				    	else
				    		return "Error";
				    } else {
				    	return "You dont have right permission";
				    }
				} catch (Exception e) {
					System.out.println("You dont have right permission");
				}
			}
			return "Error";
		});
		
		post("/apartments/new", (req, res) -> {
			String auth = req.headers("Authorization");
			if ((auth != null) && (auth.contains("Bearer "))) {
				String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
				try {
				    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
				    if(claims.getBody().get("Type").equals("Host")) {
				    	String payload = req.body();
						Apartment newApartment = g.fromJson(payload, Apartment.class);
						ApartmentsDAO.getInstance().addNewApartment(newApartment);
						return "Success";
				    } else {
				    	return "You dont have right permission";
				    }
				} catch (Exception e) {
					System.out.println("You dont have right permission");
				}
			}
			return "Error";
		});
		
		get("/amenities", (req, res) -> {
			String auth = req.headers("Authorization");
			if ((auth != null) && (auth.contains("Bearer "))) {
				String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
				try {
				    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
				    if(claims.getBody().get("Type").equals("Admin")) {
				    	return g.toJson(AmenitiesDAO.getInstance().getAmenities());
				    } else {
				    	return "You dont have right permission";
				    }
				} catch (Exception e) {
					System.out.println("You dont have right permission");
				}
			}
			return "Error";
		});
		
		post("/amenities/new", (req, res) -> {
			String auth = req.headers("Authorization");
			if ((auth != null) && (auth.contains("Bearer "))) {
				String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
				try {
				    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
				    if(claims.getBody().get("Type").equals("Admin")) {
				    	String payload = req.body();
						Amenity newAmenity = g.fromJson(payload, Amenity.class);
						AmenitiesDAO.getInstance().addNewAmenity(newAmenity);
						return "Success";
				    } else {
				    	return "You dont have right permission";
				    }
				} catch (Exception e) {
					System.out.println("You dont have right permission");
				}
			}
			return "Error";
		});
		
		post("/amenities/update", (req, res) -> {
			String auth = req.headers("Authorization");
			if ((auth != null) && (auth.contains("Bearer "))) {
				String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
				try {
				    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
				    if(claims.getBody().get("Type").equals("Admin")) {
				    	String payload = req.body();
						Amenity newData = g.fromJson(payload, Amenity.class);
						if(AmenitiesDAO.getInstance().updateAmenity(newData))
							return "Success";
						else
							return "Error";
				    } else {
				    	return "You dont have right permission";
				    }
				} catch (Exception e) {
					System.out.println("You dont have right permission");
				}
			}
			return "Error";
		});
		
		post("/amenities/delete", (req, res) -> {
			String auth = req.headers("Authorization");
			if ((auth != null) && (auth.contains("Bearer "))) {
				String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
				try {
				    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
				    if(claims.getBody().get("Type").equals("Admin")) {
				    	String payload = req.body();
						Amenity toDelete = g.fromJson(payload, Amenity.class);
						if(AmenitiesDAO.getInstance().deleteAmenity(toDelete)) {
							ApartmentsDAO.getInstance().deleteAmenity(toDelete);
							return "Success";
						} else
							return "Error";
				    } else {
				    	return "You dont have right permission";
				    }
				} catch (Exception e) {
					System.out.println("You dont have right permission");
				}
			}
			return "Error";
		});
		
		
		
	}
}
