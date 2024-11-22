package edu.jsu.mcis.cs310.tas_fa24.dao;

import edu.jsu.mcis.cs310.tas_fa24.Badge;
import edu.jsu.mcis.cs310.tas_fa24.Shift;
import java.sql.*;
import java.util.HashMap;

/**
 * <p>Class for getting shift types<p>
 * @author caden
 */
public class ShiftDAO {
    private final String QUERY_FIND = "SELECT * FROM shift WHERE id = ?";
    private final String EMPLOYEE_SHIFT_FIND = "SELECT * FROM employee WHERE badgeid = ?";
    private final DAOFactory daoFactory;

    public ShiftDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    /**
     * <p>This method gets the shift type for an employee based on their id<p>
     * @param id Employee id
     * @return Returns the shift type in a Shift object
     */
    public Shift find(int id){
        Shift shift = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            Connection conn = daoFactory.getConnection();
            if(conn.isValid(0)){
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, id);
                
                rs = ps.executeQuery();
                if(rs.next()){
                    shift = extractShiftFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            DAOUtility.close(rs, ps);
        }

        return shift;
    }
    /**
     * <p>This method takes the badge of the employee and uses it to get the id. Then it passes that to the original find function<p>
     * @param badgeid Employees badge id
     * @return Returns the shift type in a Shift object
     */
    public Shift find(Badge badgeid){
        Integer shiftType = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            Connection conn = daoFactory.getConnection();
            if(conn.isValid(0)){
                
                ps = conn.prepareStatement(EMPLOYEE_SHIFT_FIND);
                ps.setString(1, badgeid.getId());
                
                rs = ps.executeQuery();
                if(rs.next()){
                    shiftType = rs.getInt("shiftid");
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            DAOUtility.close(rs, ps);
        }

        return daoFactory.getShiftDAO().find(shiftType);
    }
    /**
     * <p>This function is what actually gets the information from the sql file. It then creates a new Shift object and return it.<p>
     * @param rs Result set retrieved from query
     * @return Shift object
     * @throws SQLException 
     */
    private Shift extractShiftFromResultSet(ResultSet rs) throws SQLException {
        HashMap<String, String> shiftMap = new HashMap<>();
        String description = rs.getString("description");
        String shiftStart = rs.getString("shiftstart");
        String shiftStop = rs.getString("shiftStop");
        String roundInterval = rs.getString("roundinterval");
        String gracePeriod = rs.getString("graceperiod");
        String dockPenalty = rs.getString("dockpenalty");
        String lunchStart = rs.getString("lunchstart");
        String lunchStop = rs.getString("lunchstop");
        String lunchThreshold = rs.getString("lunchthreshold");
                    
        shiftMap.put("description", description);
        shiftMap.put("shiftstart", shiftStart);
        shiftMap.put("shiftstop", shiftStop);
        shiftMap.put("roundinterval", roundInterval);
        shiftMap.put("graceperiod", gracePeriod);
        shiftMap.put("dockpenalty", dockPenalty);
        shiftMap.put("lunchstart", lunchStart);
        shiftMap.put("lunchstop", lunchStop);
        shiftMap.put("lunchthreshold", lunchThreshold);

        return new Shift(shiftMap);
    }
}