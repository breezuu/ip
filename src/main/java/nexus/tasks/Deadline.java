package nexus.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Deadline class which represents a task with a deadline.
 */
public class Deadline extends Task {
    private final LocalDateTime deadlineTime;
    private final DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("d/M/yyyy h:mm a", Locale.ENGLISH);
    private final DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy h:mm a", Locale.ENGLISH);

    /**
     * Constructs a Deadline object with the specified description, deadline, and completion status.
     * @param desc Description of the deadline task.
     * @param deadline Deadline date and time in the format (d/M/yyyy h:mm a).
     * @param isDone Completion status of the deadline task.
     */
    public Deadline(String desc, String deadline, boolean isDone) {
        super(desc, isDone);
        this.deadlineTime = LocalDateTime.parse(deadline, inputFormat);
    }

    /**
     * Checks if the deadline falls on the specified date.
     * @param date Date in the format (d/M/yyyy).
     * @return True if the deadline falls on the specified date, false otherwise.
     */
    @Override
    public boolean isValidDateWindow(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        return this.deadlineTime.format(formatter).equals(date);
    }

    /**
     * Returns the type of the task as a string.
     * @return Type of the task as "D".
     */
    @Override
    public String getType() {
        return "D";
    }

    /**
     * Returns a string representation of the Deadline task.
     * @return String representation of the Deadline task.
     */
    @Override
    public String saveString() {
        return "D" + " | " + (isDone ? "1" : "0") + " | " + description + " | " + deadlineTime.format(inputFormat);
    }

    /**
     * Returns a string representation of the Deadline task.
     * @return String representation of the Deadline task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadlineTime.format(outputFormat) + ")";
    }
}
