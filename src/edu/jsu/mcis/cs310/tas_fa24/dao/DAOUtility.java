package edu.jsu.mcis.cs310.tas_fa24.dao;

import java.time.*;
import java.util.*;
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
 * @author Conner Bain, Christian Bard, Caden Parrish 
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
    /**
     * <p>This function determines the type of punch</p>
     * @param x
     * @return EventType
     */
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
    /**
     * <p>This function takes the output of PunchList and returns it in a JSON format.</p>
     * @param dailyPunchList Output from PunchList
     * @return JSON form of PunchList
     */
    public static String getPunchListAsJSON(ArrayList<Punch> dailyPunchList){
        ArrayList<HashMap<String, String>> jsonData = new ArrayList<>();
        for(int x = 0; x < dailyPunchList.size(); x++){
            HashMap<String, String> punchData = new HashMap<>();
            Punch punch = dailyPunchList.get(x);
            DateTimeFormatter format = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
            LocalDateTime ogt = punch.getOriginalTimestamp();
            String ogtFormatted = ogt.format(format).toUpperCase();
            LocalDateTime adt = punch.getAdjustedTimeStamp();
            String adtFormatted = adt.format(format).toUpperCase();

            punchData.put("originaltimestamp", ogtFormatted);
            punchData.put("badgeid", String.valueOf(punch.getBadge().getId()));
            punchData.put("adjustedtimestamp", adtFormatted);
            punchData.put("adjustmenttype", punch.getPunchAdjustmentType().toString());
            punchData.put("terminalid", String.valueOf(punch.getTerminalid()));
            punchData.put("id", String.valueOf(punch.getID()));
            punchData.put("punchtype", String.valueOf(punch.getPunchtype()));
            jsonData.add(punchData);
        }
        String json = Jsoner.serialize(jsonData);
        return json;
    }

    /**
     * <p>This method takes an array list of Punch objects and a Shift object,
     * iterates through the pairs of punches, calculates the total number of minutes
     * worked minus lunch break deductions if needed, then returns that number</p>
     * @param dailyPunchList The array list of Punch objects the user wants the total of minutes from
     * @param shift The Shift objects that is used to determine if time needs to be deducted for a lunch break
     * @return The total number of minutes accrued for the employee for the day as an int
     */
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
    
    //Unfinished function
    public static String getPunchListPlusTotalsAsJSON(ArrayList<Punch> punchlist, Shift shift){
        ArrayList<HashMap<String, String>> jsonData = new ArrayList<>();
        for(int x = 0; x < punchlist.size(); x++){
            HashMap<String, String> punchData = new HashMap<>();
            Punch punch = punchlist.get(x);
            DateTimeFormatter format = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
            LocalDateTime ogt = punch.getOriginalTimestamp();
            String ogtFormatted = ogt.format(format).toUpperCase();
            LocalDateTime adt = punch.getAdjustedTimeStamp();
            String adtFormatted = adt.format(format).toUpperCase();

            punchData.put("originaltimestamp", ogtFormatted);
            punchData.put("badgeid", String.valueOf(punch.getBadge().getId()));
            punchData.put("adjustedtimestamp", adtFormatted);
            punchData.put("adjustmenttype", punch.getPunchAdjustmentType().toString());
            punchData.put("terminalid", String.valueOf(punch.getTerminalid()));
            punchData.put("id", String.valueOf(punch.getID()));
            punchData.put("punchtype", String.valueOf(punch.getPunchtype()));
            //punchData.put("totalminutes", String.valueOf(calculateTotalMinutes()));
            //punchData.put("absenteeism", String.valueOf(calculateAbsenteeism());
            jsonData.add(punchData);
        }
        String json = Jsoner.serialize(jsonData);
        return json;
    }
}