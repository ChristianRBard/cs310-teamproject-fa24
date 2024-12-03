/**
 * <p>Provides data access methods for Employee objects.</p>
 */

package edu.jsu.mcis.cs310.tas_fa24.dao;

import edu.jsu.mcis.cs310.tas_fa24.Employee;
import edu.jsu.mcis.cs310.tas_fa24.Department;
import edu.jsu.mcis.cs310.tas_fa24.Badge;
import java.sql.*;

public class EmployeeDAO {
    /**
     * <p>Constructs an EmployeeDAO with a DAOFactory.</p>
     *
     * @param daoFactory The DAOFactory instance used for database connections.
     */

    private final DAOFactory daoFactory;

    public EmployeeDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Employee find(int id) {
     /**
     * <p>Finds an Employee object by its ID.</p>
     *
     * @param id The ID of the employee to find.
     * @return The Employee object if found, or null if not.
     */
        Employee employee = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();
            String query = "SELECT * FROM employee WHERE id = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            if (rs.next()) {
                employee = extractEmployeeFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            DAOUtility.close(rs, pst);
        }

        return employee;
    }

    public Employee find(Badge badge) { 
     /**
     * <p>Finds an Employee object by a Badge object.</p>
     *
     * @param badge The Badge object used to locate the employee.
     * @return The Employee object if found, or null if not.
     */
        Employee employee = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();
            String query = "SELECT * FROM employee WHERE badgeid = ?";
            pst = conn.prepareStatement(query);
            pst.setString(1, badge.getId());
            rs = pst.executeQuery();

            if (rs.next()) {
                employee = extractEmployeeFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            DAOUtility.close(rs, pst);
        }

        return employee;
    }

    private Employee extractEmployeeFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String firstName = rs.getString("firstname");
        String middleName = rs.getString("middlename");
        String lastName = rs.getString("lastname");
        String badgeId = rs.getString("badgeid");
        int employeeTypeId = rs.getInt("employeetypeid");
        int departmentId = rs.getInt("departmentid");
        int shiftId = rs.getInt("shiftid");
        Date active = rs.getDate("active");

        DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();
        Department department = departmentDAO.find(departmentId);

        return new Employee(id, badgeId, firstName, middleName, lastName, employeeTypeId, department, shiftId, active);
    }
}