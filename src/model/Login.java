package src.model;

public class Login {

    private String email;
    private String password;
    private String userType;
    private String status;

    public Login(String email, String password,
                 String userType, String status) {

        this.email = email;
        this.password = password;
        this.userType = userType;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    public String getStatus() {
        return status;
    }
}