package edu.jsu.mcis.cs310.tas_fa24;

public class Department {

    private int id;
    private String description;
    private int terminalId;

    public Department(int id, String description, int terminalId) {
        this.id = id;
        this.description = description;
        this.terminalId = terminalId;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getTerminalId() {
        return terminalId;
    }

    @Override
    public String toString() {
        return "#" + id + " (" + description + "), Terminal ID: " + terminalId;
    }
}