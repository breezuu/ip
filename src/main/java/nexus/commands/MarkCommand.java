package nexus.commands;

import java.util.ArrayList;

import nexus.exception.NexusException;
import nexus.storage.Storage;
import nexus.tasks.Task;
import nexus.tasks.TaskList;
import nexus.ui.Ui;

public class MarkCommand extends Command {
    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) throws NexusException {
        tasks.validateIndex(index);

        ArrayList<Task> taskList = tasks.getTasks();

        taskList.get(index - 1).mark();

        storage.saveTasks(taskList);

        ui.printTaskUpdated(taskList, index);
    }
}
