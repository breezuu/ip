package nexus.commands;

import nexus.exception.NexusException;
import nexus.storage.Storage;
import nexus.tasks.Task;
import nexus.tasks.TaskList;
import nexus.ui.Ui;

/**
 * Command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    private final int index;

    /**
     * Constructor for DeleteCommand.
     * @param index Index of the task to be deleted.
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Executes DeleteCommand to remove a task from the task list.
     * @param tasks TaskList from which the task will be deleted.
     * @param ui Ui to display the result of the command.
     * @param storage Storage to save the updated task list.
     * @throws NexusException If there is an error during command execution.
     */
    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) throws NexusException {
        tasks.validateIndex(index);

        Task removedTask = tasks.deleteTask(index - 1);
        ui.printTaskDeleted(removedTask, tasks);
        storage.saveTasks(tasks.getTasks());
    }
}
