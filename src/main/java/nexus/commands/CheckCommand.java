package nexus.commands;

import java.time.format.DateTimeParseException;
import java.util.List;

import nexus.exception.NexusException;
import nexus.storage.Storage;
import nexus.tasks.Task;
import nexus.tasks.TaskList;
import nexus.ui.Ui;

/**
 * Command to check for tasks occurring on a specific date.
 */
public class CheckCommand extends Command {
    private static final String HEADER = "[NEXUS]: Checking for existing deadlines/events with the specified date...\n";
    private static final String DATE_ERROR_PROMPT = "[NEXUS]: Did you follow the date format (DD/MM/YYYY)?\n";
    private static final String CMD_EXAMPLE = "// e.g. 'check 01/01/2002'";

    private final String dateToCheck;

    /**
     * Constructor for CheckCommand.
     * @param date Date used to check for tasks in the format (d/M/yyyy).
     */
    public CheckCommand(String date) {
        this.dateToCheck = date;
    }

    /**
     * Executes CheckCommand to find tasks occurring on a specific date.
     * @param tasks TaskList to search for tasks.
     * @param ui Ui to display the result of the command.
     * @param storage Storage to save the task list (not used in this command).
     * @return A formatted string response from the user interface.
     * @throws NexusException If there is an error during execution.
     */
    @Override
    public String run(TaskList tasks, Ui ui, Storage storage) throws NexusException {
        try {
            List<Task> matchingTasks = findTasksMatchingKeyword(tasks);
            return HEADER + ui.printTaskListGui(matchingTasks, "tasks");
        } catch (DateTimeParseException e) {
            return DATE_ERROR_PROMPT + CMD_EXAMPLE;
        }
    }

    private List<Task> findTasksMatchingKeyword(TaskList tasks) {
        return tasks.getTasks().stream()
                .filter(t -> t.isValidDateWindow(this.dateToCheck))
                .toList();
    }

    /**
     * Returns the name of the command.
     * @return Name of the command.
     */
    @Override
    public String getName() {
        return "CheckCommand";
    }
}
