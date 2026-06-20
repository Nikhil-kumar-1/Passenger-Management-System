package src.dao;

import src.database.DBConnection;
import src.model.Passenger;

import java.sql.*;

public class PassengerDAO {

    public void addPassenger(Passenger p) {

        String sql =
                "INSERT INTO passenger(pnrNumber,passengerName,age,origin,destination,gender,ticketPrice,trainNumber,email) VALUES(?,?,?,?,?,?,?,?,?)";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setLong(1, p.getPnrNumber());
            ps.setString(2, p.getPassengerName());
            ps.setInt(3, p.getAge());
            ps.setString(4, p.getOrigin());
            ps.setString(5, p.getDestination());
            ps.setString(6, p.getGender());
            ps.setDouble(7, p.getTicketPrice());
            ps.setLong(8, p.getTrainNumber());
            ps.setString(9, p.getEmail());

            ps.executeUpdate();

            System.out.println("Passenger Added Successfully");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    public void viewPassengers() {

        String sql = "SELECT * FROM passenger";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n===== PASSENGER LIST =====");

            while (rs.next()) {

                System.out.println(
                        rs.getLong("pnrNumber")
                                + " | "
                                + rs.getString("passengerName")
                                + " | "
                                + rs.getInt("age")
                                + " | "
                                + rs.getString("origin")
                                + " | "
                                + rs.getString("destination")
                                + " | "
                                + rs.getString("email")
                );
            }

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

public void searchPassenger(long pnr) {

    String sql =
            "SELECT * FROM passenger WHERE pnrNumber=?";

    try {

        Connection con = DBConnection.getConnection();

        PreparedStatement ps =
                con.prepareStatement(sql);

        ps.setLong(1, pnr);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            System.out.println("\n===== PASSENGER FOUND =====");

            System.out.println(
                    rs.getLong("pnrNumber")
                            + " | "
                            + rs.getString("passengerName")
                            + " | "
                            + rs.getInt("age")
                            + " | "
                            + rs.getString("origin")
                            + " | "
                            + rs.getString("destination")
                            + " | "
                            + rs.getString("email")
            );

        } else {

            System.out.println("Passenger Not Found");
        }

    } catch (Exception e) {

        System.out.println(e.getMessage());
    }
}

public void deletePassenger(long pnr) {

    String sql =
            "DELETE FROM passenger WHERE pnrNumber=?";

    try {

        Connection con = DBConnection.getConnection();

        PreparedStatement ps =
                con.prepareStatement(sql);

        ps.setLong(1, pnr);

        int rows = ps.executeUpdate();

        if (rows > 0) {

            System.out.println(
                    "Passenger Deleted Successfully");

        } else {

            System.out.println(
                    "Passenger Not Found");
        }

    } catch (Exception e) {

        System.out.println(e.getMessage());
    }

    
}
public void searchPassengerByEmail(String email) {

    String sql =
            "SELECT * FROM passenger WHERE email=?";

    try {

        Connection con = DBConnection.getConnection();

        PreparedStatement ps =
                con.prepareStatement(sql);

        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            System.out.println("\n===== MY DETAILS =====");

            System.out.println(
                    rs.getLong("pnrNumber")
                            + " | "
                            + rs.getString("passengerName")
                            + " | "
                            + rs.getInt("age")
                            + " | "
                            + rs.getString("origin")
                            + " | "
                            + rs.getString("destination")
                            + " | "
                            + rs.getString("gender")
                            + " | "
                            + rs.getDouble("ticketPrice")
                            + " | "
                            + rs.getLong("trainNumber")
                            + " | "
                            + rs.getString("email")
            );

        } else {

            System.out.println("Passenger Not Found");
        }

    } catch (Exception e) {

        System.out.println(e.getMessage());
    }
}


}

