package nexus.commands;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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
     * @throws NexusException If there is an error during execution.
     */
    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) throws NexusException {
        ArrayList<Task> taskList = tasks.getTasks();
        List<Task> result;

        System.out.println("    [NEXUS]: Checking for existing deadlines/events with the specified date...");

        try {
            result = taskList.stream()
                    .filter(t -> t.isDuringDate(this.dateToCheck))
                    .toList();
            ui.printTaskList(result);
        } catch (DateTimeParseException e) {
            System.out.println("    [NEXUS]: Did you follow the date format (DD/MM/YYYY)?");
            System.out.println("    // e.g. 'check 01/01/2002'");
        }
    }
}
