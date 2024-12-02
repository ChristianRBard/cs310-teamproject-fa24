package edu.jsu.mcis.cs310.tas_fa24;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
/**
 * <p>Class used for creating and using Punch Objects<p>
 * @author caden
 */
public class Punch {
    private final int terminalid;
    private int id;
    private final Badge badgeId;
    private final EventType punchtype;
    private final LocalDateTime originaltimestamp;
    private LocalDateTime adjustedtimestamp;
    private PunchAdjustmentType adjustmenttype = null;
/**
* <p>Constructor for creating a punch that has not been adjusted<p>
* @param terminalid Terminal used for creating a punch
* @param badgeID Badge id of Employee creating a punch
* @param punchType Punch Type
*/
    public Punch(int terminalid, Badge badgeID, EventType punchType){
       this.terminalid = terminalid;
       this.badgeId = badgeID;
       this.punchtype = punchType;
       this.originaltimestamp = LocalDateTime.now();
    }
/**
* <p>Constructor for creating a punch that has not been adjusted<p>
* @param id Id of employee creating punch
* @param terminalid Terminal used for creating a punch
* @param badgeID Badge id of Employee creating a punch
* @param  originaltimestamp Original time the punch was created
* @param punchType Punch Type
*/  
    public Punch(Integer id, int terminalid, Badge badgeID, LocalDateTime originaltimestamp, EventType punchType){
       this.id = id;
       this.terminalid = terminalid;
       this.badgeId = badgeID;
       this.punchtype = punchType;
       this.originaltimestamp = originaltimestamp;
    }

    public int getTerminalid (){
       return this.terminalid;
    }
    public int getID (){
       return this.id;
    }
    public Badge getBadge(){
       return this.badgeId;
    }
    public EventType getPunchtype(){
       return this.punchtype;
    }
    public LocalDateTime getOriginalTimestamp(){
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
/**
 * <p>Adjust the punches of employees based on a set of rules provided by the sql file. Tells the type of adjustment made.<p>
 * @param s Shift
 */
    public void adjust(Shift s){
        LocalDateTime ogt = getOriginalTimestamp();
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
        ArrayList<Integer> intervals = new ArrayList<>();
        intervals.add(0, 00);
        intervals.add(1, 15);
        intervals.add(2, 30);
        intervals.add(3, 45);

        switch(getPunchtype().ordinal()){
            case 0://Clock Out
                System.out.println("Clock Out");
                System.out.println(ogtToDate + " " + ogtToDate.getDayOfWeek());
                if(!ogtToDate.getDayOfWeek().toString().toLowerCase().equals("saturday") && !ogtToDate.getDayOfWeek().toString().toLowerCase().equals("sunday")){
                
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
                    
                    if(ogtToTime.isAfter(shiftStop.minusMinutes(dockPenalty + 1)) && ogtToTime.isBefore(shiftStop.minusMinutes(gracePeriod))){
                        System.out.println("DockPenalty");
                        ogtToTime = shiftStop.minusMinutes(dockPenalty);
                        adjType = PunchAdjustmentType.SHIFT_DOCK;
                        break;
                    }
                    
                }
                System.out.println("Before Interval Round");
                if(!intervals.contains(ogtToTime.getMinute())){
                    int roundedSeconds = (((ogtToTime.getMinute() * 60 + ogtToTime.getSecond()) + 450) / 900) * 900;
                    int roundedMinutes = roundedSeconds / 60;
                    System.out.println(roundedMinutes);
                    System.out.println(roundedSeconds);
                    
                    int roundedHours = ogtToTime.getHour() + (roundedMinutes / 60);
                    System.out.println(roundedHours);
                    roundedMinutes = roundedMinutes % 60;
                    ogtToTime = LocalTime.of(roundedHours, roundedMinutes);
                    System.out.println(ogtToTime);
                    System.out.println("Interval Round");
                    adjType = PunchAdjustmentType.INTERVAL_ROUND;
                    break;
                }
                    ogtToTime = LocalTime.of(ogtToTime.getHour(), ogtToTime.getMinute(), 00);
                    break;
            case 1://Clock In
                System.out.println("Clock In");

                if(!ogtToDate.getDayOfWeek().toString().toLowerCase().equals("saturday") && !ogtToDate.getDayOfWeek().toString().toLowerCase().equals("sunday")){
                
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
                    
                    if(ogtToTime.isAfter(shiftStart.plusMinutes(gracePeriod)) && ogtToTime.isBefore(shiftStart.plusMinutes(dockPenalty - 1))){
                        System.out.println("DockPenalty");
                        ogtToTime = shiftStart.plusMinutes(dockPenalty);
                        adjType = PunchAdjustmentType.SHIFT_DOCK;
                        break;
                    }
                }
                
                if(!intervals.contains(ogtToTime.getMinute())){
                    int roundedSeconds = (((ogtToTime.getMinute() * 60 + ogtToTime.getSecond()) + 450) / 900) * 900;
                    int roundedMinutes = roundedSeconds / 60;
                    System.out.println(roundedMinutes);
                    int roundedHours = ogtToTime.getHour() + (roundedMinutes / 60);
                    roundedMinutes = roundedMinutes % 60;
                    ogtToTime = LocalTime.of(roundedHours, roundedMinutes);
                    System.out.println(ogtToTime);
                    System.out.println("Interval Round");
                    adjType = PunchAdjustmentType.INTERVAL_ROUND;
                    break;
                }
                
                ogtToTime = LocalTime.of(ogtToTime.getHour(), ogtToTime.getMinute(), 00);
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
/**
 * <p>Prints the original punch with no adjustments. Formats it to readable.<p>
 * @return Original punch without adjustments
 */
   public String printOriginal(){
       StringBuilder s = new StringBuilder();
       DateTimeFormatter format = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
       String fixedDate = originaltimestamp.format(format).toUpperCase();
       s.append("#").append(badgeId.getId()).append(" ");
       s.append(punchtype);
       s.append(": ").append(fixedDate);
       return s.toString();
   }
/**
 * <p>Prints the adjusted punch. Formats it to be readable.<p>
 * @return Adjusted punch
 */
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

