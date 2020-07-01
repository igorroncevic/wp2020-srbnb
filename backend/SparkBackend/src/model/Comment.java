package model;

public class Comment {
	
	private int id;
	private String author;
	private int apartment;
	private String content;
	private double rating;
	
	public Comment(String author, int apartment, String content, double rating) {
		super();
		this.author = author;
		this.apartment = apartment;
		this.content = content;
		this.rating = rating;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getApartment() {
		return apartment;
	}

	public void setApartment(int apartment) {
		this.apartment = apartment;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}	

	
}
