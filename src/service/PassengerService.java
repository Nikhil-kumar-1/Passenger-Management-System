package src.service;

import src.dao.PassengerDAO;
import src.model.Passenger;

public class PassengerService {

    PassengerDAO dao = new PassengerDAO();

    public void registerPassenger(
            Passenger passenger) {

        dao.addPassenger(passenger);
    }

    public void viewPassengers() {

        dao.viewPassengers();
    }

    public void searchPassenger(long pnr) {

        dao.searchPassenger(pnr);
    }

    public void deletePassenger(long pnr) {

        dao.deletePassenger(pnr);
    }
}