package edu.jsu.mcis.cs310.tas_fa24;

/**
 * <p>This class provides the necessary methods to create and access a Badge object</p>
 * @author Jay Snellen
 */
public class Badge {

    /**
     * <p>These are the necessary attributes of a Badge object</P>
     * @param id This is the unique id that every badge in the database has
     * @param description This stores the name of who the badge belongs to
     */
    private final String id, description;

    public Badge(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append('#').append(id).append(' ');
        s.append('(').append(description).append(')');

        return s.toString();

    }

}
