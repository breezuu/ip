package nexus.commands;

import nexus.exception.NexusException;
import nexus.storage.Storage;
import nexus.tasks.TaskList;
import nexus.ui.Ui;

/**
 * Abstract base class for all commands in Nexus.
 */
public abstract class Command {
    /**
     * Executes the command with the given task list, user interface, and storage.
     * @param tasks TaskList to operate on.
     * @param ui Ui for user interaction.
     * @param databank Storage for task data persistence.
     * @throws NexusException If there is an error during command execution.
     */
    public abstract void run(TaskList tasks, Ui ui, Storage databank) throws NexusException;

    /**
     * Checks if the command is an exit command.
     * @return True if the command is an exit command, false otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
