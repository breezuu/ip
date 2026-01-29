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
     * @throws NexusException If there is an error during command execution.
     */
    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) throws NexusException {
        try {
            List<Task> res = tasks.getTasks().stream().filter(t -> t.getDescription().contains(keyword)).toList();
            ui.printTaskList(res);
        } catch (NullPointerException e) {
            System.out.println("    [NEXUS]: The keyword cannot be null or empty.");
            System.out.println("    // e.g. 'find quiz'");
        }
    }
}
