package edu.jsu.mcis.cs310.tas_fa24.dao;

import edu.jsu.mcis.cs310.tas_fa24.Badge;
import java.sql.*;

public class BadgeDAO {

    private static final String QUERY_FIND = "SELECT * FROM badge WHERE id = ?";

    private final DAOFactory daoFactory;

    BadgeDAO(DAOFactory daoFactory) {

        this.daoFactory = daoFactory;

    }

    public Badge find(String id) {
        Badge badge = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();
            pst = conn.prepareStatement(QUERY_FIND);
            pst.setString(1, id);
            rs = pst.executeQuery();

            if (rs.next()) {
                String badgeId = rs.getString("id");
                String description = rs.getString("description");
                badge = new Badge(badgeId, description);
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            DAOUtility.close(rs, pst);
        }

        return badge;
    }

}
