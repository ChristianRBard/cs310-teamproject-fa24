package edu.jsu.mcis.cs310.tas_fa24;

import java.time.LocalDateTime;

public class Punch {
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
}