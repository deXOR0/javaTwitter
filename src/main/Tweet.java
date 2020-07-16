package main;

import java.util.ArrayList;

public class Tweet {
	private String tweet;
	private ArrayList<Reply> replyList = new ArrayList<>();
	
	public String getTweet() {
		return tweet;
	}
	
	public void setTweet(String tweet) {
		this.tweet = tweet;
	}
	
	public ArrayList<Reply> getReplyList() {
		return replyList;
	}
	
	public void addReplyList(Reply newReply) {
		this.replyList.add(newReply);
	}

	public Tweet(String tweet) {
		super();
		this.tweet = tweet;
	}
	
	
}
