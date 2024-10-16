package edu.jsu.mcis.cs310.tas_fa24.dao;

import edu.jsu.mcis.cs310.tas_fa24.Department;
import java.sql.*;

public class DepartmentDAO {

    private final DAOFactory daoFactory;

    public DepartmentDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Department find(int id) {

        Department department = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();
            String query = "SELECT * FROM department WHERE id = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            if (rs.next()) {
                int departmentId = rs.getInt("id");
                String description = rs.getString("description");
                int terminalId = rs.getInt("terminalid");

                department = new Department(departmentId, description, terminalId);
            }
        }
        catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        finally {
            DAOUtility.close(rs, pst);
        }

        return department;
    }
}