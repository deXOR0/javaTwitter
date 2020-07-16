package main;

import java.util.*;

public class Main {

	Scanner scan = new Scanner(System.in);
	ArrayList<User> users = new ArrayList<>();
	User currentUser;
	
	void clear() {
		for (int i = 0; i < 20; i++) {
			System.out.println();
		}
	}
	
	void replyTweet(String type) {
		User thisUser = currentUser;
		String username;
		clear();
		if (type.equals("all")) {
			boolean validUser = false, userFound = false;
			do {
				System.out.print("Input username to reply to [x to cancel]: ");
				username = scan.nextLine();
				if (username.equals("x")) {
					userHome();
				}
				for (User checkUser : users) {
					if (checkUser.getUsername().equals(username)){
						userFound = true;
						if (checkUser.getTweetList().isEmpty()) {
							System.out.println("This user hasn't tweeted anything");
							scan.nextLine();
							replyTweet("all");
						}
						else {
							validUser = true;
							thisUser = checkUser;
						}
					}
				}
				if (!userFound) {
					System.out.println("User was not found!");
				}
			}while (!validUser && !userFound);
		}
		
		int choose = -1;
		do {
			clear();
			viewUserTweet(thisUser);
			System.out.print("Choose tweet you want to reply [1 - " + (thisUser.getTweetList().size()) + " | 0 to cancel] : ");
			try {
				choose = scan.nextInt();
			} catch (Exception e) {
				choose = -1;
			}
			scan.nextLine();
			if (choose == 0) {
				userHome();
			}
		}while (choose < 1 || choose > thisUser.getTweetList().size());
		choose--;
		String newReply = "";
		do {
			clear();
			System.out.println(thisUser.getTweetList().get(choose).getTweet());
			System.out.println();
			System.out.print("Type your reply [x to cancel]: ");
			newReply = scan.nextLine();
			if (newReply.equals("x")) {
				tweetMenu(type);
			}
		}while (newReply.length() <= 0);
		thisUser.getTweetList().get(choose).addReplyList(new Reply(currentUser.getUsername(), newReply));
		System.out.println("Posting your reply...");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tweetMenu(type);
	}
	
	void deleteTweet() {
		clear();
		if (currentUser.getTweetList().isEmpty()) {
			System.out.println("You haven't tweeted anything");
			scan.nextLine();
			userHome();
		}
		else {
			int choose = -1;
			do {
				System.out.println("You can only delete your own tweet");
				System.out.println();
				viewUserTweet(currentUser);
				System.out.print("Choose which tweet you want to delete [1 - " + currentUser.getTweetList().size() + "] : ");
				try {
					choose = scan.nextInt();
				} catch (Exception e) {
					choose = -1;
				}
			}while (choose < 1|| choose > currentUser.getTweetList().size());
			choose--;
			String confirm = "0";
			do {
				clear();
				System.out.println(currentUser.getTweetList().get(choose).getTweet());
				System.out.print("Are you sure you want to delete this tweet[Y|N]? ");
				confirm = scan.nextLine();
			}while (!confirm.equalsIgnoreCase("y") && !confirm.equalsIgnoreCase("n"));
			if (confirm.equalsIgnoreCase("y")) {
				currentUser.getTweetList().remove(choose);
			}
			else {
				System.out.println("You didn't delete any tweet");
				scan.nextLine();
			}
		}
		userHome();
	}
	
