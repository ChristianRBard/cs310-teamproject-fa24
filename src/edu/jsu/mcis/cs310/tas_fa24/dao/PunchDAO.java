package edu.jsu.mcis.cs310.tas_fa24.dao;

import edu.jsu.mcis.cs310.tas_fa24.Punch;
import edu.jsu.mcis.cs310.tas_fa24.Badge;
import edu.jsu.mcis.cs310.tas_fa24.EventType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class PunchDAO {
    private static final String QUERY_FIND = "SELECT * FROM event WHERE id = ?";
    private static final String QUERY_LIST = "SELECT * FROM event WHERE badgeid = ? AND DATE(timestamp) = ? ORDER BY DATE(timestamp)";

    private final DAOFactory daoFactory;

    public PunchDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public Punch find(int id){
        Punch punch = null;
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            Connection conn = daoFactory.getConnection();
            
            if(conn.isValid(0)){
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, id);
                
                rs = ps.executeQuery();
                
                if(rs.next()){
                    int termid = rs.getInt("terminalid");
                    String badgeid = rs.getString("badgeid");
                    Timestamp timestamp = rs.getTimestamp("timestamp");
                    int eventType = rs.getInt("eventtypeid");
                    Badge bad = badgeDAO.find(badgeid);
                    EventType punchType = DAOUtility.getEventType(eventType);
                    punch = new Punch(id, termid, bad, timestamp.toLocalDateTime(), punchType);
                }  
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            DAOUtility.close(rs, ps);
        }
        return punch;
    }

    public ArrayList<Punch> list(Badge badge, LocalDate date) {
        ArrayList<Punch> punchList = new ArrayList<>();
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();
            pst = conn.prepareStatement(QUERY_LIST);
            pst.setString(1, badge.getId());
            pst.setDate(2, Date.valueOf(date));
            rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int termID = rs.getInt("terminalid");
                Timestamp time = rs.getTimestamp("timestamp");
                int eventID = rs.getInt("eventtypeid");
                EventType event = DAOUtility.getEventType(eventID);
                Punch punch = new Punch(id, termID, badge, time.toLocalDateTime(), event);
                punchList.add(punch);
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            DAOUtility.close(rs, pst);
        }

        return punchList;
    }

    /*public ArrayList<Punch> list(Badge badge, LocalDate startDate, LocalDate endDate) {
        LocalDate dateCounter = startDate;
        ArrayList<Punch> rangePunchList = new ArrayList<>();
        rangePunchList.addAll(list(badge, dateCounter));
        for (Punch p : rangePunchList) {
            System.out.print(p.toString() + "\t");
        }
        while (dateCounter.isBefore(endDate)) {
            dateCounter.plusDays(1);
            rangePunchList.addAll(list(badge, dateCounter));
            
        }
        return rangePunchList;
    }*/
}
    /*public Punch find(int id) {
        Punch punch = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();
            pst = conn.prepareStatement(QUERY_FIND);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            if (rs.next()) {
                punch = extractPunchFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            DAOUtility.close(rs, pst);
        }

        return punch;
    }*/

    /*private Punch extractPunchFromResultSet(ResultSet rs) throws SQLException {
        // Replace these fields with actual columns from your 'event' table
        int id = rs.getInt("id");
        String badgeId = rs.getString("badgeid");
        String eventType = rs.getString("eventtype");
        Timestamp timestamp = rs.getTimestamp("timestamp");

        // Create and return a new Punch object
        return new Punch(id, badgeId, EventType.valueOf(eventType.toUpperCase()), timestamp.toLocalDateTime());
    }*/
