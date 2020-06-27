package model.users;

import java.util.List;

import model.enums.Gender;

public class Guest extends User {

	public Guest(String username, String password, String name, String lastname, Gender gender) {
		super(username, password, name, lastname, gender);
	}

}
