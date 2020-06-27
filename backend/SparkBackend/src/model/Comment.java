package model;

public class Comment {
	
	private String author;
	private Apartment apartment;
	private String content;
	private double rating;
	
	public Comment(String author, Apartment apartment, String content, double rating) {
		super();
		this.author = author;
		this.apartment = apartment;
		this.content = content;
		this.rating = rating;
	}	

}
