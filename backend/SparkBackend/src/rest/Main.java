package rest;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;
import static spark.Spark.port;
import static spark.Spark.staticFiles;

import java.io.File;
import java.util.Date;
import java.security.Key;

import io.jsonwebtoken.security.Keys;
import model.*;
import model.enums.ApartmentStatus;
import model.enums.UserType;
import requests.ApartmentSearch;
import requests.Login;
import requests.ReservationSearch;
import requests.UserSearch;

import com.google.gson.Gson;
import dao.AmenitiesDAO;
import dao.ApartmentsDAO;
import dao.CommentsDAO;
import dao.ReservationsDAO;
import dao.UsersDAO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Main {

	
	public static Gson g = Utils.createGson();

	public static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public static void main(String[] args) throws Exception {

		port(8080);

		staticFiles.externalLocation(new File("./WebContent").getCanonicalPath());
		
		post("/users/login", (req, res) -> {
			String payload = req.body();
			Login login = g.fromJson(payload, Login.class);

			User user = UsersDAO.getInstance().login(login);

			if (user == null) {
				res.status(400);
				return "Failed to login";
			} else {
				String jwt = Jwts.builder().setSubject(user.getUsername()).setIssuedAt(new Date()).signWith(key)
						.compact();
				String userType = user.getType().toString();
				return "{\"JWT\":\"" + jwt + "\",\"UserType\":\"" + userType + "\"}";
			}

		});

		post("/users", (req, res) -> {
			String username = Utils.authenticate(req);
			String payload = req.body();
			User user = g.fromJson(payload, User.class);
			if (username == null) {
				user.setType(UserType.Guest);
				boolean successful = UsersDAO.getInstance().addNewUser(user);
				if (successful) {
					res.status(200);
					return "User successfully registered";
				} else {
					res.status(400);
					return "Username already exists";
				}
			} else if(UsersDAO.getInstance().getUserType(username) == UserType.Admin) {
				user.setType(UserType.Host);
				boolean successful = UsersDAO.getInstance().addNewUser(user);
				if (successful) {
					res.status(200);
					return "User successfully registered";
				} else {
					res.status(400);
					return "Username already exists";
				}
			} else {
				res.status(403);
				return "You can't register another user";
			}

		});
		
		put("/users", (req, res) -> {
			String username = Utils.authenticate(req);
			if (username == null) {
				res.status(403);
				return "You must loggin first";
			} else {
				String payload = req.body();
				User newData = g.fromJson(payload, User.class);
				newData.setUsername(username);
				UsersDAO.getInstance().updateUserData(newData);
				return "Success";
			}
		});

		get("/users", (req, res) -> {
			String username = Utils.authenticate(req);
			if (username == null || UsersDAO.getInstance().getUserType(username) == UserType.Guest) {
				res.status(403);
				return "You can't view users";
			} else {
				if (UsersDAO.getInstance().getUserType(username) == UserType.Admin) {
					if (req.queryParams().isEmpty())
						return g.toJson(UsersDAO.getInstance().getAllUsers());
					else {
						UserSearch search = new UserSearch(req);
						return g.toJson(
								UsersDAO.getInstance().searchUsers(search, UsersDAO.getInstance().getAllUsers()));
					}

				} else {
					if (req.queryParams().isEmpty())
						return g.toJson(UsersDAO.getInstance().getMyGuests(username));
					else {
						UserSearch search = new UserSearch(req);
						return g.toJson(UsersDAO.getInstance().searchUsers(search,
								UsersDAO.getInstance().getMyGuests(username)));
					}
				}
			}
		});

		get("/users/myinfo", (req, res) -> {
			String username = Utils.authenticate(req);
			User user = UsersDAO.getInstance().getUser(username);
			if (username == null) {
				res.status(403);
				return "You are not logged in";
			} else {
				return g.toJson(user);
			}
		});
		
		get("/users/:user", (req, res) -> {
			String username = Utils.authenticate(req);
			String usernameToGet = req.params("user");
			User user = UsersDAO.getInstance().getUser(usernameToGet);
			return g.toJson(user);
		});
		
		put("/users/:user/block", (req, res) -> {
			String username = Utils.authenticate(req);
			String usernameToBlock = req.params("user");
			if (username == null || UsersDAO.getInstance().getUserType(username) != UserType.Admin) {
				res.status(403);
				return "You don't have permission to block users";
			} else {
				if(UsersDAO.getInstance().doesUserExists(usernameToBlock)) {
					if(UsersDAO.getInstance().getUserType(usernameToBlock) == UserType.Admin) {
						res.status(400);
						return "You can't block admins";
					} else {
						UsersDAO.getInstance().blockUser(usernameToBlock);
						return "Success";
					}
				} else {
					res.status(400);
					return "User with this username can't be found";
				}
			}
		});

		get("/apartments", (req, res) -> {
			String username = Utils.authenticate(req);
			if (username == null || UsersDAO.getInstance().getUserType(username) == UserType.Guest) {
				if (req.queryParams().isEmpty()) {
					return g.toJson(ApartmentsDAO.getInstance().getActiveApartments());
				} else {
					ApartmentSearch search = new ApartmentSearch(req);
					return g.toJson(ApartmentsDAO.getInstance().searchApartments(search,
							ApartmentsDAO.getInstance().getActiveApartments()));
				}
			} else if (UsersDAO.getInstance().getUserType(username) == UserType.Host) {
				if (req.queryParams().isEmpty()) {
					return g.toJson(ApartmentsDAO.getInstance().getMyApartments(username));
				} else {
					ApartmentSearch search = new ApartmentSearch(req);
					return g.toJson(ApartmentsDAO.getInstance().searchApartments(search,
							ApartmentsDAO.getInstance().getMyApartments(username)));
				}
			} else {
				if (req.queryParams().isEmpty()) {
					return g.toJson(ApartmentsDAO.getInstance().getApartments());
				} else {
					ApartmentSearch search = new ApartmentSearch(req);
					return g.toJson(ApartmentsDAO.getInstance().searchApartments(search,
							ApartmentsDAO.getInstance().getApartments()));
				}
			}

		});

		get("/apartments/:id", (req, res) -> {
			String username = Utils.authenticate(req);
			int id = Integer.parseInt(req.params("id"));
			Apartment apartment = ApartmentsDAO.getInstance().getApartment(id);
			if (username == null || UsersDAO.getInstance().getUserType(username) == UserType.Guest) {
				if (apartment.getStatus() == ApartmentStatus.Inactive) {
					res.status(403);
					return "You can't view inactive apartments";
				} else
					return g.toJson(apartment);
			} else if (UsersDAO.getInstance().getUserType(username) == UserType.Host) {
				if (!apartment.getHost().equals(username)) {
					res.status(403);
					return "You can't view this apartment";
				} else
					return g.toJson(apartment);
			} else {
				return g.toJson(apartment);
			}

		});

		get("/apartments/:id/comments", (req, res) -> {
			String username = Utils.authenticate(req);
			int apartmentId = Integer.parseInt(req.params("id"));
			if(username == null) {
				return g.toJson(CommentsDAO.getInstance().getVisibleComments(apartmentId));
			}else if (UsersDAO.getInstance().getUserType(username) == UserType.Guest) {
				return g.toJson(CommentsDAO.getInstance().getVisibleComments(apartmentId));
			} else if (UsersDAO.getInstance().getUserType(username) == UserType.Host) {
				if (!ApartmentsDAO.getInstance().getHost(apartmentId).getUsername().equals(username)) {
					res.status(403);
					return "You don't have permission to view comments for this apartment";
				}
				return g.toJson(CommentsDAO.getInstance().getComments(apartmentId));
			} else {
				return g.toJson(CommentsDAO.getInstance().getComments(apartmentId));
			}
		});

		put("/apartments/:id", (req, res) -> {
			String username = Utils.authenticate(req);
			int id = Integer.parseInt(req.params("id"));
			String payload = req.body();
			Apartment newData = g.fromJson(payload, Apartment.class);
			newData.setId(id);
			if (username == null || UsersDAO.getInstance().getUserType(username) == UserType.Guest) {
				res.status(403);
				return "You can't update apartments";
			} else if (UsersDAO.getInstance().getUserType(username) == UserType.Host) {
				if (!ApartmentsDAO.getInstance().getHost(id).getUsername().equals(username)) {
					res.status(403);
					return "You don't have permission to update this apartment";
				}
				if (ApartmentsDAO.getInstance().updateApartment(newData)) {
					ReservationsDAO.getInstance().updateReservations(id);
					return "Success";
				}
				else {
					res.status(400);
					return "Can't find apartment with this id";
				}

			} else {
				if (ApartmentsDAO.getInstance().updateApartment(newData)) {
					ReservationsDAO.getInstance().updateReservations(id);
					return "Success";
				}
				else {
					res.status(400);
					return "Can't find apartment with this id";
				}
			}

		});

		delete("/apartments/:id", (req, res) -> {
			String username = Utils.authenticate(req);
			int id = Integer.parseInt(req.params("id"));
			if (username == null || UsersDAO.getInstance().getUserType(username) == UserType.Guest) {
				res.status(403);
				return "You can't delete apartments";
			} else if (UsersDAO.getInstance().getUserType(username) == UserType.Host) {
				if (!ApartmentsDAO.getInstance().getHost(id).getUsername().equals(username)) {
					res.status(403);
					return "You don't have permission to delete this apartment";
				}
				if (ApartmentsDAO.getInstance().deleteApartment(id))
					return "Success";
				else {
					res.status(400);
					return "Can't find apartment with this id";
				}

			} else {
				if (ApartmentsDAO.getInstance().deleteApartment(id))
					return "Success";
				else {
					res.status(400);
					return "Can't find apartment with this id";
				}
			}
		});

		post("/apartments", (req, res) -> {
			String username = Utils.authenticate(req);
			if (UsersDAO.getInstance().getUserType(username) != UserType.Host) {
				res.status(403);
				return "You can't add new apartment";
			} else {
				String payload = req.body();
				Apartment newApartment = g.fromJson(payload, Apartment.class);
				newApartment.setHost(username);
				ApartmentsDAO.getInstance().addNewApartment(newApartment);
				return "Success";
			}
		});

		get("/apartments/:id/amenities", (req, res) -> {
			String username = Utils.authenticate(req);
			int id = Integer.parseInt(req.params("id"));
			Apartment apartment = ApartmentsDAO.getInstance().getApartment(id);
			if (username == null || UsersDAO.getInstance().getUserType(username) == UserType.Guest) {
				if (apartment.getStatus() == ApartmentStatus.Inactive) {
					res.status(403);
					return "You can't view inactive apartments";
				} else
					return g.toJson(AmenitiesDAO.getInstance().getAmenitiesForApartment(apartment));
			} else if (UsersDAO.getInstance().getUserType(username) == UserType.Host) {
				if (!apartment.getHost().equals(username)) {
					res.status(403);
					return "You can't view this apartment";
				} else
					return g.toJson(AmenitiesDAO.getInstance().getAmenitiesForApartment(apartment));
			} else {
				return g.toJson(AmenitiesDAO.getInstance().getAmenitiesForApartment(apartment));
			}
		});

		get("/amenities", (req, res) -> {
			return g.toJson(AmenitiesDAO.getInstance().getAmenities());
		});

		post("/amenities", (req, res) -> {
			String username = Utils.authenticate(req);
			if (username == null || UsersDAO.getInstance().getUserType(username) != UserType.Admin) {
				res.status(403);
				return "You can't add new amenity";
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
			if (username == null || UsersDAO.getInstance().getUserType(username) != UserType.Admin) {
				res.status(403);
				return "You can't update amenities";
			} else {
				String payload = req.body();
				Amenity newData = g.fromJson(payload, Amenity.class);
				if (AmenitiesDAO.getInstance().updateAmenityName(id, newData.getName()))
					return "Success";
				else {
					res.status(400);
					return "Can't find amenity with this id";
				}
			}
		});

		delete("/amenities/:id", (req, res) -> {
			String username = Utils.authenticate(req);
			int id = Integer.parseInt(req.params("id"));
			if (username == null || UsersDAO.getInstance().getUserType(username) != UserType.Admin) {
				res.status(403);
				return "You can't delete amenities";
			} else {
				if (AmenitiesDAO.getInstance().deleteAmenity(id)) {
					ApartmentsDAO.getInstance().deleteAmenity(id);
					return "Success";
				} else {
					res.status(400);
					return "Can't find amenity with this id";
				}
			}
		});

		get("/reservations", (req, res) -> {
			String username = Utils.authenticate(req);
			if (username == null) {
				res.status(403);
				return "You must login first";
			} else {
				if (UsersDAO.getInstance().getUserType(username) == UserType.Guest) {
					if (req.queryParams().isEmpty())
						return g.toJson(ReservationsDAO.getInstance().getGuestReservations(username));
					else {
						ReservationSearch search = new ReservationSearch(req);
						return g.toJson(ReservationsDAO.getInstance().searchReservations(search,
								ReservationsDAO.getInstance().getGuestReservations(username)));
					}
				} else if (UsersDAO.getInstance().getUserType(username) == UserType.Host) {
					if (req.queryParams().isEmpty())
						return g.toJson(ReservationsDAO.getInstance().getHostReservations(username));
					else {
						ReservationSearch search = new ReservationSearch(req);
						return g.toJson(ReservationsDAO.getInstance().searchReservations(search,
								ReservationsDAO.getInstance().getHostReservations(username)));
					}
				} else {
					if (req.queryParams().isEmpty())
						return g.toJson(ReservationsDAO.getInstance().getReservations());
					else {
						ReservationSearch search = new ReservationSearch(req);
						return g.toJson(ReservationsDAO.getInstance().searchReservations(search,
								ReservationsDAO.getInstance().getReservations()));
					}

				}
			}

		});

		put("/reservations/:id/cancel", (req, res) -> {
			String username = Utils.authenticate(req);
			int id = Integer.parseInt(req.params("id"));
			if (username == null || UsersDAO.getInstance().getUserType(username) != UserType.Guest) {
				res.status(403);
				return "You can't cancel reservation";
			} else {
				Reservation toCancle = ReservationsDAO.getInstance().getReservation(id);
				if (!toCancle.getGuest().equals(username)) {
					res.status(403);
					return "You can't cancle this reservation";
				}
				if (ReservationsDAO.getInstance().cancleReservation(id))
					return "Success";
				else {
					res.status(400);
					return "Reservation must be in status created or accepted";
				}
			}

		});

		put("/reservations/:id/accept", (req, res) -> {
			String username = Utils.authenticate(req);
			int id = Integer.parseInt(req.params("id"));
			if (username == null || UsersDAO.getInstance().getUserType(username) != UserType.Host) {
				res.status(403);
				return "You can't accept reservations";
			} else {
				Reservation toAccept = ReservationsDAO.getInstance().getReservation(id);
				if (!ApartmentsDAO.getInstance().getHost(toAccept.getApartment()).getUsername().equals(username)) {
					res.status(403);
					return "You don't have permission to accept this reservation";
				}
				if (ReservationsDAO.getInstance().acceptReservation(id))
					return "Success";
				else {
					res.status(400);
					return "Reservation must be in status created";
				}
			}
		});

		put("/reservations/:id/decline", (req, res) -> {
			String username = Utils.authenticate(req);
			int id = Integer.parseInt(req.params("id"));
			if (username == null || UsersDAO.getInstance().getUserType(username) != UserType.Host) {
				res.status(403);
				return "You can't decline reservations";
			} else {
				Reservation toDecline = ReservationsDAO.getInstance().getReservation(id);
				if (!ApartmentsDAO.getInstance().getHost(toDecline.getApartment()).getUsername().equals(username)) {
					res.status(403);
					return "You don't have permission to decline this reservation";
				}
				if (ReservationsDAO.getInstance().declineReservation(id))
					return "Success";
				else {
					res.status(400);
					return "Reservation must be in status created or accepted";
				}
			}
		});

		put("/reservations/:id/complete", (req, res) -> {
			String username = Utils.authenticate(req);
			int id = Integer.parseInt(req.params("id"));
			if (username == null || UsersDAO.getInstance().getUserType(username) != UserType.Host) {
				res.status(403);
				return "You can't decline reservations";
			} else {
				Reservation toComplete = ReservationsDAO.getInstance().getReservation(id);
				if (!ApartmentsDAO.getInstance().getHost(toComplete.getApartment()).getUsername().equals(username)) {
					res.status(403);
					return "You don't have permission to complete this reservation";
				}
				if (ReservationsDAO.getInstance().completeReservation(id))
					return "Success";
				else {
					res.status(400);
					return "Reservation must be in status accepted";
				}
			}
		});

		post("/reservations", (req, res) -> {
			String username = Utils.authenticate(req);
			if (username == null || UsersDAO.getInstance().getUserType(username) != UserType.Guest) {
				res.status(403);
				return "You can't make new reservations";
			} else {
				String payload = req.body();
				Reservation newReservation = g.fromJson(payload, Reservation.class);
				newReservation.setGuest(username);
				if (ReservationsDAO.getInstance().addNewReservation(newReservation))
					return "Success";
				else {
					res.status(400);
					return "You can't make reservation for selected dates";
				}
			}
		});

		post("/comments", (req, res) -> {
			String username = Utils.authenticate(req);
			if (username == null || UsersDAO.getInstance().getUserType(username) != UserType.Guest) {
				res.status(403);
				return "You can't make new comment";
			} else {
				String payload = req.body();
				Comment newComment = g.fromJson(payload, Comment.class);
				newComment.setAuthor(username);
				if (!ReservationsDAO.getInstance().guestHasCompletedReservation(newComment.getAuthor(),
						newComment.getApartment())) {
					res.status(403);
					return "You don't have permission to leave a comment on this apartment";
				}
				CommentsDAO.getInstance().addNewComment(newComment);
				return "Success";
			}

		});

		put("/comments/:id/visible", (req, res) -> {
			String username = Utils.authenticate(req);
			int id = Integer.parseInt(req.params("id"));
			if (username == null || UsersDAO.getInstance().getUserType(username) != UserType.Host) {
				res.status(403);
				return "You can't make comments visible to guests";
			} else {
				Comment comment = CommentsDAO.getInstance().getComment(id);
				if (!ApartmentsDAO.getInstance().getHost(comment.getApartment()).getUsername().equals(username)) {
					res.status(403);
					return "You don't have permission to make this comment visible to guests";
				}
				CommentsDAO.getInstance().makeCommentVisible(id);
				return "Success";
			}
		});

		put("/comments/:id/hide", (req, res) -> {
			String username = Utils.authenticate(req);
			int id = Integer.parseInt(req.params("id"));
			if (username == null || UsersDAO.getInstance().getUserType(username) != UserType.Host) {
				res.status(403);
				return "You can't hide comments";
			} else {
				Comment comment = CommentsDAO.getInstance().getComment(id);
				if (!ApartmentsDAO.getInstance().getHost(comment.getApartment()).getUsername().equals(username)) {
					res.status(403);
					return "You don't have permission to hide this comment";
				}
				CommentsDAO.getInstance().hideComment(id);
				return "Success";
			}
		});

		get("/comments/:id", (req, res) -> {
			String username = Utils.authenticate(req);
			int id = Integer.parseInt(req.params("id"));
			Comment comment = CommentsDAO.getInstance().getComment(id);
			if (username == null) {
				res.status(403);
				return "You must login first";
			} else {
				if (UsersDAO.getInstance().getUserType(username) == UserType.Guest) {
					if (comment.isVisibleToGuests())
						return g.toJson(comment);
					else {
						res.status(403);
						return "You can't see this comment";
					}
				} else if (UsersDAO.getInstance().getUserType(username) == UserType.Host) {
					if (!ApartmentsDAO.getInstance().getHost(comment.getApartment()).getUsername().equals(username)) {
						res.status(403);
						return "You don't have permission to view comments for this apartment";
					}
					else
						return g.toJson(comment);
				} else {
					return g.toJson(comment);
				}
			}
		});

	}
}
