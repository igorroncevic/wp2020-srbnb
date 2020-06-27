package model.users;

import model.enums.Gender;

public class Admin extends User {

	public Admin(String username, String password, String name, String lastname, Gender gender) {
		super(username, password, name, lastname, gender);
	}

}
