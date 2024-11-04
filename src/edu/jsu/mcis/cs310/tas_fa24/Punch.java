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
       this.originaltimestamp = LocalDateTime.now();
    }
   
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
    public EventType getPunchtype (){
       return this.punchtype;
    }
    public LocalDateTime getOriginaltimestamp(){
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