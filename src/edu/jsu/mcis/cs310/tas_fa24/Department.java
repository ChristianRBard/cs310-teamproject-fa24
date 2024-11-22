/**
 * Represents a department within an organization.
 * Contains details about the department ID, description, and terminal ID.
 * @author ChristianRBard
 */
package edu.jsu.mcis.cs310.tas_fa24;

public class Department {
    /**
     * Constructs a Department object.
     *
     * @param id The unique identifier of the department.
     * @param description A brief description of the department.
     * @param terminalId The terminal ID associated with the department.
     */
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