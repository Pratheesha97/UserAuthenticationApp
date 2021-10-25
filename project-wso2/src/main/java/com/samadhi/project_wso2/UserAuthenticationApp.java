package com.samadhi.project_wso2;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;

import com.samadhi.project_wso2.dao.UserDAO;

import java.util.logging.Level;

public class UserAuthenticationApp {

	public static void main(String[] args) {
		Logger logger = Logger.getAnonymousLogger();

		String appTitle = "\nWELCOME TO USER AUTHENTICATION APPLICATION";
		System.out.println(appTitle);
		for (int i = 0; i < appTitle.length(); i++) {
			System.out.print("=");
		}

		System.out.print("\n1. Register User\n2. Login\n3. List all users\n4. Back to main menu\n");

		try (Scanner scanner = new Scanner(System.in)) {
			int selectedOption = 0;

			System.out.print("\nEnter the option number to proceed: ");
			try {
				selectedOption = scanner.nextInt();
			} catch (InputMismatchException e) {
				logger.log(Level.ALL, "User input data do not match with the expected data type.");
				scanner.next();
			}

			while (selectedOption > 4 || selectedOption < 1) {
				System.err.println("Invalid Option Number!");
				System.out.print("Enter the option number to proceed: ");
				try {
					selectedOption = scanner.nextInt();
				} catch (InputMismatchException e) {
					logger.log(Level.ALL, "User input data do not match with the expected data type.");
					scanner.next();
				}

			}

			switch (selectedOption) {
			case 1:
				Registration.register();
				break;
			case 2:
				Login.login();
				break;
			case 3:
				UserAccount user = new UserAccount();
				UserDAO userDAO = new UserDAO();
				userDAO.displayUsers(user);
				break;
			case 4:
				UserAuthenticationApp.main(args);
				break;
			default:
				System.err.println("Invalid option number");
				break;
			}
		}
	}
}
