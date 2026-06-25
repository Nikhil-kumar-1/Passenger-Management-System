package src.model;

public class Passenger {
    private String pnrNumber;
    private String passengerName;
    private int age;
    private String origin;
    private String destination;
    private String gender;
    private double ticketPrice;
    private String trainNumber;
    private String email;
    private String password;
    
    public Passenger() {}
    
    public Passenger(String pnrNumber, String passengerName, int age, String origin, 
                     String destination, String gender, double ticketPrice, 
                     String trainNumber, String email, String password) {
        this.pnrNumber = pnrNumber;
        this.passengerName = passengerName;
        this.age = age;
        this.origin = origin;
        this.destination = destination;
        this.gender = gender;
        this.ticketPrice = ticketPrice;
        this.trainNumber = trainNumber;
        this.email = email;
        this.password = password;
    }
    
    // Getters and Setters
    public String getPnrNumber() { return pnrNumber; }
    public void setPnrNumber(String pnrNumber) { this.pnrNumber = pnrNumber; }
    
    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }
    
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }
    
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public double getTicketPrice() { return ticketPrice; }
    public void setTicketPrice(double ticketPrice) { this.ticketPrice = ticketPrice; }
    
    public String getTrainNumber() { return trainNumber; }
    public void setTrainNumber(String trainNumber) { this.trainNumber = trainNumber; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    @Override
    public String toString() {
        return "Passenger{" +
                "pnrNumber='" + pnrNumber + '\'' +
                ", passengerName='" + passengerName + '\'' +
                ", age=" + age +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", gender='" + gender + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", trainNumber='" + trainNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}