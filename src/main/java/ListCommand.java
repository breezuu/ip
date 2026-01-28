class ListCommand extends Command {
    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) throws NexusException {
        ui.printTaskList(tasks.getTasks());
    }
}
