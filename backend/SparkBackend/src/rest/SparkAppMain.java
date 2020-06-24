package rest;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.before;
import static spark.Spark.halt;
import static spark.Spark.port;
import static spark.Spark.staticFiles;

import java.io.File;

import org.eclipse.jetty.security.UserAuthentication;

import com.google.gson.Gson;

import beans.users.*;
import dao.UsersDAO;
import spark.Session;

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
			
			boolean successful = UsersDAO.getInstance().addNewGuest(guest);
			
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
				Session ss = req.session(true);
				ss.attribute("user", user);
				return "Successful login";
			}
			
		});
	
	}
}
