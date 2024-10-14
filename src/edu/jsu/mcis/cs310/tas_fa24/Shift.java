package edu.jsu.mcis.cs310.tas_fa24;

import java.time.LocalTime;

public class Shift {
    private int id;
    private String description;
    private LocalTime shiftStart;
    private LocalTime shiftStop;

    public Shift(int id, String description, LocalTime shiftStart, LocalTime shiftStop) {
        this.id = id;
        this.description = description;
        this.shiftStart = shiftStart;
        this.shiftStop = shiftStop;
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
}