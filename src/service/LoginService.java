package src.service;

import src.model.Login;
import src.exception.DuplicateEmailException;
import src.exception.InvalidCredentialsException;

import java.util.ArrayList;
import java.util.List;

public class LoginService {
    private static List<Login> loginTable = new ArrayList<>();

    public void register(Login login) throws DuplicateEmailException {
        // Check duplicate
        if (findByEmail(login.getEmail()) != null) {
            throw new DuplicateEmailException("Email already exists: " + login.getEmail());
        }
        
        loginTable.add(login);
        System.out.println("Login stored: " + login.getEmail() + " | Total: " + loginTable.size());
    }

    public Login login(String email, String password) throws InvalidCredentialsException {
        System.out.println("Searching: " + email + " | Total records: " + loginTable.size());
        
        for (Login login : loginTable) {
            System.out.println("   Checking: " + login.getEmail());
            if (login.getEmail().equalsIgnoreCase(email)) {
                System.out.println("   Email match found!");
                if (login.getPassword().equals(password)) {
                    System.out.println("   Password match found!");
                    if (!login.getStatus().equals("Active")) {
                        throw new InvalidCredentialsException("Account is deactivated");
                    }
                    System.out.println("Login successful!");
                    return login;
                } else {
                    System.out.println("   Password mismatch!");
                }
            }
        }
        throw new InvalidCredentialsException("Invalid email or password");
    }

    private Login findByEmail(String email) {
        for (Login login : loginTable) {
            if (login.getEmail().equalsIgnoreCase(email)) {
                return login;
            }
        }
        return null;
    }
    
    public List<Login> getAllLogins() {
        return new ArrayList<>(loginTable);
    }
    
    public boolean isEmailExists(String email) {
    return findByEmail(email) != null;
}
}