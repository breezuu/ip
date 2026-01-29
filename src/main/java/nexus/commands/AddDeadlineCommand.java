package nexus.commands;

import java.time.format.DateTimeParseException;

import nexus.exception.NexusException;
import nexus.storage.Storage;
import nexus.tasks.Deadline;
import nexus.tasks.TaskList;
import nexus.ui.Ui;

public class AddDeadlineCommand extends Command {
    private final String description;
    private final String deadline;
    private final boolean isDone;

    /**
     * Constructor for AddDeadlineCommand.
     * @param desc Description of the deadline task.
     * @param deadline Deadline of the task in the format "dd/MM/yyyy HH:mm".
     * @param isDone Whether the task is done initially.
     */
    public AddDeadlineCommand(String desc, String deadline, boolean isDone) {
        this.description = desc;
        this.deadline = deadline;
        this.isDone = isDone;
    }

    /**
     * Executes the command to add a deadline task to the task list.
     * @param tasks The task list to which the deadline task will be added.
     * @param ui The user interface for displaying messages.
     * @param storage The storage for saving tasks.
     * @throws NexusException If there is an issue with adding the task or saving to storage.
     */
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
