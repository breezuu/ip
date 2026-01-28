class ExitCommand extends Command {
    public ExitCommand() {
        // Do nothing
    }

    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) throws NexusException {
        storage.saveTasks(tasks.getTasks()); // Any corruption in the 'databank.txt' file will be fixed when data is overwritten
        ui.printFarewell();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
