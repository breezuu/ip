package nexus.commands;

import nexus.exception.NexusException;
import nexus.storage.Storage;
import nexus.tasks.TaskList;
import nexus.ui.Ui;

public class ListCommand extends Command {
    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) throws NexusException {
        ui.printTaskList(tasks.getTasks());
    }
}
