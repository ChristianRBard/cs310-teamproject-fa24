package edu.jsu.mcis.cs310.tas_fa24;
/**
 * <p>This class creates an object to store the type of adjustments that are made to the punches</p>
 * @author Jay Snellen
 */
public enum PunchAdjustmentType {
    /**
     * <p>The necessary adjustment types based on all the types that can be made</p>
     */
    NONE("None"),
    SHIFT_START("Shift Start"),
    SHIFT_STOP("Shift Stop"),
    SHIFT_DOCK("Shift Dock"),
    LUNCH_START("Lunch Start"),
    LUNCH_STOP("Lunch Stop"),
    INTERVAL_ROUND("Interval Round");

    private final String description;

    private PunchAdjustmentType(String d) {
        description = d;
    }

    @Override
    public String toString() {
        return description;
    }

}
