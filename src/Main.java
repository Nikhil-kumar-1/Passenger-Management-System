package src;

import src.dao.LoginDAO;
import src.dao.PassengerDAO;
import src.model.Login;
import src.model.Passenger;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        LoginDAO loginDAO = new LoginDAO();
        PassengerDAO passengerDAO = new PassengerDAO();

        while (true) {

            System.out.println("\n===== PASSENGER MANAGEMENT SYSTEM =====");
            System.out.println("1. Admin Registration");
            System.out.println("2. Login");
            System.out.println("3. Register Passenger");
            System.out.println("4. Exit");

            System.out.print("Enter Choice : ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Email : ");
                    String email = sc.nextLine();

                    System.out.print("Password : ");
                    String password = sc.nextLine();

                    Login admin = new Login(
                            email,
                            password,
                            "ADMIN",
                            "ACTIVE"
                    );

                    loginDAO.register(admin);

                    break;

                case 2:

                    System.out.print("Email : ");
                    String loginEmail = sc.nextLine();

                    System.out.print("Password : ");
                    String loginPassword = sc.nextLine();

                    Login user =
                            loginDAO.login(
                                    loginEmail,
                                    loginPassword
                            );

                    if (user != null) {

                        if (user.getUserType()
                                .equalsIgnoreCase("ADMIN")) {

                            System.out.println(
                                    "Admin Login Successful");

                            adminMenu(
                                    sc,
                                    passengerDAO
                            );

                        } else {

                            System.out.println(
                                    "Passenger Login Successful");

                            passengerMenu(
                                    sc,
                                    passengerDAO,
                                    user.getEmail()
                            );
                        }

                    } else {

                        System.out.println(
                                "Invalid Credentials");
                    }

                    break;

                case 3:

                    System.out.print("PNR Number : ");
                    long pnr = sc.nextLong();
                    sc.nextLine();

                    System.out.print("Passenger Name : ");
                    String name = sc.nextLine();

                    System.out.print("Age : ");
                    int age = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Origin : ");
                    String origin = sc.nextLine();

                    System.out.print("Destination : ");
                    String destination = sc.nextLine();

                    System.out.print("Gender : ");
                    String gender = sc.nextLine();

                    System.out.print("Ticket Price : ");
                    double price = sc.nextDouble();

                    System.out.print("Train Number : ");
                    long trainNo = sc.nextLong();
                    sc.nextLine();

                    System.out.print("Email : ");
                    String passengerEmail = sc.nextLine();

                    System.out.print("Password : ");
                    String passengerPassword = sc.nextLine();

                    Passenger p = new Passenger(
                            pnr,
                            name,
                            age,
                            origin,
                            destination,
                            gender,
                            price,
                            trainNo,
                            passengerEmail
                    );

                    passengerDAO.addPassenger(p);

                    Login passengerLogin =
                            new Login(
                                    passengerEmail,
                                    passengerPassword,
                                    "PASSENGER",
                                    "ACTIVE"
                            );

                    loginDAO.register(passengerLogin);

                    break;

                case 4:

                    System.out.println("Thank You");
                    System.exit(0);

                    break;

                default:

                    System.out.println("Invalid Choice");
            }
        }
    }

    public static void adminMenu(
            Scanner sc,
            PassengerDAO passengerDAO) {

        while (true) {

            System.out.println("\n===== ADMIN DASHBOARD =====");
            System.out.println("1. View All Passengers");
            System.out.println("2. Search Passenger");
            System.out.println("3. Delete Passenger");
            System.out.println("4. Logout");

            System.out.print("Enter Choice : ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:

                    passengerDAO.viewPassengers();
                    break;

                case 2:

                    System.out.print("Enter PNR : ");
                    long searchPnr = sc.nextLong();

                    passengerDAO.searchPassenger(
                            searchPnr);

                    break;

                case 3:

                    System.out.print("Enter PNR : ");
                    long deletePnr = sc.nextLong();

                    passengerDAO.deletePassenger(
                            deletePnr);

                    break;

                case 4:

                    System.out.println(
                            "Logout Successful");

                    return;

                default:

                    System.out.println(
                            "Invalid Choice");
            }
        }
    }

    public static void passengerMenu(
            Scanner sc,
            PassengerDAO passengerDAO,
            String email) {

        while (true) {

            System.out.println(
                    "\n===== PASSENGER DASHBOARD =====");

            System.out.println(
                    "1. View My Details");

            System.out.println(
                    "2. Logout");

            System.out.print(
                    "Enter Choice : ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:

                    passengerDAO
                            .searchPassengerByEmail(
                                    email);

                    break;

                case 2:

                    System.out.println(
                            "Logout Successful");

                    return;

                default:

                    System.out.println(
                            "Invalid Choice");
            }
        }
    }
}