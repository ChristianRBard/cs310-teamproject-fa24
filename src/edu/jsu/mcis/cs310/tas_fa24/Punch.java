package edu.jsu.mcis.cs310.tas_fa24;

import edu.jsu.mcis.cs310.tas_fa24.dao.DAOFactory;
import edu.jsu.mcis.cs310.tas_fa24.dao.EmployeeDAO;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class Punch {
    private final int terminalid;
    private int id;
    private final Badge badgeId;
    private final EventType punchtype;
    private final LocalDateTime originaltimestamp;
    private final LocalDateTime adjustedtimestamp = null;
    private PunchAdjustmentType adjustmenttype;
    
    public Punch(int terminalid, Badge badgeID, EventType punchType){
       this.terminalid = terminalid;
       this.badgeId = badgeID;
       this.punchtype = punchType;
       this.originaltimestamp = LocalDateTime.now();
    }
   
    public Punch(Integer id, int terminalid, Badge badgeID, LocalDateTime originaltimestamp, EventType punchType){
       this.id = id;
       this.terminalid = terminalid;
       this.badgeId = badgeID;
       this.punchtype = punchType;
       this.originaltimestamp = originaltimestamp;
    }
   
    public int getTerminalID (){
       return this.terminalid;
    }
    public int getID (){
       return this.id;
    }
    public Badge getBadgeID(){
       return this.badgeId;
    }
    public EventType getPunchType (){
       return this.punchtype;
    }
    public LocalDateTime getOriginalTimeStamp(){
       return this.originaltimestamp;
    }
    
    public Boolean isBetween(LocalTime ogt, LocalTime after, LocalTime before){
        Boolean truthStatement = false;
        
        if(ogt.isBefore(before)||ogt.isAfter(after)){
            truthStatement = true;
        }
        
        return truthStatement;
    }
        
    public void adjust(Shift s){
        System.out.println(s.getDescription());
        LocalDateTime ogt = getOriginalTimeStamp();
        LocalTime ogtToTime = ogt.toLocalTime();
        int gracePeriod = s.getGracePeriod();
        int dockPenalty = s.getDockPenalty();
        int intervalRound = s.getRoundInterval();
        LocalTime before = ogtToTime.minusMinutes(gracePeriod);
        LocalTime after = ogtToTime.plusMinutes(gracePeriod);
        LocalTime shiftStart = s.getShiftStart();
        LocalTime lunchStop = s.getLunchStop();
        
        switch(getPunchType().ordinal()){
            case 0://Clock Out
                System.out.println("case 2");
                
                if(isBetween(ogtToTime, before, after)){
                    System.out.println("Shift Stop");
                    ogtToTime = shiftStop;
                    System.out.println(ogtToTime);
                }
                
                if(isBetween(ogtToTime, LocalTime.of(12, 00, 00), LocalTime.of(12, 30, 00))){
                    System.out.println("Lunch Punch In");
                    ogtToTime = lunchStop;
                }
                
                
                
                break;
            case 1://Clock In

                if(isBetween(ogtToTime, before, after)){
                    System.out.println("Shift Start");
                    ogtToTime = shiftStart;
                    System.out.println(ogtToTime);
                }
                
                if(isBetween(ogtToTime, LocalTime.of(12, 00, 00), LocalTime.of(12, 30, 00))){
                    System.out.println("Lunch Punch In");
                    ogtToTime = lunchStop;
                }
                
                break;
            case 2://Time out means they forgot to clock out and the system handles it after a certain time has passed.
                
            default:
                System.out.println("Invalid punch type!");
                break;          
        }
    }
   
   @Override
   public String toString(){
       return printOriginal();
   }
   
   public String printOriginal(){
       StringBuilder s = new StringBuilder();
       DateTimeFormatter format = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
       String fixedDate = originaltimestamp.format(format).toUpperCase();
       System.out.println(originaltimestamp);
       //s.append(id).append(" ");
       s.append("#").append(badgeId.getId()).append(" ");
       s.append(punchtype);
       s.append(": ").append(fixedDate);
       
       System.out.println(getPunchType());
       return s.toString();
   }
}
