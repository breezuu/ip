package nexus.commands;

import nexus.exception.NexusException;
import nexus.storage.Storage;
import nexus.tasks.Task;
import nexus.tasks.TaskList;
import nexus.tasks.Todo;
import nexus.ui.Ui;

public class AddTodoCommand extends Command {
    private final String description;

    public AddTodoCommand(String desc) {
        this.description = desc;
    }

    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) throws NexusException {
        Task todoTask = new Todo(this.description, false);
        tasks.addTask(todoTask);
        ui.printTaskAdded(todoTask, tasks);
        storage.saveTasks(tasks.getTasks());
    }
}
