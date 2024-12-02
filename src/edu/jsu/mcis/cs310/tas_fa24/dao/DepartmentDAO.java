/**
 * <p>Provides data access methods for Department objects.</p>
 * @Author ChristianRBard
 */

package edu.jsu.mcis.cs310.tas_fa24.dao;

import edu.jsu.mcis.cs310.tas_fa24.Department;
import java.sql.*;

public class DepartmentDAO {
    
    /**
     * <p>Constructs a DepartmentDAO with a DAOFactory.</p>
     *
     * @param daoFactory The DAOFactory instance used for database connections.
     */

    private final DAOFactory daoFactory;
    
    public DepartmentDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public Department find(int id) { 
    /**
     * <p>Finds a Department object by its ID.</p>
     *
     * @param id The ID of the department to find.
     * @return The Department object if found, or null if not.
     */
        
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