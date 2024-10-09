package edu.jsu.mcis.cs310.tas_fa24;

public class Badge {

    private final String id, description;
    private final int shiftId;

    public Badge(String id, String description, int shiftId) {
        this.id = id;
        this.description = description;
        this.shiftId = shiftId;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
    
    public int getShiftId() {
        return shiftId;
    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append('#').append(id).append(' ');
        s.append('(').append(description).append(')');

        return s.toString();

    }

}
