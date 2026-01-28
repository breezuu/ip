import java.time.format.DateTimeParseException;

class AddDeadlineCommand extends Command {
    private final String description;
    private final String deadline;
    private final boolean isDone;

    public AddDeadlineCommand(String desc, String deadline, boolean isDone) {
        this.description = desc;
        this.deadline = deadline;
        this.isDone = isDone;
    }

    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) throws NexusException {
        try {
            Deadline deadlineTask = new Deadline(this.description, this.deadline, this.isDone);
            tasks.addTask(deadlineTask);
            ui.printTaskAdded(deadlineTask, tasks);
            storage.saveTasks(tasks.getTasks());
        } catch (DateTimeParseException e) {
            System.out.println("    [NEXUS]: Did you follow the date and time format (12-hour)?");
            System.out.println("    // e.g. 'deadline quiz /by 01/01/2002 10:00 AM'");
        }
    }
}
