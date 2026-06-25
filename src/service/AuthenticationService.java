package src.service;

import src.model.Login;
import src.exception.*;
import src.util.EmailValidator;

public class AuthenticationService {
    private static AuthenticationService instance;
    private LoginService loginService;
    
    private AuthenticationService() {
        loginService = new LoginService();
    }
    
    public static AuthenticationService getInstance() {
        if (instance == null) {
            instance = new AuthenticationService();
        }
        return instance;
    }
    
    public void registerAdmin(String email, String password) 
            throws InvalidEmailException, DuplicateEmailException {
        
        System.out.println("Registering Admin: " + email);
        
        // Validate email
        if (!EmailValidator.isValidEmail(email)) {
            throw new InvalidEmailException("Invalid email format: " + email + 
                ". " + EmailValidator.getEmailHelp());
        }
        
        Login admin = new Login(email, password, "Admin", "Active");
        loginService.register(admin);
        System.out.println("Admin registered: " + email);
    }
    
    public void registerPassenger(String email, String password) 
            throws InvalidEmailException, DuplicateEmailException {
        
        System.out.println("Registering Passenger: " + email);
        
        // Validate email
        if (!EmailValidator.isValidEmail(email)) {
            throw new InvalidEmailException("Invalid email format: " + email + 
                ". " + EmailValidator.getEmailHelp());
        }
        
        Login passenger = new Login(email, password, "Passenger", "Active");
        loginService.register(passenger);
        System.out.println("Passenger login registered: " + email);
    }
    
    public Login validateLogin(String email, String password) 
            throws InvalidCredentialsException, InvalidEmailException {
        
        // Validate email format first
        if (!EmailValidator.isValidEmail(email)) {
            throw new InvalidEmailException("Invalid email format: " + email + 
                ". " + EmailValidator.getEmailHelp());
        }
        
        return loginService.login(email, password);
    }
    
    // Check if email already exists
public boolean isEmailExists(String email) {
    return loginService.isEmailExists(email);
}
}