package nexus.commands;

import nexus.exception.NexusException;
import nexus.storage.Storage;
import nexus.tasks.TaskList;
import nexus.ui.Ui;

/**
 * Command to exit the Nexus app.
 */
public class ExitCommand extends Command {
    public ExitCommand() {
        // Do nothing
    }

    /**
     * Executes ExitCommand to terminate the Nexus app.
     * @param tasks TaskList (not used in this command).
     * @param ui Ui to display the farewell message.
     * @param storage Storage to save the task list before exiting.
     * @throws NexusException If there is an error during command execution.
     */
    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) throws NexusException {
        storage.saveTasks(tasks.getTasks());
        ui.printFarewell();
    }

    /**
     * Checks if the command is an exit command.
     * @return True, indicating this is an exit command.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
