abstract class Command {
    abstract void run(TaskList tasks, Ui ui, Storage databank) throws NexusException;

    public boolean isExit() {
        return false;
    }
}
