package com.samadhi.project_wso2;

import com.samadhi.project_wso2.dao.LoginDAO;

public class UserAccount {
	private int userID;
	private String name;
	private String email;
	private String salt;
	private String saltedHash;
	private boolean loggedIn = false;

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getSaltedHash() {
		return saltedHash;
	}

	public void setSaltedHash(String saltedHash) {
		this.saltedHash = saltedHash;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public boolean validateUserInfo(String string) {
		if (!string.isBlank()) {
			return true;
		} else {
			System.err.println("This field can't be empty!");
			return false;
		}
	}

	public boolean validateUserEmail(String string) {

		LoginDAO dao = new LoginDAO();
		UserAccount user = dao.authUser(string);

		if (!validateUserInfo(string) == true) {
			return false;
		} else if (!(string.contains("@") && string.contains("."))) {
			System.err.println("An email address must contain '@','.' symbols!");
			return false;
		} else if (user != null) {
			System.err.println("This User already exists");
			return false;
		} else {
			return true;
		}
	}
}
