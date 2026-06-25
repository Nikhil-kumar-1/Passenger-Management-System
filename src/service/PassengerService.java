package src.service;

import src.model.Passenger;
import src.exception.*;
import src.util.JsonFormatter;

import java.util.ArrayList;
import java.util.List;

public class PassengerService {
    private static PassengerService instance;
    private List<Passenger> passengerTable;
    private AuthenticationService authService;
    
    private PassengerService() {
        passengerTable = new ArrayList<>();
        authService = AuthenticationService.getInstance();
    }
    
    public static PassengerService getInstance() {
        if (instance == null) {
            instance = new PassengerService();
        }
        return instance;
    }
    
    public Passenger registerPassenger(String passengerName, int age, String origin, 
                                       String destination, String gender, double ticketPrice, 
                                       String trainNumber, String email, String password) 
            throws InvalidEmailException, DuplicateEmailException, DatabaseException {
        
        try {
            System.out.println("\n Registering passenger: " + passengerName);
            System.out.println(" Email: " + email);
            
            // Register login credentials first
            authService.registerPassenger(email, password);
            
            // Generate PNR Number
            String pnrNumber = generatePNRNumber();
            
            // Create passenger
            Passenger passenger = new Passenger(
                pnrNumber,
                passengerName,
                age,
                origin,
                destination,
                gender,
                ticketPrice,
                trainNumber,
                email,
                password
            );
            
            // Store in passenger table
            passengerTable.add(passenger);
            
            // Print success message
            System.out.println("\n Passenger registered successfully!");
            System.out.println(" Passenger Details:");
            System.out.println(JsonFormatter.passengerToJson(passenger));
            
            return passenger;
            
        } catch (InvalidEmailException | DuplicateEmailException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseException("Database error occurred while registering passenger: " + e.getMessage());
        }
    }
    
    private String generatePNRNumber() {
        return "PMS" + String.format("%08d", (int)(Math.random() * 100000000));
    }
    
    public Passenger findPassengerByEmail(String email) {
        for (Passenger p : passengerTable) {
            if (p.getEmail().equalsIgnoreCase(email)) {
                return p;
            }
        }
        return null;
    }
    
    public List<Passenger> getAllPassengers() {
        return new ArrayList<>(passengerTable);
    }
    
    public void displayAllPassengers() {
        if (passengerTable.isEmpty()) {
            System.out.println(" No passengers registered yet.");
        } else {
            System.out.println("\n All Registered Passengers:");
            System.out.println("=".repeat(60));
            for (Passenger p : passengerTable) {
                System.out.println(JsonFormatter.passengerToJson(p));
                System.out.println("-".repeat(60));
            }
        }
    }
}