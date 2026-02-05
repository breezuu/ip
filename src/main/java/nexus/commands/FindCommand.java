package nexus.commands;

import java.util.List;

import nexus.exception.NexusException;
import nexus.storage.Storage;
import nexus.tasks.Task;
import nexus.tasks.TaskList;
import nexus.ui.Ui;

/**
 * Command to find tasks in the task list based on a keyword.
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Constructor for FindCommand.
     * @param keyword The keyword to search for in task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes FindCommand to display tasks containing the specified keyword.
     * @param tasks TaskList used to search for tasks.
     * @param ui Ui to display the found tasks.
     * @param storage Storage (not used in this command).
     * @return A formatted string response from the user interface.
     * @throws NexusException If there is an error during command execution.
     */
    @Override
    public String run(TaskList tasks, Ui ui, Storage storage) throws NexusException {
        StringBuilder sb = new StringBuilder();
        try {
            List<Task> res = tasks.getTasks().stream().filter(t -> t.getDescription().contains(keyword)).toList();
            sb.append(ui.printTaskListGui(res));
        } catch (NullPointerException e) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("[NEXUS]: The keyword cannot be null or empty.\n");
            sb2.append("// e.g. 'find quiz'");
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
        return "FindCommand";
    }
}
