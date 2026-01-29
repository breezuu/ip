package nexus.commands;

import java.util.ArrayList;

import nexus.exception.NexusException;
import nexus.storage.Storage;
import nexus.tasks.Task;
import nexus.tasks.TaskList;
import nexus.ui.Ui;

public class UnmarkCommand extends Command {
    private final int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) throws NexusException {
        tasks.validateIndex(index);

        ArrayList<Task> taskList = tasks.getTasks();

        taskList.get(index - 1).unmark();

        storage.saveTasks(taskList);

        ui.printTaskUpdated(taskList, index);
    }
}
