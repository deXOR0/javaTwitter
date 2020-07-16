package main;

public class Reply {
	
	private String user;
	private String reply;

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public Reply(String user, String reply) {
		super();
		this.user = user;
		this.reply = reply;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	
}
