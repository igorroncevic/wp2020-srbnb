package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

import model.Apartment;
import model.Comment;
import rest.Main;

public class CommentsDAO {
	
	private static CommentsDAO instance;
	
	private String COMMENTS_FILE_PATH = "data/comments.json";
	
	private Map<Integer, Comment> comments = new HashMap<Integer, Comment>();
	
	private CommentsDAO() {
		loadData();
	}
	
	public static CommentsDAO getInstance() {
		if(instance == null)
			instance = new CommentsDAO();
		
		return instance;
	}
	
	private void loadData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(COMMENTS_FILE_PATH));
			List<Comment> data = Main.g.fromJson(br, new TypeToken<List<Comment>>(){}.getType());
			for(Comment comment : data) {
				this.comments.put(comment.getId(), comment);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void saveData() {
		String json = Main.g.toJson(comments.values());
		
		try {
			FileWriter writer = new FileWriter(COMMENTS_FILE_PATH);
			writer.write(json);
			writer.close();
			  
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean addNewComment(Comment newComment) {
		newComment.setId(comments.size());
		comments.put(newComment.getId(), newComment);
		saveData();
		return true;
	}
	
	public Comment getComment(int id) {
		return comments.get(id);
	}
	
	public void makeCommentVisible(int id) {
		comments.get(id).setVisibleToGuests(true);
		saveData();
	}
	
	public void hideComment(int id) {
		comments.get(id).setVisibleToGuests(false);
		saveData();
	}
	
	public List<Comment> getVisibleComments(int apartment) {
		List<Comment> visibleComments = new ArrayList<Comment>();
		for(Comment comment : comments.values()) {
			if(comment.getApartment() == apartment && comment.isVisibleToGuests())
				visibleComments.add(comment);
		}
		return visibleComments;
	}
	
	public List<Comment> getComments(int apartment) {
		List<Comment> comms = new ArrayList<Comment>();
		for(Comment comment : comments.values()) {
			if(comment.getApartment() == apartment)
				comms.add(comment);
		}
		return comms;
	} 

}
