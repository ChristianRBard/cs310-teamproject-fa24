package edu.jsu.mcis.cs310.tas_fa24.dao;
import edu.jsu.mcis.cs310.tas_fa24.Punch;
import edu.jsu.mcis.cs310.tas_fa24.Badge;
import edu.jsu.mcis.cs310.tas_fa24.EventType;
import java.sql.*;
import java.time.LocalDateTime;

public class PunchDAO {
    private static final String QUERY_FIND = "SELECT * FROM event WHERE terminalid = ? AND badgeid = ? AND eventtypeid = ?";
    
    private final DAOFactory daoFactory;
    
    PunchDAO(DAOFactory daoFactory){
        this.daoFactory = daoFactory;
    }
    
    public Punch find(int id){
        Punch punch = null;
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            Connection conn = daoFactory.getConnection();
            
            if(conn.isValid(0)){
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, id);
                
                Boolean hasResults = ps.execute();
                
                if(hasResults){
                    rs = ps.getResultSet();
                    while(rs.next()){
                        int termid = rs.getInt("terminalid");
                        String badgeid = rs.getString("badgeid");
                        String timestamp = rs.getString("timestamp");
                        int eventtype = rs.getInt("eventtypeid");
                        Badge bad = badgeDAO.find(badgeid);
                        LocalDateTime timestamp2 = LocalDateTime.parse(timestamp);
                        
                        punch = new Punch(id, termid, bad, timestamp2, EventType.valueOf(eventtype));
                        
                    }
                }
                
                
            }
        }catch (SQLException e) {

            throw new DAOException(e.getMessage());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }

        }
        return punch;
        
    }
}
