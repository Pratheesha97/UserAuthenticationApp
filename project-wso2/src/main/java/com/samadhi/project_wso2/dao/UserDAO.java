package com.samadhi.project_wso2.dao;

import com.samadhi.project_wso2.Login;
import com.samadhi.project_wso2.UserAccount;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.logging.Level;

public class UserDAO {

	private Connection con = null;
	Logger logger = Logger.getAnonymousLogger();

	public void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user_management_app_db", "root", "root");
		} catch (ClassNotFoundException e) {
			logger.log(Level.ALL, "MySQL JDBC driver class can not found in the java class path");
		} catch (SQLException e) {
			logger.log(Level.ALL, e.getMessage());
		}
	}

	public void addUser(UserAccount user) {
		String query = "insert into User (user_name, email, salt, salted_Hash, loggedIn) values (?,?,?,?,?)";
		PreparedStatement pst;
		connect();

		try {
			pst = con.prepareStatement(query);

			pst.setString(1, user.getName());
			pst.setString(2, user.getEmail());
			pst.setString(3, user.getSalt());
			pst.setString(4, user.getSaltedHash());
			pst.setBoolean(5, user.isLoggedIn());

			pst.executeUpdate();

			pst.close();
			con.close();

		} catch (SQLException e) {
			logger.log(Level.ALL, e.getMessage());
		}
	}

	public void displayUsers(UserAccount user) {

		if (user.isLoggedIn() == false) {
			Login.login();
		}

		try {
			String query = "select * from User";

			connect();
			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery(query);

			System.out.println("=================== ALL USERS ====================");

			String leftAlignFormat = "| %-7d | %-14s | %-19s |%n";

			System.out.format("+---------+----------------+---------------------+%n");
			System.out.format("| User ID |      Name      |    Email Address    |%n");
			System.out.format("+---------+----------------+---------------------+%n");
			while (rs.next()) {
				System.out.format(leftAlignFormat, rs.getInt(1), rs.getString(2), rs.getString(3));
			}
			System.out.format("+---------+----------------+---------------------+%n");

			st.close();
			con.close();

		} catch (Exception e) {
			logger.log(Level.ALL, e.getMessage());
		}
	}
}
