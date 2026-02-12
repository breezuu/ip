package nexus.commands;

import nexus.exception.NexusException;
import nexus.storage.Storage;
import nexus.tasks.Note;
import nexus.tasks.Task;
import nexus.tasks.TaskList;
import nexus.ui.Ui;

/**
 * Command to add a note to the note list.
 */
public class AddNoteCommand extends Command {
    private final String description;

    /**
     * Constructor for AddNoteCommand.
     * @param desc Description of the note.
     */
    public AddNoteCommand(String desc) {
        this.description = desc;
    }

    /**
     * Executes AddNoteCommand to add a note to the note list.
     * @param tasks The TaskList object to which the note will be added.
     * @param ui Ui to display the result of the command.
     * @param storage Storage to save the updated task list.
     * @return A formatted string response from the user interface.
     * @throws NexusException If there is an error during execution.
     */
    @Override
    public String run(TaskList tasks, Ui ui, Storage storage) throws NexusException {
        int prevTaskCount = tasks.getSize();

        Task note = new Note(this.description, false);
        tasks.addTask(note);

        assert tasks.getSize() == prevTaskCount + 1 : "Size of TaskList object should increase by 1";

        storage.saveTasks(tasks.getTasks());
        return ui.printAddedTask(note, tasks);
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
