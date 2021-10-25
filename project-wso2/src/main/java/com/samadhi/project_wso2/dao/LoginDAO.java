package com.samadhi.project_wso2.dao;

import com.samadhi.project_wso2.UserAccount;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

	Connection con = null;
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

	public UserAccount authUser(String email) {
		try {
			UserAccount user = new UserAccount();
			String query = "select * from User where email=\'" + email + "\'";
			connect();
			Statement st = con.createStatement();

			try {
				ResultSet rs = st.executeQuery(query);

				rs.next();
				user.setUserID(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setSalt(rs.getString(4));
				user.setSaltedHash(rs.getString(5));
				user.setLoggedIn(rs.getBoolean(6));

				return user;

			} catch (Exception e) {
				logger.log(Level.ALL, "An Exception has occured: " + e.getMessage());
				return null;
			} finally {
				st.close();
				con.close();
			}

		} catch (SQLException e) {
			logger.log(Level.ALL, e.getMessage());
		}

		return null;
	}

	public void updateUserStatus(UserAccount user, String status) {
		String query = "update User set loggedIn=? where userID=?";
		PreparedStatement pst = null;
		connect();

		try {
			pst = con.prepareStatement(query);

			if (status == "Login") {
				pst.setBoolean(1, true);
				user.setLoggedIn(true);
			} else {
				pst.setBoolean(1, false);
				user.setLoggedIn(false);
			}

			pst.setInt(2, user.getUserID());

			pst.executeUpdate();

			pst.close();
			con.close();

		} catch (SQLException e) {
			logger.log(Level.ALL, e.getMessage());
		}
	}
}
