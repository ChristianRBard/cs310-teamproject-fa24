/**
 * Represents different types of employment.
 * Defines employment as either Part-Time or Full-Time.
 * @Author ChristianRBard
 */
package edu.jsu.mcis.cs310.tas_fa24;

public enum EmployeeType {
    /**
     * Constructs an EmployeeType enumeration value.
     *
     * @param description A description of the employment type.
     */
    PART_TIME("Temporary / Part-Time"),
    FULL_TIME("Full-Time");
    private final String description;
    
    private EmployeeType(String d) {
        description = d;
    }

    @Override
    public String toString() {
        return description;
    }
    
}
