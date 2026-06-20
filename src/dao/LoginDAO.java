package src.dao;

import src.database.DBConnection;
import src.model.Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDAO {

    public void register(Login login) {

        String sql =
        "INSERT INTO login(email,password,userType,status) VALUES(?,?,?,?)";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, login.getEmail());
            ps.setString(2, login.getPassword());
            ps.setString(3, login.getUserType());
            ps.setString(4, login.getStatus());

            ps.executeUpdate();

            System.out.println("Registration Successful");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

   public Login login(String email, String password) {

    String sql =
            "SELECT * FROM login WHERE email=? AND password=?";

    try {

        Connection con = DBConnection.getConnection();

        PreparedStatement ps =
                con.prepareStatement(sql);

        ps.setString(1, email);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        if(rs.next()) {

            return new Login(
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("userType"),
                    rs.getString("status")
            );
        }

    } catch (Exception e) {

        System.out.println(e.getMessage());
    }

    return null;
}
}