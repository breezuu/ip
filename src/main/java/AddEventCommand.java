import java.time.format.DateTimeParseException;

class AddEventCommand extends Command {
    private final String description;
    private final String startTime;
    private final String endTime;
    private final boolean isDone;

    public AddEventCommand(String desc, String startTime, String endTime, boolean isDone) {
        this.description = desc;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isDone = isDone;
    }

    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) throws NexusException {
        try {
            Event eventTask = new Event(this.description, this.startTime, this.endTime, this.isDone);
            tasks.addTask(eventTask);
            ui.printTaskAdded(eventTask, tasks);
            storage.saveTasks(tasks.getTasks());
        } catch (DateTimeParseException e) {
            System.out.println("    [NEXUS]: Did you follow the date and time format (12-hour)?");
            System.out.println("    // e.g. 'event race /from 01/01/2002 1:00 PM /to 01/01/2002 2:00 PM'");
        }
    }
}
