package nexus.commands;

import nexus.exception.NexusException;
import nexus.storage.Storage;
import nexus.tasks.Task;
import nexus.tasks.TaskList;
import nexus.tasks.Todo;
import nexus.ui.Ui;

/**
 * Command to add a Todo task to the task list.
 */
public class AddTodoCommand extends Command {
    private final String description;

    /**
     * Constructor for AddTodoCommand.
     * @param desc Description of the Todo task.
     */
    public AddTodoCommand(String desc) {
        this.description = desc;
    }

    /**
     * Executes AddTodoCommand to add a Todo task to the task list.
     * @param tasks TaskList to which the Todo task will be added.
     * @param ui Ui to display the result of the command.
     * @param storage Storage to save the updated task list.
     * @return A formatted string response from the user interface.
     * @throws NexusException If there is an error during execution.
     */
    @Override
    public String run(TaskList tasks, Ui ui, Storage storage) throws NexusException {
        Task todoTask = new Todo(this.description, false);
        tasks.addTask(todoTask);
        storage.saveTasks(tasks.getTasks());
        return ui.printAddedTask(todoTask, tasks);
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
