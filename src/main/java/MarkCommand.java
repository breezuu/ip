import java.util.ArrayList;

class MarkCommand extends Command {
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

        ui.printTaskUpdated(taskList.get(index - 1), taskList, index);
    }
}
