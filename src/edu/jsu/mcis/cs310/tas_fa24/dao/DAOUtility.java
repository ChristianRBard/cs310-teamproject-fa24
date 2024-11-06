package edu.jsu.mcis.cs310.tas_fa24.dao;

import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import com.github.cliftonlabs.json_simple.*;
import java.sql.*;
import edu.jsu.mcis.cs310.tas_fa24.EventType;
import edu.jsu.mcis.cs310.tas_fa24.Punch;
import edu.jsu.mcis.cs310.tas_fa24.Shift;

/**
 * Utility class for DAOs. This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 */
public final class DAOUtility {

    private DAOUtility() {}

    public static void close(ResultSet rs, Statement stmt) {
        closeResultSet(rs);
        closeStatement(stmt);
    }

    private static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("Error closing ResultSet: " + e.getMessage());
            }
        }
    }

    private static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing Statement: " + e.getMessage());
            }
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing Connection: " + e.getMessage());
            }
        }
    }
    
    public static EventType getEventType(int x){
        if(x == 1){
            return EventType.CLOCK_IN;
        }else if(x == 0){
            return EventType.CLOCK_OUT;
        }else if(x == 2){
            return EventType.TIME_OUT;
        }
        return null;
    }   
    
    public static String getPunchListAsJSON(ArrayList<Punch> dailyPunchList){
        ArrayList<HashMap<String, String>> jsonData = new ArrayList<>();
        
        for(int x = 0; x < dailyPunchList.size(); x++){
            HashMap<String, String> punchData = new HashMap<>();
            Punch punch = dailyPunchList.get(x);
            Shift s = ;
            punch.adjust();
            punchData.put("originaltimestamp", punch.printOriginal());
            punchData.put("badgeid", String.valueOf(punch.getBadge()));
            punchData.put("adjustedtimestamp", punch.printAdjusted());
            punchData.put("adjustmenttype", punch.getAdjustedTimeStamp().toString());
            punchData.put("terminalid", String.valueOf(punch.getTerminalid()));
            punchData.put("id", String.valueOf(punch.getID()));
            punchData.put("punchType", String.valueOf(punch.getPunchtype()));
            jsonData.add(punchData);
            System.out.println("Testing util" + punch.getOriginalTimestamp());
        }
        String json = Jsoner.serialize(jsonData);
        return json;
    }
}