	void deleteReply() {
		clear();
		String username;
		User userChosen = currentUser;
		boolean validChoice = false;
		do {
			viewAllTweet();
			System.out.println("You can only delete your own replies or replies on your tweets");
			System.out.println();
			System.out.print("Choose which user's tweet's reply you want to delete [Username | x to cancel]: ");
			username = scan.nextLine();
			if (username.equals(currentUser.getUsername())){
				validChoice = true;
			}
			else if (username.equals("x")) {
				userHome();
			}
			else {
				for (User thisUser : users) {
					if (thisUser.getUsername().equals(username) && !thisUser.getTweetList().isEmpty()) {
						userChosen = thisUser;
						validChoice = true;
						break;
					}
					else if (thisUser.getTweetList().isEmpty()) {
						System.out.println("This user hasn't tweeted anything yet!");
						userChosen = thisUser;
						break;
					}
				}
			}
			if (!validChoice && userChosen == currentUser) {
				System.out.println("Username was not found!");
				scan.nextLine();
			}
		}while (!validChoice);
		int choose = -1;
		do {
			viewUserTweet(userChosen);
			System.out.print("Input which tweet you want to access [0 to cancel]: ");
			try {
				choose = scan.nextInt();
			} catch (Exception e) {
				choose = -1;
			}
			scan.nextLine();
			if (choose == 0) {
				userHome();
			}
		}while (choose < 0 || choose > userChosen.getTweetList().size());
		choose--;
		int chooseReply = -1;
		if (userChosen.getTweetList().get(choose).getReplyList().isEmpty()) {
			System.out.println("This tweet doesn't have any reply yet");
			scan.nextLine();
			deleteReply();
		}
		do {
			int count = 1;
			clear();
			System.out.println("Replies");
			System.out.println("=======");
			for (Reply thisReply : userChosen.getTweetList().get(choose).getReplyList()) {
				System.out.println((count++) + ". " + "User : " + thisReply.getUser());
				System.out.println("  " + thisReply.getReply());
			}
			System.out.print("Choose which reply you want to delete [0 to cancel]: ");
			try {
				chooseReply = scan.nextInt();
			} catch (Exception e) {
				chooseReply = -1;
			}
			scan.nextLine();
			if (chooseReply == 0) {
				userHome();
			}
		}while (chooseReply < 1 || chooseReply > userChosen.getTweetList().get(choose).getReplyList().size());
		chooseReply--;
		String confirm = "";
		if (userChosen.getUsername().equals(currentUser.getUsername())) {
			do {
				System.out.print("Are you sure you want to delete this reply[Y/N]? ");
				confirm = scan.nextLine();
			}while (!confirm.equalsIgnoreCase("y") && !confirm.equalsIgnoreCase("n"));
			if (confirm.equalsIgnoreCase("y")) {
				userChosen.getTweetList().get(choose).getReplyList().remove(chooseReply);
				System.out.println("Reply has been deleted!");
			}
			else {
				System.out.println("You did not delete anything");
			}
		}
		else if (!userChosen.getTweetList().get(choose).getReplyList().get(chooseReply).getUser().equals(currentUser.getUsername())) {
			System.out.println("You are now allowed to delete this reply as this is not your tweet");
			scan.nextLine();
		}
		else {
			do {
				System.out.print("Are you sure you want to delete this reply[Y/N]? ");
				confirm = scan.nextLine();
			}while (!confirm.equalsIgnoreCase("y") && !confirm.equalsIgnoreCase("n"));
			if (confirm.equalsIgnoreCase("y")) {
				userChosen.getTweetList().get(choose).getReplyList().remove(chooseReply);
				System.out.println("Reply has been deleted!");
			}
			else {
				System.out.println("You did not delete anything");
			}
		}
		userHome();
	}
	
	void tweetMenu(String type) {
		int choose = -1;
		do {
			if (type.equals("user")) {
				viewUserTweet(currentUser);
			}
			else {
				viewAllTweet();
			}
			System.out.println("==========================");
			System.out.println("1. Reply Tweet");
			System.out.println("2. Delete Tweet");
			System.out.println("3. Delete Reply");
			System.out.println("4. Back");
			System.out.print(">> ");
			try {
				choose = scan.nextInt();
			} catch (Exception e) {
				choose = -1;
			}
			scan.nextLine();
			switch (choose) {
			case 1:
				replyTweet(type);
				break;
			case 2:
				deleteTweet();
				break;
			case 3:
				deleteReply();
				break;
			case 4:
				userHome();
				break;
			default:
				System.out.println("Input must be between 1 and 3");
				scan.nextLine();
			}
		}while (choose < 1 || choose > 4);
	}
	
	void viewAllTweet() {
		clear();
		for (User thisUser : users) {
			if (thisUser.getTweetList().isEmpty()) {
				continue;
			}
			else {
				System.out.println("User : " + thisUser.getUsername());
				int count = 1;
				for (Tweet thisTweet : thisUser.getTweetList()) {
					System.out.println(count++ + ". " + thisTweet.getTweet());
					System.out.println("  Replies : ");
					if (thisTweet.getReplyList().isEmpty()) {
						System.out.println("    No replies");
					}
					else {
						int countReply = 1;
						for (Reply thisReply : thisTweet.getReplyList()) {
							System.out.println("   " + countReply++ + ". User : " + thisReply.getUser());
							System.out.println("      " + thisReply.getReply());
						}
					}
					System.out.println();
				}
			}
		}
	}
	
