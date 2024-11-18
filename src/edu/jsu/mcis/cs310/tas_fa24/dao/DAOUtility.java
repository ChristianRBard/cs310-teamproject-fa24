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
    
    public static int calculateTotalMinutes(ArrayList<Punch> dailyPunchList, Shift shift) {
        long totalMinutes = 0;
        boolean lunchClockOut = false;
        for (Punch i: dailyPunchList) {
            i.adjust(shift);
        }
        if (!dailyPunchList.isEmpty()){
            LocalTime finalClockOut = dailyPunchList.get(dailyPunchList.size()-1).getAdjustedTimeStamp().toLocalTime();
            ArrayList<Punch> punchPair = new ArrayList<>();
            while (!dailyPunchList.isEmpty()) {
                if (dailyPunchList.get(0).getPunchtype() == EventType.valueOf("CLOCK_IN")){
                    punchPair.add(dailyPunchList.get(0));
                    dailyPunchList.remove(0);
                    if (dailyPunchList.get(0).getPunchtype() == EventType.valueOf("CLOCK_OUT")) {
                        punchPair.add(dailyPunchList.get(0));
                        dailyPunchList.remove(0);
                        if (totalMinutes != 0) {
                        lunchClockOut = true;
                    }
                    } else if (dailyPunchList.get(0).getPunchtype() == EventType.valueOf("TIME_OUT")) {
                        punchPair.remove(0);
                        dailyPunchList.remove(0);
                    }
                    else {
                        dailyPunchList.remove(0);
                    }
                }else {
                    dailyPunchList.remove(0);
                }
                if (!punchPair.isEmpty()) {
                    totalMinutes += java.time.Duration.between(punchPair.get(0).getAdjustedTimeStamp(), punchPair.get(1).getAdjustedTimeStamp()).toMinutes();
                    punchPair.removeAll(punchPair);
                }
            }
            if (!lunchClockOut && finalClockOut.isAfter(shift.getLunchStart())) {
                if (totalMinutes >= shift.getLunchThreshold()){
                    totalMinutes -= java.time.Duration.between(shift.getLunchStart(), shift.getLunchStop()).toMinutes();
                }
            }
            punchPair.removeAll(punchPair);
        }
        return (int)totalMinutes;
    }
}