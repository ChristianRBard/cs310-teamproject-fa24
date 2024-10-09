package edu.jsu.mcis.cs310.tas_fa24.dao;

import edu.jsu.mcis.cs310.tas_fa24.EmployeeType;
import java.sql.*;

public class EmployeeTypeDAO {

    private final DAOFactory daoFactory;

    public EmployeeTypeDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public EmployeeType find(int id) {

        EmployeeType employeeType = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();
            String query = "SELECT * FROM employeetype WHERE id = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            if (rs.next()) {
                String description = rs.getString("description");

                employeeType = EmployeeType.valueOf(description.toUpperCase().replace(" / ", "_").replace("-", "_"));
            }
        }
        catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        finally {
            DAOUtility.close(rs, pst);
        }

        return employeeType;
    }
}