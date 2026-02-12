package nexus.commands;

import java.time.format.DateTimeParseException;

import nexus.exception.NexusException;
import nexus.storage.Storage;
import nexus.tasks.Deadline;
import nexus.tasks.TaskList;
import nexus.ui.Ui;

/**
 * Command to add a deadline task to the task list.
 */
public class AddDeadlineCommand extends Command {
    private final String description;
    private final String deadline;
    private final boolean isDone;

    /**
     * Constructor for AddDeadlineCommand.
     * @param desc Description of the deadline task.
     * @param deadline Deadline of the task in the format (d/M/yyyy h:mm a).
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
     * @return A formatted string response from the user interface.
     * @throws NexusException If there is an issue with adding the task or saving to storage.
     */
    @Override
    public String run(TaskList tasks, Ui ui, Storage storage) throws NexusException {
        StringBuilder sb = new StringBuilder();

        try {
            int prevTaskCount = tasks.getSize();

            Deadline deadlineTask = new Deadline(this.description, this.deadline, this.isDone);
            tasks.addTask(deadlineTask);

            assert tasks.getSize() == prevTaskCount + 1 : "Size of TaskList object should increase by 1";

            sb.append(ui.printAddedTask(deadlineTask, tasks));
            storage.saveTasks(tasks.getTasks());

        } catch (DateTimeParseException e) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("[NEXUS]: Did you follow the date and time format (12-hour)?\n");
            sb2.append("// e.g. 'deadline quiz /by 01/01/2002 10:00 AM'");
            return sb2.toString();
        }
        return sb.toString();
    }

    /**
     * Returns the name of the command.
     * @return Name of the command.
     */
    @Override
    public String getName() {
        return "AddCommand";
    }
}
