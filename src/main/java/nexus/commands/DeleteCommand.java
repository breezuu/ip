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
     * @param tasks A TaskList from which the task will be deleted.
     * @param ui Ui to display the result of the command.
     * @param storage Storage to save the updated task list.
     * @return A formatted string response from the user interface.
     * @throws NexusException If there is an error during command execution.
     */
    @Override
    public String run(TaskList tasks, Ui ui, Storage storage) throws NexusException {
        int prevTaskCount = tasks.getSize();

        tasks.validateIndex(index);

        Task removedTask = tasks.deleteTask(index - 1);

        assert tasks.getSize() == prevTaskCount - 1 : "Size of TaskList object should decrease by 1";

        String response = ui.printDeletedTask(removedTask, tasks);
        storage.saveTasks(tasks.getTasks());
        return response;
    }

    /**
     * Returns the name of the command.
     * @return Name of the command.
     */
    @Override
    public String getName() {
        return "DeleteCommand";
    }
}
