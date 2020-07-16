package main;

import java.util.ArrayList;

public class User {
	private String username;
	private String password;
	private ArrayList<Tweet> tweetList = new ArrayList<>();
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public ArrayList<Tweet> getTweetList() {
		return tweetList;
	}
	public void addTweetList(Tweet newTweet) {
		this.tweetList.add(newTweet);
	}
	
	
}
