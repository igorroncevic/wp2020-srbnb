package beans;

import beans.users.Guest;

public class Comment {
	
	private Guest author;
	private Apartment apartment;
	private String content;
	private double rating;
	
	public Comment(Guest author, Apartment apartment, String content, double rating) {
		super();
		this.author = author;
		this.apartment = apartment;
		this.content = content;
		this.rating = rating;
	}	

}
