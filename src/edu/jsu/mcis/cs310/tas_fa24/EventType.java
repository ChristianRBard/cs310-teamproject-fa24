package edu.jsu.mcis.cs310.tas_fa24;
/**
 * <p>This class creates an object to store the type of punch that was made</p>
 * @author Jay Snellen
 */
public enum EventType {
    /**
     * <p>The necessary types of punches that can be made</p>
     */
    CLOCK_OUT("CLOCK OUT"),
    CLOCK_IN("CLOCK IN"),
    TIME_OUT("TIME OUT");

    private final String description;

    private EventType(String d) {
        description = d;
    }

    @Override
    public String toString() {
        return description;
    }

}
