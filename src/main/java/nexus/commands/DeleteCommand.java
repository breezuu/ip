package nexus.commands;

import nexus.exception.NexusException;
import nexus.storage.Storage;
import nexus.tasks.Task;
import nexus.tasks.TaskList;
import nexus.ui.Ui;

public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) throws NexusException {
        tasks.validateIndex(index);

        Task removedTask = tasks.deleteTask(index - 1);
        ui.printTaskDeleted(removedTask, tasks);
        storage.saveTasks(tasks.getTasks());
    }
}
