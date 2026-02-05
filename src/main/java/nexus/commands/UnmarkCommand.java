package nexus.commands;

import java.util.ArrayList;

import nexus.exception.NexusException;
import nexus.storage.Storage;
import nexus.tasks.Task;
import nexus.tasks.TaskList;
import nexus.ui.Ui;

/**
 * Command to unmark a task.
 */
public class UnmarkCommand extends Command {
    private final int index;

    /**
     * Constructs an UnmarkCommand with the specified task index.
     * @param index Index of the task to be unmarked.
     */
    public UnmarkCommand(int index) {
        this.index = index;
    }

    /**
     * Executes UnmarkCommand to unmark a task.
     * @param tasks TaskList used to find the task to be unmarked.
     * @param ui Ui to display the result of the command.
     * @param storage Storage to save the updated task list.
     * @return A formatted string response from the user interface.
     * @throws NexusException If there is an error during command execution.
     */
    @Override
    public String run(TaskList tasks, Ui ui, Storage storage) throws NexusException {
        tasks.validateIndex(index);

        ArrayList<Task> taskList = tasks.getTasks();

        taskList.get(index - 1).unmark();

        storage.saveTasks(taskList);

        String response = ui.printUpdatedTask(taskList, index);
        return response;
    }

    /**
     * Returns the name of the command.
     * @return Name of the command.
     */
    @Override
    public String getName() {
        return "ChangeMarkCommand";
    }
}
