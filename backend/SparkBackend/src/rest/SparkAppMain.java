package rest;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.port;
import static spark.Spark.staticFiles;

import java.io.File;

import com.google.gson.Gson;

import beans.users.Guest;
import dao.UsersDAO;

public class SparkAppMain {

	private static Gson g = new Gson();

	public static void main(String[] args) throws Exception {
		
		port(8080);

		staticFiles.externalLocation(new File("./WebContent").getCanonicalPath());
		
		get("/test", (req, res) -> {
			UsersDAO.getInstance();
			return "Works";
		});
		
		post("/register", (req, res) -> {
			
			String payload = req.body();
			Guest guest = g.fromJson(payload, Guest.class);
			
			boolean successful = UsersDAO.getInstance().saveNewGuest(guest);
			
			if(successful) {
				res.status(200);
				return "User successfully registered";
			}
			else {
				res.status(400);
				return "Username already exists";
			}
			
		});

	}
}
