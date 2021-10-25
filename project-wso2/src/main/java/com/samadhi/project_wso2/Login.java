package com.samadhi.project_wso2;

import com.samadhi.project_wso2.dao.*;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Login {
	public static void login() {
		Logger logger = Logger.getAnonymousLogger();

		try (Scanner scanner = new Scanner(System.in)) {

			System.out.println("\n=========== LOGIN =============");

			System.out.print("Enter your email address=> ");
			String email = scanner.nextLine();

			System.out.print("Enter your password => ");
			String password = scanner.nextLine();

			LoginDAO loginDAO = new LoginDAO();
			UserAccount user = loginDAO.authUser(email);

			if (user != null) {
				String salt = user.getSalt();
				String HashedPw;
				try {
					HashedPw = SaltedHashFunction.getHashedGivenSalt(password, salt);

					if (!HashedPw.equals(user.getSaltedHash())) {
						System.err.println("Incorrect Password");
						login();
					} else {
						System.out.println("Successfully logged in!");
						loginDAO.updateUserStatus(user, "Login");

						System.out.println("\nHello " + user.getName() + ",\n");

						UserDAO userDAO = new UserDAO();
						userDAO.displayUsers(user);

						System.out.println("\n\nPress 0 to logout");
						int optionSelected = 1;

						try {
							optionSelected = scanner.nextInt();
						} catch (InputMismatchException e) {
							logger.log(Level.ALL, "User input data do not match with the expected data type.");
							scanner.next();
						}

						while (optionSelected != 0) {
							System.err.println("Invalid option. Press 0 to logout");
							try {
								optionSelected = scanner.nextInt();
							} catch (InputMismatchException e) {
								logger.log(Level.ALL, "User input data do not match with the expected data type.");
								scanner.next();
							}
						}

						if (optionSelected == 0) {
							loginDAO.updateUserStatus(user, "Logout");
							UserAuthenticationApp.main(null);
						}

					}

				} catch (Exception e) {
					logger.log(Level.ALL, "An Exception has occured: " + e.getMessage());
				}
			} else {
				System.err.println("User does not exist!");

				System.out.println("\n\nPress 2 -> Try Again");
				System.out.println("Press 4 -> Back to Main menu");

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
}
