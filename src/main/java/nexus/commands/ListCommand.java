package nexus.commands;

import nexus.exception.NexusException;
import nexus.storage.Storage;
import nexus.tasks.TaskList;
import nexus.ui.Ui;

/**
 * Command to list all tasks in the task list.
 */
public class ListCommand extends Command {
    private final String listType;

    public ListCommand(String listType) {
        this.listType = listType;
    }

    /**
     * Executes ListCommand to display all tasks in the task list.
     * @param tasks A TaskList which is used to get all tasks.
     * @param ui Ui to display the list of tasks.
     * @param storage Storage (not used in this command).
     * @return A formatted string response from the user interface.
     * @throws NexusException If there is an error during command execution.
     */
    @Override
    public String run(TaskList tasks, Ui ui, Storage storage) throws NexusException {
        return ui.printTaskListGui(tasks.getTasks(), this.listType);
    }

    /**
     * Returns the name of the command.
     * @return Name of the command.
     */
    @Override
    public String getName() {
        return "ListCommand";
    }
}
