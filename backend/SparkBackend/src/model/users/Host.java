package model.users;

import java.util.List;

import model.enums.Gender;

public class Host extends User {

	public Host(String username, String password, String name, String lastname, Gender gender) {
		super(username, password, name, lastname, gender);
	}

}
