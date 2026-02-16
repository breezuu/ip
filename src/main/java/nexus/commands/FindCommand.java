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
    private static final String HEADER = "[NEXUS]: Checking for existing tasks with the specified keyword...\n";
    private static final String ERROR_PROMPT = "[NEXUS]: An error occurred while searching for tasks.\n";
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
        try {
            List<Task> matchingTasks = findTasksMatchingDate(tasks);
            return HEADER + ui.printTaskListGui(matchingTasks, "all");
        } catch (NullPointerException e) {
            throw new NexusException(ERROR_PROMPT);
        }
    }

    private List<Task> findTasksMatchingDate(TaskList tasks) {
        return tasks.getTasks().stream()
                .filter(t -> t.getDescription().contains(keyword))
                .toList();
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
