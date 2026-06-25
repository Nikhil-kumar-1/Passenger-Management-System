package src.ui;

import src.model.Login;
import src.model.Passenger;
import src.service.AuthenticationService;
import src.service.PassengerService;
import src.service.AdminService;
import src.exception.*;
import src.util.EmailValidator;

import java.util.Scanner;

public class ConsoleUI {
    private Scanner scanner;
    private AuthenticationService authService;
    private PassengerService passengerService;
    private AdminService adminService;
    private Login currentUser;
    
    public ConsoleUI() {
        scanner = new Scanner(System.in);
        authService = AuthenticationService.getInstance();
        passengerService = PassengerService.getInstance();
        adminService = AdminService.getInstance();
    }
    
    public void start() {
        System.out.println("============================================================");
        System.out.println("  PASSENGER MANAGEMENT SYSTEM (PMS)");
        System.out.println("============================================================");
        
        adminService.initializeAdmin();
        
        while (true) {
            displayMainMenu();
            System.out.print("Enter your choice: ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Please enter a valid number.");
                continue;
            }
            
            switch (choice) {
                case 1:
                    adminLogin();
                    break;
                case 2:
                    passengerLogin();
                    break;
                case 3:
                    registerPassenger();
                    break;
                case 4:
                    System.out.println("\nThank you for using PMS!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("ERROR: Invalid choice. Please try again.");
            }
        }
    }
    
    private void displayMainMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. Admin Login");
        System.out.println("2. Passenger Login");
        System.out.println("3. Register as Passenger");
        System.out.println("4. Exit");
        System.out.println("================================");
    }
    
    private void adminLogin() {
        System.out.println("\n========== ADMIN LOGIN ==========");
        
        String email = getInput("Email: ");
        String password = getInput("Password: ");
        
        try {
            Login login = authService.validateLogin(email, password);
            if (login.getUserType().equals("Admin")) {
                currentUser = login;
                System.out.println("SUCCESS: Admin login successful!");
                adminMenu();
            } else {
                System.out.println("ERROR: Access denied. Admin only.");
            }
        } catch (InvalidEmailException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("HINT: " + EmailValidator.getEmailHelp());
        } catch (InvalidCredentialsException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("HINT: Use admin@pms.com / admin123");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    
    private void passengerLogin() {
        System.out.println("\n========== PASSENGER LOGIN ==========");
        
        String email = getInput("Email: ");
        String password = getInput("Password: ");
        
        try {
            Login login = authService.validateLogin(email, password);
            if (login.getUserType().equals("Passenger")) {
                currentUser = login;
                System.out.println("SUCCESS: Passenger login successful!");
                passengerMenu();
            } else {
                System.out.println("ERROR: Access denied. Passenger only.");
            }
        } catch (InvalidEmailException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("HINT: " + EmailValidator.getEmailHelp());
        } catch (InvalidCredentialsException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    
    private void registerPassenger() {
        System.out.println("\n========== PASSENGER REGISTRATION ==========");
        
        try {
            String name = getInput("Passenger Name: ");
            
            int age = 0;
            while (true) {
                try {
                    age = Integer.parseInt(getInput("Age: "));
                    if (age < 1 || age > 150) {
                        System.out.println("ERROR: Please enter valid age (1-150)");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Please enter a valid number");
                }
            }
            
            String origin = getInput("Origin: ");
            String destination = getInput("Destination: ");
            String gender = getInput("Gender (M/F): ");
            
            double ticketPrice = 0;
            while (true) {
                try {
                    ticketPrice = Double.parseDouble(getInput("Ticket Price: Rs. "));
                    if (ticketPrice < 0) {
                        System.out.println("ERROR: Ticket price cannot be negative");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Please enter a valid number");
                }
            }
            
            String trainNumber = getInput("Train Number: ");
            
            // Email validation with duplicate check
            String email;
            while (true) {
                email = getInput("Email: ");
                
                if (!EmailValidator.isValidEmail(email)) {
                    System.out.println("ERROR: Invalid email format!");
                    System.out.println("HINT: " + EmailValidator.getEmailHelp());
                    System.out.println("HINT: Example: nikhil@gmail.com");
                    continue;
                }
                
                // Check if email already exists
                if (authService.isEmailExists(email)) {
                    System.out.println("ERROR: This email is already registered!");
                    System.out.println("HINT: Please use a different email address.");
                    System.out.println("HINT: Or login if you already have an account.");
                    return;
                }
                break;
            }
            
            String password = getInput("Password: ");
            if (password.length() < 3) {
                System.out.println("ERROR: Password must be at least 3 characters");
                return;
            }
            
            Passenger passenger = passengerService.registerPassenger(
                name, age, origin, destination, gender, 
                ticketPrice, trainNumber, email, password
            );
            
            System.out.println("\nSUCCESS: Registration complete! You can now login.");
            
        } catch (InvalidEmailException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (DuplicateEmailException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("HINT: This email is already registered.");
        } catch (DatabaseException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void adminMenu() {
        while (true) {
            System.out.println("\n========== ADMIN MENU ==========");
            System.out.println("1. Register Passenger");
            System.out.println("2. View All Passengers");
            System.out.println("3. Logout");
            System.out.println("================================");
            
            System.out.print("Enter your choice: ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Please enter a valid number.");
                continue;
            }
            
            switch (choice) {
                case 1:
                    registerPassenger();
                    break;
                case 2:
                    passengerService.displayAllPassengers();
                    break;
                case 3:
                    currentUser = null;
                    System.out.println("SUCCESS: Logout successful!");
                    return;
                default:
                    System.out.println("ERROR: Invalid choice.");
            }
        }
    }
    
    private void passengerMenu() {
        while (true) {
            System.out.println("\n========== PASSENGER MENU ==========");
            System.out.println("1. View My Details");
            System.out.println("2. Logout");
            System.out.println("====================================");
            
            System.out.print("Enter your choice: ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Please enter a valid number.");
                continue;
            }
            
            switch (choice) {
                case 1:
                    viewPassengerDetails();
                    break;
                case 2:
                    currentUser = null;
                    System.out.println("SUCCESS: Logout successful!");
                    return;
                default:
                    System.out.println("ERROR: Invalid choice.");
            }
        }
    }
    
    private void viewPassengerDetails() {
        if (currentUser != null) {
            Passenger passenger = passengerService.findPassengerByEmail(currentUser.getEmail());
            if (passenger != null) {
                System.out.println("\n========== YOUR DETAILS ==========");
                System.out.println("PNR Number    : " + passenger.getPnrNumber());
                System.out.println("Name          : " + passenger.getPassengerName());
                System.out.println("Age           : " + passenger.getAge());
                System.out.println("Origin        : " + passenger.getOrigin());
                System.out.println("Destination   : " + passenger.getDestination());
                System.out.println("Gender        : " + passenger.getGender());
                System.out.println("Ticket Price  : Rs." + passenger.getTicketPrice());
                System.out.println("Train Number  : " + passenger.getTrainNumber());
                System.out.println("Email         : " + passenger.getEmail());
                System.out.println("====================================");
            } else {
                System.out.println("ERROR: Passenger details not found.");
            }
        }
    }
    
    private String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}