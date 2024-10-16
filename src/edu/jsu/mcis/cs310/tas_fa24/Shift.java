package edu.jsu.mcis.cs310.tas_fa24;

import java.util.HashMap;
import java.time.LocalTime;
import java.sql.Timestamp;

public class Shift {
    private final int id;
    private final String description;
    private final LocalTime shiftStart;
    private final LocalTime shiftStop;
    private int roundInterval, gracePeriod, dockPenalty;
    private final LocalTime lunchStart;
    private final LocalTime lunchStop;
    private int lunchThreshold;
    HashMap<String, String> shiftMap = new HashMap<>();    
    public Shift(HashMap shiftMap) {
        
        
        
        this.id = (Integer)shiftMap.get("id");
        this.description = (String)shiftMap.get("description");
        this.shiftStart = LocalTime.parse((String)shiftMap.get("shiftstart"));
        this.shiftStop = LocalTime.parse((String)shiftMap.get("shiftstart"));
        this.roundInterval = (Integer)shiftMap.get("roundinterval");
        this.gracePeriod = (Integer)shiftMap.get("graceperiod");
        this.dockPenalty = (Integer)shiftMap.get("dockpenalty");
        this.lunchStart = LocalTime.parse((String)shiftMap.get("lunchstart"));
        this.lunchStop = LocalTime.parse((String)shiftMap.get("lunchstop"));
        this.lunchThreshold = (Integer)shiftMap.get("lunchthreshold");
    }

    public int getId() {
        return id;
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
        int shiftTime = 0;
        
        s.append(this.description).append(": ");
        s.append(this.shiftStart).append(" - ").append(this.shiftStop).append(" ");
        s.append("(").append(shiftTime).append(" minutes);");
        s.append("Lunch: ").append(this.lunchStart).append(this.lunchStop);
        s.append("(").append(this.lunchThreshold).append(" minutes)");
        
        
        return s.toString();
    }
}