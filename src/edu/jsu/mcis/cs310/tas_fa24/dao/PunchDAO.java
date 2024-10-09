package edu.jsu.mcis.cs310.tas_fa24.dao;

import edu.jsu.mcis.cs310.tas_fa24.Punch;
import edu.jsu.mcis.cs310.tas_fa24.Badge;
import edu.jsu.mcis.cs310.tas_fa24.EventType;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class PunchDAO {
    private static final String QUERY_FIND = "SELECT * FROM event WHERE id = ?";
    private static final String QUERY_LIST = "SELECT * FROM event WHERE badgeid = ? AND DATE(timestamp) = ?";

    private final DAOFactory daoFactory;

    public PunchDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Punch find(int id) {
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
                Punch punch = extractPunchFromResultSet(rs);
                punchList.add(punch);
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            DAOUtility.close(rs, pst);
        }

        return punchList;
    }

    private Punch extractPunchFromResultSet(ResultSet rs) throws SQLException {
        // Replace these fields with actual columns from your 'event' table
        int id = rs.getInt("id");
        String badgeId = rs.getString("badgeid");
        String eventType = rs.getString("eventtype");
        Timestamp timestamp = rs.getTimestamp("timestamp");

        // Create and return a new Punch object
        return new Punch(id, badgeId, EventType.valueOf(eventType.toUpperCase()), timestamp.toLocalDateTime());
    }
}