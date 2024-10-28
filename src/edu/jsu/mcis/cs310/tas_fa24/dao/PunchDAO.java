package edu.jsu.mcis.cs310.tas_fa24.dao;

import java.util.ArrayList;
import java.time.LocalDate;
import edu.jsu.mcis.cs310.tas_fa24.Punch;
import edu.jsu.mcis.cs310.tas_fa24.Badge;
import edu.jsu.mcis.cs310.tas_fa24.EventType;
import edu.jsu.mcis.cs310.tas_fa24.Department;
import edu.jsu.mcis.cs310.tas_fa24.Employee;
import java.sql.*;

public class PunchDAO {
    private static final String QUERY_FIND = "SELECT * FROM event WHERE id = ?";  
    private static final String QUERY_INSERT = "INSERT INTO event (terminalid, badgeid, timestamp, eventtypeid) VALUES (?, ?, ?, ?)";
    private static final String QUERY_LIST = "SELECT * FROM event WHERE badgeid = ? AND DATE(timestamp) = ? ORDER BY DATE(timestamp)";

    private final DAOFactory daoFactory;

    public PunchDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Punch find(int id) {
        Punch punch = null;
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();
            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                if (rs.next()) {
                    int termid = rs.getInt("terminalid");
                    String badgeid = rs.getString("badgeid");
                    Timestamp timestamp = rs.getTimestamp("timestamp");
                    int eventType = rs.getInt("eventtypeid");
                    Badge badge = badgeDAO.find(badgeid);
                    EventType punchType = DAOUtility.getEventType(eventType);
                    punch = new Punch(id, termid, badge, timestamp.toLocalDateTime(), punchType);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            DAOUtility.close(rs, ps);
        }
        return punch;
    }

    public int create(Punch punch) {
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
        int resultId = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();
            if (conn.isValid(0)) {
                // Retrieve Employee and Department information
                Employee employee = employeeDAO.find(punch.getBadge());
                Department department = employee.getDepartment();
                
                // Authorization check
                if (punch.getTerminalid() == department.getTerminalId() || punch.getTerminalid() == 0) {
                    // Authorized, insert into event table
                    ps = conn.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, punch.getTerminalid());
                    ps.setString(2, punch.getBadge().getId());
                    ps.setTimestamp(3, Timestamp.valueOf(punch.getOriginaltimestamp()));
                    ps.setInt(4, punch.getPunchtype().ordinal());

                    int rowsAffected = ps.executeUpdate();

                    if (rowsAffected == 1) {
                        rs = ps.getGeneratedKeys();
                        if (rs.next()) {
                            resultId = rs.getInt(1);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            DAOUtility.close(rs, ps);
        }
        return resultId;
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






public ArrayList<Punch> list(Badge badge, LocalDate startDate, LocalDate endDate) {

    LocalDate dateCounter = startDate;
    ArrayList<Punch> rangePunchList = new ArrayList<>();
    rangePunchList.addAll(list(badge, dateCounter));    
    for (Punch p : rangePunchList) {
        System.out.print(p.toString() + "\t");
    }
    while (dateCounter.isBefore(endDate)) {
        dateCounter = dateCounter.plusDays(1);
        rangePunchList.addAll(list(badge, dateCounter));
        
    }
    return rangePunchList;
    }
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