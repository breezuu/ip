package nexus.tasks;

/**
 * Represents a note with content.
 */
public class Note extends Task {
    private final String prefix = "[N] ";

    /**
     * Creates a new note with the given content and completion status.
     *
     * @param desc The content of the note. Cannot be null.
     * @param isDone      The completion status of the note.
     */
    public Note(String desc, boolean isDone) {
        super(desc, isDone);
    }

    /**
     * Returns the type identifier for a Note object.
     *
     * @return The type identifier, which is "N" for a Note object.
     */
    public String getType() {
        return "N";
    }

    /**
     * Returns the content of the note.
     *
     * @return The content of the note.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns a string representation of the note for saving.
     *
     * @return The string representation of the note for saving.
     */
    public String saveString() {
        return "N" + " | " + this.description;
    }

    @Override
    public String toString() {
        return prefix + this.description;
    }
}
