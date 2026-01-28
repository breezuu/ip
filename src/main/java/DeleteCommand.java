class DeleteCommand extends Command {
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
