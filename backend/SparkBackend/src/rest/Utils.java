package rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import spark.Request;

public class Utils {
	
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	public static JsonSerializer<Date> ser = (src, typeOfSrc, context) -> src == null ? null
			: new JsonPrimitive(dateFormat.format(src));
	public static JsonDeserializer<Date> deser = (jSon, typeOfT, context) -> {
		try {
			return jSon == null ? null : dateFormat.parse(jSon.getAsString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	};

	public static String authenticate(Request req) {
		String auth = req.headers("Authorization");
		if ((auth != null) && (auth.contains("Bearer "))) {
			String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
			try {
			    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(Main.key).build().parseClaimsJws(jwt);
				return claims.getBody().getSubject();
			} catch (Exception e) {
				System.out.println("Failed to authenticate");
			}
		}
		return null;
	}
	
	public static Gson createGson() {
		return new GsonBuilder().registerTypeAdapter(Date.class, ser).registerTypeAdapter(Date.class, deser).create();
	}

}
