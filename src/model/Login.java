package src.model;

public class Login {
    private String email;
    private String password;
    private String userType; // "Admin" or "Passenger"
    private String status; // "Active" or "Deactivated"
    
    public Login() {}
    
    public Login(String email, String password, String userType, String status) {
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.status = status;
    }
    
    // Getters and Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    @Override
    public String toString() {
        return "Login{email='" + email + "', userType='" + userType + "', status='" + status + "'}";
    }
}