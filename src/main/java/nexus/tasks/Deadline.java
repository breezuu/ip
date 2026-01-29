package nexus.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Deadline extends Task {
    private final LocalDateTime deadlineTime;
    private final DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("d/M/yyyy h:mm a", Locale.ENGLISH);
    private final DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy h:mm a", Locale.ENGLISH);

    public Deadline(String desc, String deadline, boolean isDone) {
        super(desc, isDone);
        this.deadlineTime = LocalDateTime.parse(deadline, inputFormat);
    }

    @Override
    public boolean isDuringDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        return this.deadlineTime.format(formatter).equals(date);
    }

    @Override
    public String getType() {
        return "D";
    }

    @Override
    public String saveString() {
        return "D" + " | " + (isDone ? "1" : "0") + " | " + description + " | " + deadlineTime.format(inputFormat);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadlineTime.format(outputFormat) + ")";
    }
}
