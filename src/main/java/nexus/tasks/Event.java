package nexus.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Event class which represents a task with a start and end time.
 */
public class Event extends Task {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("d/M/yyyy h:mm a", Locale.ENGLISH);
    private final DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy h:mm a", Locale.ENGLISH);

    /**
     * Constructs an Event object with the specified description, start time, end time, and completion status.
     * @param desc Description of the event task.
     * @param start Start date and time in the format (d/M/yyyy h:mm a).
     * @param end End date and time in the format (d/M/yyyy h:mm a).
     * @param isDone Completion status of the event task.
     */
    public Event(String desc, String start, String end, boolean isDone) {
        super(desc, isDone);
        this.startTime = LocalDateTime.parse(start, inputFormat);
        this.endTime = LocalDateTime.parse(end, inputFormat);
    }

    /**
     * Checks if the event falls on the specified date.
     * @param date Date in the format (d/M/yyyy).
     * @return True if the event falls on the specified date, false otherwise.
     */
    @Override
    public boolean isValidDateWindow(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        return this.startTime.format(formatter).equals(date);
    }

    /**
     * Returns the type of the task as a string.
     * @return Type of the task as "E".
     */
    @Override
    public String getType() {
        return "E";
    }

    /**
     * Returns a string representation of the Event task.
     * @return String representation of the Event task.
     */
    @Override
    public String saveString() {
        return "E" + " | " + (isDone ? "1" : "0") + " | " + description + " | " + startTime.format(inputFormat)
                + " | " + endTime.format(inputFormat);
    }

    /**
     * Returns a string representation of the Event task.
     * @return String representation of the Event task.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startTime.format(outputFormat) + " to: "
                + endTime.format(outputFormat) + ")";
    }
}
