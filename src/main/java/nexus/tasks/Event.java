package nexus.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Event extends Task {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("d/M/yyyy h:mm a", Locale.ENGLISH);
    private final DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy h:mm a", Locale.ENGLISH);

    public Event(String desc, String start, String end, boolean isDone) {
        super(desc, isDone);
        this.startTime = LocalDateTime.parse(start, inputFormat);
        this.endTime = LocalDateTime.parse(end, inputFormat);
    }

    @Override
    public boolean isDuringDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        return this.startTime.format(formatter).equals(date);
    }

    @Override
    public String getType() {
        return "E";
    }

    @Override
    public String saveString() {
        return "E" + " | " + (isDone ? "1" : "0") + " | " + description + " | " + startTime.format(inputFormat)
                + " | " + endTime.format(inputFormat);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startTime.format(outputFormat) + " to: "
                + endTime.format(outputFormat) + ")";
    }
}