	void viewUserTweet(User thisUser) {
		clear();
		int count = 1;
		System.out.println("User : " + thisUser.getUsername());
		for (Tweet thisTweet : thisUser.getTweetList()) {
			System.out.println(count++ + ". " + thisTweet.getTweet());
			System.out.println("  Replies : ");
			if (thisTweet.getReplyList().isEmpty()) {
				System.out.println("    No replies");
			}
			else {
				int countReply = 1;
				for (Reply thisReply : thisTweet.getReplyList()) {
					System.out.println("   " + countReply++ + ". User : " + thisReply.getUser());
					System.out.println("      " + thisReply.getReply());
				}
			}
			System.out.println();
		}
	}
	
	void tweet() {
		clear();
		String newTweet = "";
		do {
			System.out.print("Type your tweet [x to cancel]: ");
			newTweet = scan.nextLine();
			if (newTweet.equals("x")) {
				userHome();
			}
		}while (newTweet.length() <= 0);
		currentUser.addTweetList(new Tweet(newTweet));
		System.out.println("Posting your tweet...");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userHome();
	}
	
	void userHome() {
		int choose = -1;
		do {
			clear();
			System.out.println("Welcome, " + currentUser.getUsername());
			System.out.println("========================");
			System.out.println("1. Tweet");
			System.out.println("2. View Your Tweets");
			System.out.println("3. View All Tweets");
			System.out.println("4. Log Out");
			System.out.print(">> ");
			try {
				choose = scan.nextInt();
			} catch (Exception e) {
				choose = -1;
			}
			scan.nextLine();
			switch (choose) {
			case 1:
				tweet();
				break;
			case 2:
				tweetMenu("user");
				break;
			case 3:
				tweetMenu("all");
				break;
			case 4:
				mainMenu();
				break;
			default:
				System.out.println("Input must be between 1 and 4");
				scan.nextLine();
			}
		}while (choose < 1 || choose > 4);
	}
	
	void register() {
		clear();
		String username, password;
		boolean validUsername = false;
		
		do {
			System.out.print("Input your username [5-15 characters] : ");
			username = scan.nextLine();
			if (username.length() >= 5 && username.length() <= 20) {
				validUsername = true;
				for (User thisUser : users) {
					if (thisUser.getUsername().equals(username)) {
						System.out.println("Username is already taken!");
						validUsername = false;
					}
				}
			}
		}while (!validUsername);
		
		do {
			System.out.print("Input your password [5-15 characters] : ");
			password = scan.nextLine();
		}while (password.length() < 5 || password.length() > 20);
		
		users.add(new User(username, password));
		System.out.println("Registration Success!");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mainMenu();
	}
	
	void login() {
		clear();
		String username, password;
		boolean validUsername = false;
		
		do {
			System.out.print("Input your username [x to return to main menu] : ");
			username = scan.nextLine();
			if (username.equals("x")) {
				mainMenu();
			}
			else {
				for (User thisUser : users) {
					if (thisUser.getUsername().equals(username)) {
						currentUser = thisUser;
						validUsername = true;
						break;
					}
				}
			}
			if (!validUsername) {
				System.out.println("Username not found!");
			}
		}while (!validUsername);
		
		do {
			System.out.print("Input your password [x to return to main menu]: ");
			password = scan.nextLine();
			if (password.equals("x")) {
				mainMenu();
			}
			if (!currentUser.getPassword().equals(password)) {
				System.out.println("Password does not match!");
			}
		
		}while (!currentUser.getPassword().equals(password));
		
		userHome();
	}
	
	void mainMenu() {
		int choose = -1;
		
		do {
			clear();
			System.out.println("Twitter on Java");
			System.out.println("===============");
			System.out.println("1. Log In");
			System.out.println("2. Register");
			System.out.println("3. Exit");
			System.out.print(">> ");
			
			try {
				choose = scan.nextInt();
			} catch (Exception e) {
				choose = -1;
			}
			scan.nextLine();
			
			switch (choose) {
			case 1:
				login();
				break;
			case 2:
				register();
				break;
			case 3:
				System.exit(0);
				break;
			default:
				System.out.println("Input must be between 1 and 3");
			}
		}while (choose < 1 || choose > 3);
		
	}
	
	public Main() {
		// TODO Auto-generated constructor stub
		mainMenu();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main();
	}

}
