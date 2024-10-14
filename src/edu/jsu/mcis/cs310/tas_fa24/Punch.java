package edu.jsu.mcis.cs310.tas_fa24;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class Punch {
    private final int terminalid;
    private int id;
    private final Badge badgeId;
    private final EventType punchtype;
    private LocalDateTime originaltimestamp;
    
    public Punch(int terminalid, Badge badgeID, EventType punchType){
       this.terminalid = terminalid;
       this.badgeId = badgeID;
       this.punchtype = punchType;
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
       return s.toString();
   }
}

/*public class Punch {
    private int id;
    private String badgeId;
    private EventType eventType; // Make sure you have an EventType enum defined
    private LocalDateTime originalTimestamp;

    public Punch(int id, String badgeId, EventType eventType, LocalDateTime originalTimestamp) {
        this.id = id;
        this.badgeId = badgeId;
        this.eventType = eventType;
        this.originalTimestamp = originalTimestamp;
    }

    public int getId() {
        return id;
    }

    public String getBadgeId() {
        return badgeId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public LocalDateTime getOriginalTimestamp() {
        return originalTimestamp;
    }
}*/
