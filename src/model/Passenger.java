package src.model;

public class Passenger {

    private long pnrNumber;
    private String passengerName;
    private int age;
    private String origin;
    private String destination;
    private String gender;
    private double ticketPrice;
    private long trainNumber;
    private String email;

    public Passenger(long pnrNumber, String passengerName, int age,
                     String origin, String destination,
                     String gender, double ticketPrice,
                     long trainNumber, String email) {

        this.pnrNumber = pnrNumber;
        this.passengerName = passengerName;
        this.age = age;
        this.origin = origin;
        this.destination = destination;
        this.gender = gender;
        this.ticketPrice = ticketPrice;
        this.trainNumber = trainNumber;
        this.email = email;
    }

    public long getPnrNumber() {
        return pnrNumber;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public int getAge() {
        return age;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getGender() {
        return gender;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public long getTrainNumber() {
        return trainNumber;
    }

    public String getEmail() {
        return email;
    }
}