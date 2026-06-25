package src.service;

import src.exception.DuplicateEmailException;
import src.exception.InvalidEmailException;

public class AdminService {
    private static AdminService instance;
    private AuthenticationService authService;
    
    private AdminService() {
        authService = AuthenticationService.getInstance();
    }
    
    public static AdminService getInstance() {
        if (instance == null) {
            instance = new AdminService();
        }
        return instance;
    }
    
    public void initializeAdmin() {
        try {
            System.out.println("\n🔧 Initializing Admin Account...");
            
            // Try to register admin
            authService.registerAdmin("admin@pms.com", "admin123");
            
            System.out.println("Admin account created successfully!");
            System.out.println("Email: admin@pms.com");
            System.out.println("Password: admin123");
            System.out.println("=".repeat(50));
            
        } catch (InvalidEmailException e) {
            System.out.println("Admin creation failed: " + e.getMessage());
        } catch (DuplicateEmailException e) {
            System.out.println("ℹAdmin already exists!");
            System.out.println("Email: admin@pms.com");
            System.out.println("Password: admin123");
            System.out.println("=".repeat(50));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}