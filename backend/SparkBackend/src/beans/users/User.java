package beans.users;

public class User {
	
	enum Gender {
		Male,
		Female
	}
	
	private String username;
	private String password;
	private String name;
	private String lastname;
	private Gender gender;
	
	public User(String username, String password, String name, String lastname, Gender gender) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.lastname = lastname;
		this.gender = gender;
	}

}
