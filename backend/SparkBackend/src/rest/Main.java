package rest;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;
import static spark.Spark.before;
import static spark.Spark.halt;
import static spark.Spark.port;
import static spark.Spark.staticFiles;

import java.io.File;
import java.util.List;
import java.util.Date;
import java.security.Key;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import io.jsonwebtoken.security.Keys;
import model.*;
import model.enums.ApartmentStatus;
import model.enums.ReservationStatus;
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
import dao.CommentsDAO;
import dao.ReservationsDAO;
import dao.UsersDAO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Main {

	public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	public static JsonSerializer<Date> ser = (src, typeOfSrc, context) -> src == null ? null : new JsonPrimitive(dateFormat.format(src));
	public static JsonDeserializer<Date> deser = (jSon, typeOfT, context) -> {
		try {
			return jSon == null ? null : dateFormat.parse(jSon.getAsString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	};
	public static Gson g = new GsonBuilder()
			   .registerTypeAdapter(Date.class, ser)
			   .registerTypeAdapter(Date.class, deser).create();
	
	public static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public static void main(String[] args) throws Exception {
		
		port(8080);
		
		staticFiles.externalLocation(new File("./WebContent").getCanonicalPath());
		
		get("/test", (req, res) -> {
			
			return g.toJson(AmenitiesDAO.getInstance().getAmenities());
		});
		
		post("/users/login", (req, res) -> {
			String payload = req.body();
			Login login = g.fromJson(payload, Login.class);
			
			User user = UsersDAO.getInstance().login(login);
			
			if(user == null) {
				res.status(400);
				return "Failed to login";
			} else {
				return Jwts.builder().setSubject(user.getUsername()).setIssuedAt(new Date()).signWith(key).compact();
			}
			
		});
		
		post("/users", (req, res) -> {
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
		
		put("/users", (req, res) -> {
			String username = Utils.authenticate(req);
			if(username == null)
				return "You dont have right permission";
			else {
				String payload = req.body();
				User newData = g.fromJson(payload, User.class);
				newData.setUsername(username);
				UsersDAO.getInstance().updateUserData(newData);
				return "Success";
			}
		});
		
		get("/users", (req, res) -> {
			String username = Utils.authenticate(req);
			if(username == null || UsersDAO.getInstance().getUserType(username) == UserType.Guest) {
				res.status(403);
				return "You cant view users";
			} else {
				if(UsersDAO.getInstance().getUserType(username) == UserType.Admin) {
					
					String search = req.queryParams("search");
					if(search == null)
						return g.toJson(UsersDAO.getInstance().getAllUsers());
					else
						return g.toJson(UsersDAO.getInstance().searchUsers(search));
					
				} else {
					
					String search = req.queryParams("search");
					if(search == null)
						return g.toJson(UsersDAO.getInstance().getMyGuests(username));
					else
						return g.toJson(UsersDAO.getInstance().searchMyGuests(username, search));
					
				}
			}
		});
		
		get("/apartments", (req, res) -> {
			String username = Utils.authenticate(req);
			if(username == null || UsersDAO.getInstance().getUserType(username) == UserType.Guest) {
				if(req.queryParams().isEmpty()) {
					return g.toJson(ApartmentsDAO.getInstance().getActiveApartments());
				} else {
					ApartmentSearch search = new ApartmentSearch(req);
					return g.toJson(ApartmentsDAO.getInstance().searchApartments(search, ApartmentsDAO.getInstance().getActiveApartments()));
				}
			} else if(UsersDAO.getInstance().getUserType(username) == UserType.Host){
				if(req.queryParams().isEmpty()) {
					return g.toJson(ApartmentsDAO.getInstance().getMyApartments(username));
				} else {
					ApartmentSearch search = new ApartmentSearch(req);
					return g.toJson(ApartmentsDAO.getInstance().searchApartments(search, ApartmentsDAO.getInstance().getMyApartments(username)));
				}
			} else {
				if(req.queryParams().isEmpty()) {
					return g.toJson(ApartmentsDAO.getInstance().getApartments());
				} else {
					ApartmentSearch search = new ApartmentSearch(req);
					return g.toJson(ApartmentsDAO.getInstance().searchApartments(search, ApartmentsDAO.getInstance().getApartments()));
				}
			}
			
		});
		
		get("/apartments/:id", (req, res) -> {
			String username = Utils.authenticate(req);
			int id = Integer.parseInt(req.params("id"));
			Apartment apartment = ApartmentsDAO.getInstance().getApartment(id);
			if(username == null || UsersDAO.getInstance().getUserType(username) == UserType.Guest) {
				if(apartment.getStatus() == ApartmentStatus.Inactive)
					return "You can't view inactive apartments";
				else
					return g.toJson(apartment);
			} else if(UsersDAO.getInstance().getUserType(username) == UserType.Host){
				if(!apartment.getHost().equals(username))
					return "You can't view this apartment";
				else
					return g.toJson(apartment);
			} else {
				return g.toJson(apartment);
			}
			
		});
		
		put("/apartments/:id", (req, res) -> {
			String username = Utils.authenticate(req);
			int id = Integer.parseInt(req.params("id"));
			if(username == null || UsersDAO.getInstance().getUserType(username) == UserType.Guest) {
				res.status(403);
				return "You cant update apartments";
			} else if(UsersDAO.getInstance().getUserType(username) == UserType.Host){
				if(!ApartmentsDAO.getInstance().getHost(id).getUsername().equals(username)) {
					res.status(403);
					return "You dont have permission to update this apartment";
				}
				String payload = req.body();
				Apartment newData = g.fromJson(payload, Apartment.class);
		    	if(ApartmentsDAO.getInstance().updateApartment(newData))
		    		return "Success";
		    	else
		    		return "Error";
		    	
			} else {
				String payload = req.body();
				Apartment newData = g.fromJson(payload, Apartment.class);
				if(ApartmentsDAO.getInstance().updateApartment(newData))
		    		return "Success";
		    	else
		    		return "Error";
			}

		});
		
		delete("/apartments/:id", (req, res) -> {
			String username = Utils.authenticate(req);
			int id = Integer.parseInt(req.params("id"));
			if(username == null || UsersDAO.getInstance().getUserType(username) == UserType.Guest) {
				res.status(403);
				return "You cant delete apartments";
			} else if(UsersDAO.getInstance().getUserType(username) == UserType.Host){
				if(!ApartmentsDAO.getInstance().getHost(id).getUsername().equals(username)) {
					res.status(403);
					return "You dont have permission to delete this apartment";
				}
				if(ApartmentsDAO.getInstance().deleteApartment(id))
		    		return "Success";
		    	else
		    		return "Error";
		    	
			} else {
				if(ApartmentsDAO.getInstance().deleteApartment(id))
		    		return "Success";
		    	else
		    		return "Error";
			}
		});
		
		post("/apartments", (req, res) -> {
			String username = Utils.authenticate(req);
			if(UsersDAO.getInstance().getUserType(username) != UserType.Host) {
				res.status(403);
				return "You cant add new apartment";
			} else {
				String payload = req.body();
				Apartment newApartment = g.fromJson(payload, Apartment.class);
				newApartment.setHost(username);
				ApartmentsDAO.getInstance().addNewApartment(newApartment);
				return "Success";
			}
		});
		
		get("/amenities", (req, res) -> {
			String username = Utils.authenticate(req);
			if(username == null || UsersDAO.getInstance().getUserType(username) != UserType.Admin) {
				res.status(403);
				return "You cant view amenities";
			} else {
				return g.toJson(AmenitiesDAO.getInstance().getAmenities());
			}
			
		});
		
		post("/amenities", (req, res) -> {
			String username = Utils.authenticate(req);
			if(username == null || UsersDAO.getInstance().getUserType(username) != UserType.Admin) {
				res.status(403);
				return "You cant add new amenity";
			} else {
				String payload = req.body();
				Amenity newAmenity = g.fromJson(payload, Amenity.class);
				AmenitiesDAO.getInstance().addNewAmenity(newAmenity);
				return "Success";
			}
		});
		
		put("/amenities/:id", (req, res) -> {
			String username = Utils.authenticate(req);
			int id = Integer.parseInt(req.params("id"));
			if(username == null || UsersDAO.getInstance().getUserType(username) != UserType.Admin) {
				res.status(403);
				return "You cant update amenities";
			} else {
				String payload = req.body();
				Amenity newData = g.fromJson(payload, Amenity.class);
				if(AmenitiesDAO.getInstance().updateAmenityName(id, newData.getName()))
					return "Success";
				else
					return "Error";
			}
		});
		
		delete("/amenities/:id", (req, res) -> {
			String username = Utils.authenticate(req);
			int id = Integer.parseInt(req.params("id"));
			if(username == null || UsersDAO.getInstance().getUserType(username) != UserType.Admin) {
				res.status(403);
				return "You cant delete amenities";
			} else {
				if(AmenitiesDAO.getInstance().deleteAmenity(id)) {
					ApartmentsDAO.getInstance().deleteAmenity(id);
					return "Success";
				} else
					return "Error";
			}
		});
		
		get("/reservations", (req, res) -> {
			String username = Utils.authenticate(req);
			if(username == null) {
				res.status(401);
				return "You must login first";
			} else {
				if(UsersDAO.getInstance().getUserType(username) == UserType.Guest) {
			    	return g.toJson(ReservationsDAO.getInstance().getGuestReservations(username));
			    } else if(UsersDAO.getInstance().getUserType(username) == UserType.Host) {
			    	return g.toJson(ReservationsDAO.getInstance().getHostReservations(username));
			    } else {
			    	return g.toJson(ReservationsDAO.getInstance().getReservations());
			    }
			}
			
		});
		
		put("/reservations/:id/cancel", (req, res) -> {
			String username = Utils.authenticate(req);
			int id = Integer.parseInt(req.params("id"));
			if(username == null || UsersDAO.getInstance().getUserType(username) != UserType.Guest) {
				res.status(403);
				return "You cant cancel reservation";
			} else {
				Reservation toCancle = ReservationsDAO.getInstance().getReservation(id);
				if(!toCancle.getGuest().equals(username))
		    		return "You can't cancle this reservation";
		    	if(ReservationsDAO.getInstance().cancleReservation(id))
		    		return "Success";
		    	else
		    		return "Error";
			}
			
		});
		
		put("/reservations/:id/accept", (req, res) -> {
			String username = Utils.authenticate(req);
			int id = Integer.parseInt(req.params("id"));
			if(username == null || UsersDAO.getInstance().getUserType(username) != UserType.Host) {
				res.status(403);
				return "You cant accept reservations";
			} else {
				Reservation toAccept = ReservationsDAO.getInstance().getReservation(id);
				if(!ApartmentsDAO.getInstance().getHost(toAccept.getApartment()).getUsername().equals(username))
		    		return "You dont have permission to accept this reservation";
		    	if(ReservationsDAO.getInstance().acceptReservation(id))
		    		return "Success";
		    	else
		    		return "Error";
			}
		});
		
		put("/reservations/:id/decline", (req, res) -> {
			String username = Utils.authenticate(req);
			int id = Integer.parseInt(req.params("id"));
			if(username == null || UsersDAO.getInstance().getUserType(username) != UserType.Host) {
				res.status(403);
				return "You cant decline reservations";
			} else {
				Reservation toDecline = ReservationsDAO.getInstance().getReservation(id);
				if(!ApartmentsDAO.getInstance().getHost(toDecline.getApartment()).getUsername().equals(username))
					return "You dont have permission to decline this reservation";
				if(ReservationsDAO.getInstance().declineReservation(id))
					return "Success";
				else
					return "Error";
			}
		});
		
		put("/reservations/:id/complete", (req, res) -> {
			String username = Utils.authenticate(req);
			int id = Integer.parseInt(req.params("id"));
			if(username == null || UsersDAO.getInstance().getUserType(username) != UserType.Host) {
				res.status(403);
				return "You cant decline reservations";
			} else {
				Reservation toComplete = ReservationsDAO.getInstance().getReservation(id);
				if(!ApartmentsDAO.getInstance().getHost(toComplete.getApartment()).getUsername().equals(username))
		    		return "You dont have permission to complete this reservation";
		    	if(ReservationsDAO.getInstance().completeReservation(id))
		    		return "Success";
		    	else
		    		return "Error";
			}
		});
		
		post("/reservations", (req, res) -> {
			String username = Utils.authenticate(req);
			if(username == null || UsersDAO.getInstance().getUserType(username) != UserType.Guest) {
				res.status(403);
				return "You cant make new reservations";
			} else {
				String payload = req.body();
				Reservation newReservation = g.fromJson(payload, Reservation.class);
				newReservation.setGuest(username);
				ReservationsDAO.getInstance().addNewReservation(newReservation);
				return "Success";
			}
		});
		
		post("/comments", (req, res) -> {
			String username = Utils.authenticate(req);
			if(username == null || UsersDAO.getInstance().getUserType(username) != UserType.Guest) {
				res.status(403);
				return "You cant make new comment";
			} else {
				String payload = req.body();
				Comment newComment = g.fromJson(payload, Comment.class);
				newComment.setAuthor(username);
				if(!ReservationsDAO.getInstance().guestHasCompletedReservation(newComment.getAuthor(), newComment.getApartment()))
		    		return "You dont have permission to leave a comment on this apartment";
		    	CommentsDAO.getInstance().addNewComment(newComment);
		    	return "Success";
			}
			
		});
		
		put("/comments/:id/visible", (req, res) -> {
			String username = Utils.authenticate(req);
			int id = Integer.parseInt(req.params("id"));
			if(username == null || UsersDAO.getInstance().getUserType(username) != UserType.Host) {
				res.status(403);
				return "You cant make comments visible to guests";
			} else {
		    	Comment comment = CommentsDAO.getInstance().getComment(id); 
		    	if(!ApartmentsDAO.getInstance().getHost(comment.getApartment()).getUsername().equals(username))
		    		return "You dont have permission to make this comment visible to guests";
		    	CommentsDAO.getInstance().makeCommentVisible(id);
		    	return "Success";
			}
		});
		
		get("/comments/:id", (req, res) -> {
			String username = Utils.authenticate(req);
			int apartmentId = Integer.parseInt(req.params("id"));
			if(username == null) {
				res.status(401);
				return "You must login first";
			} else {
				if(UsersDAO.getInstance().getUserType(username) == UserType.Guest) {
					return g.toJson(CommentsDAO.getInstance().getVisibleComments(apartmentId));
			    } else if(UsersDAO.getInstance().getUserType(username) == UserType.Host) {
			    	if(!ApartmentsDAO.getInstance().getHost(apartmentId).getUsername().equals(username))
			    		return "You dont have permission to view comments for this apartment";
			    	return g.toJson(CommentsDAO.getInstance().getComments(apartmentId));
			    } else {
			    	return g.toJson(CommentsDAO.getInstance().getComments(apartmentId));
			    }
			}
		});
		
	}
}
