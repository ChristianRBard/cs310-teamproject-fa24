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
    private LocalDateTime adjustedtimestamp = null;
    private PunchAdjustmentType adjustmenttype = null;
    
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
    public LocalDateTime getAdjustedTimeStamp(){
        return this.adjustedtimestamp;
    }
    public void setAdjustedTimestamp(LocalDateTime adjTimeStamp){
        this.adjustedtimestamp = adjTimeStamp;
    }
    public PunchAdjustmentType getPunchAdjustmentType(){
        return this.adjustmenttype;
    }
    public void setPunchAdjustmentType(PunchAdjustmentType adjType){
        this.adjustmenttype = adjType;
    }
    
    /*public Boolean isBetween(LocalTime ogt, LocalTime b, LocalTime a){
        Boolean truthStatement = false;
        
        if(ogt.isBefore(a)&&ogt.isAfter(b)){
            truthStatement = true;
        }
        
        return truthStatement;
    }*/
        
    public void adjust(Shift s){
        LocalDateTime ogt = getOriginalTimeStamp();
        LocalTime ogtToTime = ogt.toLocalTime();
        LocalDate ogtToDate = ogt.toLocalDate();
        final int gracePeriod = s.getGracePeriod();
        final int dockPenalty = s.getDockPenalty();
        final int intervalRound = s.getRoundInterval();
        final LocalTime shiftStart = s.getShiftStart();
        final LocalTime shiftStop = s.getShiftStop();
        final LocalTime lunchStart = s.getLunchStart();
        final LocalTime lunchStop = s.getLunchStop();
        PunchAdjustmentType adjType = PunchAdjustmentType.NONE;
        
        switch(getPunchType().ordinal()){
            case 0://Clock Out
                System.out.println("Clock Out");
                
                if(ogtToTime.isAfter(shiftStop.minusMinutes(gracePeriod))&&ogtToTime.isBefore(shiftStop.plusMinutes(intervalRound))){
                    System.out.println("Shift Stop");
                    ogtToTime = shiftStop;
                    System.out.println(ogtToTime);
                    adjType = PunchAdjustmentType.SHIFT_STOP;
                    break;
                }
                
                if(ogtToTime.isAfter(lunchStart) && ogtToTime.isBefore(lunchStop)){
                    System.out.println("Lunch Punch Out");
                    ogtToTime = lunchStart;
                    adjType = PunchAdjustmentType.LUNCH_START;
                    break;
                }
                
                /*if(ogtToTime.isAfter(shiftStop.plusMinutes(15))){
                    System.out.println("Interval Round Clock Out");
                    ogtToTime = shiftStop;
                    adjType = PunchAdjustmentType.INTERVAL_ROUND;
                    break;
                }*/
                
                /*if(isBetween(ogtToTime, before, after)){
                    System.out.println("DockPenalty");
                    ogtToTime = shiftStop.minusMinutes(dockPenalty);
                    adjType = PunchAdjustmentType.SHIFT_DOCK;
                    break;
                }
                
                if(ogtToTime.equals(shiftStop)){
                    System.out.println("No Adjustment Needed Clock Out");
                    ogtToTime = shiftStop;
                    break;
                }*/
                
                break;
            case 1://Clock In
                System.out.println("Clock In");

                if(ogtToTime.isBefore(shiftStart.plusMinutes(gracePeriod))&&ogtToTime.isAfter(shiftStart.minusMinutes(intervalRound))){
                    System.out.println("Shift Start");
                    ogtToTime = shiftStart;
                    System.out.println(ogtToTime);
                    adjType = PunchAdjustmentType.SHIFT_START;
                    break;
                }
                
                if(ogtToTime.isAfter(lunchStart) && ogtToTime.isBefore(lunchStop)){
                    ogtToTime = lunchStop;
                    adjType = PunchAdjustmentType.LUNCH_STOP;
                    break;
                }
                
                /*if(ogtToTime.isBefore(shiftStart.minusMinutes(15))){
                    System.out.println("Interval Round Clock In");
                    ogtToTime = shiftStart;
                    adjType = PunchAdjustmentType.INTERVAL_ROUND;
                    break;
                }*/
                
                
                /*if(!isBetween(ogtToTime, before, after)){
                    System.out.println("DockPenalty");
                    ogtToTime = shiftStart.plusMinutes(dockPenalty);
                    adjType = PunchAdjustmentType.SHIFT_DOCK;
                    break;
                }
                
                if(ogtToTime.equals(shiftStart)){
                    System.out.println("No Adjustment Needed Clock In");
                    ogtToTime = shiftStart;
                    adjType = PunchAdjustmentType.NONE;
                    break;
                }*/

                break;
            default:
                System.out.println("Invalid punch type!");
                break;  
        }
        setAdjustedTimestamp(ogtToTime.atDate(ogtToDate));
        setPunchAdjustmentType(adjType);
    }
   
   @Override
   public String toString(){
       return printOriginal();
   }
   
   public String printOriginal(){
       StringBuilder s = new StringBuilder();
       DateTimeFormatter format = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
       String fixedDate = originaltimestamp.format(format).toUpperCase();
       s.append("#").append(badgeId.getId()).append(" ");
       s.append(punchtype);
       s.append(": ").append(fixedDate);
       return s.toString();
   }
   
   public String printAdjusted(){
       StringBuilder s = new StringBuilder();
       DateTimeFormatter format = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
       String fixedDate = adjustedtimestamp.format(format).toUpperCase();
       s.append("#").append(badgeId.getId()).append(" ");
       s.append(punchtype);
       s.append(": ").append(fixedDate).append(" (").append(adjustmenttype).append(")");
       return s.toString();
   }
}
