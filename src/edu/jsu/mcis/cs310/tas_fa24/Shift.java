package edu.jsu.mcis.cs310.tas_fa24;

import java.util.HashMap;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Shift {
    private final String description;
    private final LocalTime shiftStart;
    private final LocalTime shiftStop;
    private final int roundInterval, gracePeriod, dockPenalty;
    private final LocalTime lunchStart;
    private final LocalTime lunchStop;
    private final int lunchThreshold;
    
    public Shift(HashMap<String, String> shiftMap) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
        
        this.description = (String)shiftMap.get("description");
        this.shiftStart = LocalTime.parse(shiftMap.get("shiftstart"), format);
        System.out.println(shiftStart);
        this.shiftStop = LocalTime.parse(shiftMap.get("shiftstop"), format);
        System.out.println(this.shiftStop);
        this.roundInterval = Integer.valueOf(shiftMap.get("roundinterval"));
        System.out.println(this.roundInterval);
        this.gracePeriod = Integer.valueOf(shiftMap.get("graceperiod"));
        System.out.println(this.gracePeriod);
        this.dockPenalty = Integer.valueOf(shiftMap.get("dockpenalty"));
        System.out.println(this.dockPenalty);
        this.lunchStart = LocalTime.parse(shiftMap.get("lunchstart"), format);
        System.out.println(this.lunchStart);
        this.lunchStop = LocalTime.parse(shiftMap.get("lunchstop"), format);
        System.out.println(this.lunchStop);
        this.lunchThreshold = Integer.valueOf(shiftMap.get("lunchthreshold"));
        System.out.println(this.lunchThreshold);
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