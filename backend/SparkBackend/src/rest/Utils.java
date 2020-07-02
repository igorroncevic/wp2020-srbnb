package rest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import spark.Request;

public class Utils {

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

}
