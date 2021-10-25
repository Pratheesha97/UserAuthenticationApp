package com.samadhi.project_wso2;

import com.samadhi.project_wso2.dao.UserDAO;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Registration {

	public static void register() {
		UserAccount user = new UserAccount();
		Logger logger = Logger.getAnonymousLogger();

		try (Scanner scanner = new Scanner(System.in)) {

			System.out.println("\n=========== REGISTER ===========");

			System.out.print("Enter name => ");
			String name = scanner.nextLine();
			while (!user.validateUserInfo(name)) {
				System.out.print("Re-Enter name => ");
				name = scanner.nextLine();
			}

			System.out.print("Enter email => ");
			String email = scanner.nextLine();
			while (!user.validateUserEmail(email)) {
				System.out.print("Re-Enter email => ");
				email = scanner.nextLine();
			}

			System.out.print("Enter password => ");
			String newPassword = scanner.nextLine();
			while (!user.validateUserInfo(newPassword)) {
				System.out.print("Re-Enter password=> ");
				newPassword = scanner.nextLine();
			}

			System.out.print("Confirm password => ");
			String confirmPassword = scanner.nextLine();
			while (!newPassword.equals(confirmPassword)) {
				System.err.println("Passwords do not match!");
				System.out.print("Re-confirm password=> ");
				confirmPassword = scanner.nextLine();
			}

			String[] hashedPassword;
			try {
				hashedPassword = SaltedHashFunction.getHashedPassword(newPassword);

				user.setName(name);
				user.setEmail(email);
				user.setSaltedHash(hashedPassword[0]);
				user.setSalt(hashedPassword[1]);

			} catch (Exception e) {
				logger.log(Level.ALL, "An Exception has occured: " + e.getMessage());
			}

			UserDAO dao = new UserDAO();
			dao.addUser(user);
			System.out.println("User Registered Successfully!");

			System.out.println("\n\nPress 2 -> Login");
			System.out.println("Press 4 -> Main menu");

			int optionSelected = 0;

			try {
				optionSelected = scanner.nextInt();
			} catch (InputMismatchException e) {
				logger.log(Level.ALL, "User input data do not match with the expected data type.");
				scanner.next();
			}

			while (optionSelected != 2 && optionSelected != 4) {
				System.err.println("Invalid option. Options => 2 (Login), 4 (Main menu)");
				try {
					optionSelected = scanner.nextInt();
				} catch (InputMismatchException e) {
					logger.log(Level.ALL, "User input data do not match with the expected data type.");
					scanner.next();
				}
			}

			switch (optionSelected) {
			case 2:
				Login.login();
				break;
			case 4:
				UserAuthenticationApp.main(null);
				break;
			default:
				System.err.println("Invalid option");
				break;
			}
		}
	}
}
