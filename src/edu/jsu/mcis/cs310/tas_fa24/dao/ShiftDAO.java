package edu.jsu.mcis.cs310.tas_fa24.dao;

import edu.jsu.mcis.cs310.tas_fa24.Badge;
import edu.jsu.mcis.cs310.tas_fa24.Shift;
import edu.jsu.mcis.cs310.tas_fa24.Employee;
import java.sql.*;
import java.util.HashMap;

public class ShiftDAO {
    private final String QUERY_FIND = "SELECT * FROM shift WHERE id = ?";

    private final DAOFactory daoFactory;

    public ShiftDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    

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
    
    public Shift find(Badge badgeid){
        Shift shift = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
        
        Employee emp1 = employeeDAO.find(badgeid);
        Integer shiftType = emp1.getShiftId();

        try{
            Connection conn = daoFactory.getConnection();
            if(conn.isValid(0)){
                
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, shiftType);
                
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