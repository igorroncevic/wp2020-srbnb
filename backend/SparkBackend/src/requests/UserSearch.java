package requests;

import model.enums.Gender;
import model.enums.UserType;
import spark.Request;

public class UserSearch {
	
	private UserType type = null;
	private Gender gender = null;
	private String username = null;
	
	public UserSearch(Request req) {
		String type = req.queryParams("type");
		if(type != null && (type.equals("Guest") || type.equals("Admin") || type.equals("Host")))
			this.type = UserType.valueOf(type);
		
		String gender = req.queryParams("gender");
		if(gender != null && (gender.equals("Male") || gender.equals("Female")))
			this.gender = Gender.valueOf(gender);
		
		this.username = req.queryParams("username");
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
