package edu.jsu.mcis.cs310.tas_fa24.dao;

import edu.jsu.mcis.cs310.tas_fa24.Shift;
import edu.jsu.mcis.cs310.tas_fa24.Badge;
import java.sql.*;

public class ShiftDAO {

    /*private final DAOFactory daoFactory;

    public ShiftDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Shift find(Badge badge) {
        Shift shift = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();
            String query = "SELECT * FROM shift WHERE id = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, badge.getShiftId());
            rs = pst.executeQuery();

            if (rs.next()) {
                shift = extractShiftFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            DAOUtility.close(rs, pst);
        }

        return shift;
    }

    private Shift extractShiftFromResultSet(ResultSet rs) throws SQLException {
        // Replace these fields with actual columns from your 'shift' table
        int id = rs.getInt("id");
        String description = rs.getString("description");
        Time shiftStart = rs.getTime("shiftstart");
        Time shiftStop = rs.getTime("shiftstop");

        return new Shift(id, description, shiftStart.toLocalTime(), shiftStop.toLocalTime());
    }*/
}