package src.util;

import src.model.Passenger;

public class JsonFormatter {
    public static String passengerToJson(Passenger passenger) {
        if (passenger == null) {
            return "{}";
        }
        
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\n  \"pnrNumber\": \"").append(escape(passenger.getPnrNumber())).append("\",");
        json.append("\n  \"passengerName\": \"").append(escape(passenger.getPassengerName())).append("\",");
        json.append("\n  \"age\": ").append(passenger.getAge()).append(",");
        json.append("\n  \"origin\": \"").append(escape(passenger.getOrigin())).append("\",");
        json.append("\n  \"destination\": \"").append(escape(passenger.getDestination())).append("\",");
        json.append("\n  \"gender\": \"").append(escape(passenger.getGender())).append("\",");
        json.append("\n  \"ticketPrice\": ").append(passenger.getTicketPrice()).append(",");
        json.append("\n  \"trainNumber\": \"").append(escape(passenger.getTrainNumber())).append("\",");
        json.append("\n  \"email\": \"").append(escape(passenger.getEmail())).append("\"");
        json.append("\n}");
        
        return json.toString();
    }
    
    private static String escape(String str) {
        if (str == null) return "";
        return str.replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
}