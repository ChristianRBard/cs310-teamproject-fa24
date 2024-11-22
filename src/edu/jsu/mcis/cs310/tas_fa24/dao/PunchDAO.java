package edu.jsu.mcis.cs310.tas_fa24.dao;

import java.util.ArrayList;
import java.time.LocalDate;
import edu.jsu.mcis.cs310.tas_fa24.Punch;
import edu.jsu.mcis.cs310.tas_fa24.Badge;
import edu.jsu.mcis.cs310.tas_fa24.Shift;
import edu.jsu.mcis.cs310.tas_fa24.EventType;
import edu.jsu.mcis.cs310.tas_fa24.Department;
import edu.jsu.mcis.cs310.tas_fa24.Employee;
import java.sql.*;

/**
 * <p>This class provides methods for accessing Punch objects</p>
 * @author Caden Parrish, Connor Bain, Christian Bard
 */
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
        Connection conn = null;

        try {
            conn = daoFactory.getConnection();
            if (conn.isValid(0)) {
                Employee employee = employeeDAO.find(punch.getBadge());
                Department department = employee.getDepartment();
                
                if (punch.getTerminalid() == department.getTerminalId() || punch.getTerminalid() == 0) {
                    
                    //Possibly temporary exception to check for original timestamp
                    if (punch.getOriginalTimestamp() == null) {
                        throw new DAOException("Punch original timestamp is null.");
                    }
                    
                    ps = conn.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, punch.getTerminalid());
                    ps.setString(2, punch.getBadge().getId());
                    ps.setTimestamp(3, Timestamp.valueOf(punch.getOriginalTimestamp()));
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
    
/**
 * <p>This is a method that accepts the badge of someone in the database and the date
 * that punches will be pulled from and returns all of the punches as an array list</p>
 * @param badge The badge of the person the user wants the punches of
 * @param date The date that the user wants punches from
 * @return All the punches from the date given by the badge given in an array list
 */
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
            boolean firstPunch = true;
            while (rs.next()) {
                
                int eventID = rs.getInt("eventtypeid");
                if (!firstPunch || (firstPunch && eventID == 1)) {
                    int id = rs.getInt("id");
                    int termID = rs.getInt("terminalid");
                    Timestamp time = rs.getTimestamp("timestamp");
                    EventType event = DAOUtility.getEventType(eventID);
                    Punch punch = new Punch(id, termID, badge, time.toLocalDateTime(), event);
                    punchList.add(punch);
                }
                firstPunch = false;
            }
            try {
                conn = daoFactory.getConnection();
                pst = conn.prepareStatement(QUERY_LIST);
                pst.setString(1, badge.getId());
                pst.setDate(2, Date.valueOf(date.plusDays(1)));
                rs = pst.executeQuery();
                if (rs.next()) {
                    int eventID = rs.getInt("eventtypeid");
                    if (eventID != 1){
                        int id = rs.getInt("id");
                        int termID = rs.getInt("terminalid");
                        Timestamp time = rs.getTimestamp("timestamp");
                        EventType event = DAOUtility.getEventType(eventID);
                        Punch punch = new Punch(id, termID, badge, time.toLocalDateTime(), event);
                        punchList.add(punch);
                    }
                }
            }
            catch (SQLException e) {
                throw new DAOException(e.getMessage());
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            DAOUtility.close(rs, pst);
        }
        return punchList;
    }

/**
 * <p>This is a method that takes two dates and a badge of someone in the database
 * and returns all of the punches in between the two dates in an array list</p>
 * @param badge The badge of the person who the user wants the punches of
 * @param startDate The beginning date for the punches the user wants to pull
 * @param endDate The end date for the punches the user wants to pull
 * @return All the punches from between the two dates as an array list
 */
    public ArrayList<Punch> list(Badge badge, LocalDate startDate, LocalDate endDate) {
        LocalDate dateCounter = startDate;
        ArrayList<Punch> rangePunchList = new ArrayList<>();
        rangePunchList.addAll(list(badge, dateCounter));
        while (dateCounter.isBefore(endDate)) {
            dateCounter = dateCounter.plusDays(1);
            rangePunchList.addAll(list(badge, dateCounter));
        }
        return rangePunchList;
    }
}



