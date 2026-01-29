package nexus.commands;

import nexus.exception.NexusException;
import nexus.storage.Storage;
import nexus.tasks.TaskList;
import nexus.ui.Ui;

/**
 * Command to list all tasks in the task list.
 */
public class ListCommand extends Command {
    /**
     * Executes ListCommand to display all tasks in the task list.
     * @param tasks TaskList which is used to get all tasks.
     * @param ui Ui to display the list of tasks.
     * @param storage Storage (not used in this command).
     * @throws NexusException If there is an error during command execution.
     */
    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) throws NexusException {
        ui.printTaskList(tasks.getTasks());
    }
}
