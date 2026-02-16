package nexus.commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import nexus.exception.NexusException;
import nexus.storage.Storage;
import nexus.tasks.Event;
import nexus.tasks.TaskList;
import nexus.ui.Ui;

/**
 * Command to add an event task to the task list.
 */
public class AddEventCommand extends Command {
    private static final String DATE_ERROR_PROMPT = "[NEXUS]: Did you follow the date and time format (12-hour)?\n";
    private static final String CMD_EXAMPLE = "// e.g. 'event race /from 01/01/2002 1:00 PM /to 01/01/2002 2:00 PM'";
    private static final String INPUT_FORMAT = "d/M/yyyy h:mm a";
    private static final String OUTPUT_FORMAT = "MMM dd yyyy h:mm a";

    private final String description;
    private final String startTime;
    private final String endTime;
    private final boolean isDone;
    private final DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern(INPUT_FORMAT, Locale.ENGLISH);
    private final DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern(OUTPUT_FORMAT, Locale.ENGLISH);

    /**
     * Constructor for AddEventCommand.
     * @param desc Description of the event task.
     * @param startTime Start time of the event in the format (d/M/yyyy h:mm a).
     * @param endTime End time of the event in the format (d/M/yyyy h:mm a).
     * @param isDone Whether the task is done initially.
     */
    public AddEventCommand(String desc, String startTime, String endTime, boolean isDone) {
        this.description = desc;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isDone = isDone;
    }

    /**
     * Executes the command to add an event task to the task list.
     * @param tasks The task list to which the event task will be added.
     * @param ui The user interface for displaying messages.
     * @param storage The storage for saving tasks.
     * @return A formatted string response from the user interface.
     * @throws NexusException If there is an issue with adding the task or saving to storage.
     */
    @Override
    public String run(TaskList tasks, Ui ui, Storage storage) throws NexusException {
        try {
            boolean isDateValid = isEventDateValid(this.startTime, this.endTime);

            if (!isDateValid) {
                throw new NexusException("[NEXUS]: The event start time must be before the end time.");
            }

            int prevTaskCount = tasks.getSize();

            Event eventTask = new Event(this.description, this.startTime, this.endTime, this.isDone);
            tasks.addTask(eventTask);

            assert tasks.getSize() == prevTaskCount + 1 : "Size of TaskList object should increase by 1";

            storage.saveTasks(tasks.getTasks());
            return ui.printAddedTask(eventTask, tasks);
        } catch (DateTimeParseException e) {
            throw new NexusException(DATE_ERROR_PROMPT + CMD_EXAMPLE);
        }
    }

    private boolean isEventDateValid(String startTime, String endTime) throws NexusException {
        LocalDateTime start = LocalDateTime.parse(startTime, inputFormat);
        LocalDateTime end = LocalDateTime.parse(endTime, inputFormat);

        return end.isAfter(start);
    }

    /**
     * Returns the name of the command.
     * @return Name of the command.
     */
    @Override
    public String getName() {
        return "AddCommand";
    }
}
