package edu.jsu.mcis.cs310.tas_fa24;

import java.time.*;

public class Punch {
   private int terminalid, id;
   private Badge badgeId;
   private EventType punchtype;
   private LocalDateTime originaltimestamp;
    
   public Punch(int terminalid, Badge badgeID, EventType punchType){
       this.terminalid = terminalid;
       this.badgeId = badgeID;
       this.punchtype = punchType;
   }
   
   public Punch(int id, int terminalid, Badge badgeID, LocalDateTime originaltimestamp, EventType punchType){
       this.terminalid = terminalid;
       this.badgeId = badgeID;
       this.punchtype = punchType;
       this.originaltimestamp = originaltimestamp;
   }
   
   public void setTerminalID (int terminalID){
       this.terminalid = terminalID;
   }
   public void setID (int ID){
       this.id = ID;
   }
   public void setBadgeID(Badge badgeID){
       this.badgeId = badgeID;
   }
   public void setPunchType (EventType punchType){
       this.punchtype = punchType;
   }
   public void setOriginalTimeStamp(LocalDateTime originaltimestamp){
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
       StringBuilder s = new StringBuilder();
       
       
       
       return s.toString();
   }
}
