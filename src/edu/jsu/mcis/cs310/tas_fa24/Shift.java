package edu.jsu.mcis.cs310.tas_fa24;

import java.util.HashMap;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
/**
 * <p>Class used to create a Shift object which stores the details about a shift.</p>
 * @author Caden Parrish
 */
public class Shift {
    private final String description;
    private final LocalTime shiftStart;
    private final LocalTime shiftStop;
    private final int roundInterval, gracePeriod, dockPenalty;
    private final LocalTime lunchStart;
    private final LocalTime lunchStop;
    private final int lunchThreshold;
/**
 * <p>Creates a shift object</p>
 * @param shiftMap A list of details about the shift to be converted to the object
 */
    public Shift(HashMap<String, String> shiftMap) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
        
        this.description = (String)shiftMap.get("description");
        this.shiftStart = LocalTime.parse(shiftMap.get("shiftstart"), format);
        this.shiftStop = LocalTime.parse(shiftMap.get("shiftstop"), format);
        this.roundInterval = Integer.valueOf(shiftMap.get("roundinterval"));
        this.gracePeriod = Integer.valueOf(shiftMap.get("graceperiod"));
        this.dockPenalty = Integer.valueOf(shiftMap.get("dockpenalty"));
        this.lunchStart = LocalTime.parse(shiftMap.get("lunchstart"), format);
        this.lunchStop = LocalTime.parse(shiftMap.get("lunchstop"), format);
        this.lunchThreshold = Integer.valueOf(shiftMap.get("lunchthreshold"));
    }

    public String getDescription() {
        return description;
    }

    public LocalTime getShiftStart() {
        return shiftStart;
    }

    public LocalTime getShiftStop() {
        return shiftStop;
    }
    
    public int getRoundInterval() {
        return this.roundInterval;
    }
    public int getGracePeriod() {
        return this.gracePeriod;
    }
    public int getDockPenalty() {
        return this.dockPenalty;
    }
    public LocalTime getLunchStart() {
        return this.lunchStart;
    }
    public LocalTime getLunchStop() {
        return this.lunchStop;
    }
    public int getLunchThreshold() {
        return this.lunchThreshold;
    }
/**
 * <p>Prints the details about the shift. Formats it to be readable</p>
 * @return Prints details about the shift
 */
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        int shiftTime = (this.shiftStop.toSecondOfDay() - this.shiftStart.toSecondOfDay()) / 60;
        int lunchTime = (this.lunchStop.toSecondOfDay() - this.lunchStart.toSecondOfDay()) / 60;
        
        s.append(this.description).append(": ");
        s.append(this.shiftStart).append(" - ").append(this.shiftStop).append(" ");
        s.append("(").append(shiftTime).append(" minutes);");
        s.append(" Lunch: ").append(this.lunchStart).append(" - ").append(this.lunchStop);
        s.append(" (").append(lunchTime).append(" minutes)");

        return s.toString();
    }
}