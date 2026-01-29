package nexus.commands;

import nexus.exception.NexusException;
import nexus.storage.Storage;
import nexus.tasks.TaskList;
import nexus.ui.Ui;

public abstract class Command {
    public abstract void run(TaskList tasks, Ui ui, Storage databank) throws NexusException;

    public boolean isExit() {
        return false;
    }
}
