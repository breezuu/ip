package nexus.tasks;

import java.util.ArrayList;

import nexus.exception.NexusException;

/**
 * TaskList class which manages a list of tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;
    private final String[] taskTypes = new String[]{"Todo", "Deadline", "Event"};

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList object with the provided list of tasks.
     * @param tasks The initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the list at the specified index.
     * @param index The index of the task to be deleted.
     * @return The deleted task.
     */
    public Task deleteTask(int index) {
        return tasks.remove(index);
    }

    /**
     * Retrieves all tasks in the list.
     * @return An ArrayList of tasks.
     */
    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Retrieves the task types supported by the task list.
     * @return An array of task type names.
     */
    public String[] getTaskTypes() {
        return this.taskTypes;
    }

    /**
     * Retrieves the size of the task list.
     * @return The number of tasks in the list.
     */
    public int getSize() {
        return this.tasks.size();
    }

    /**
     * Checks if the provided index is within the bounds of the task list.
     * @param index The index to be validated.
     * @throws NexusException If the index is out of bounds or the task list is empty.
     */
    public void validateIndex(int index) throws NexusException {
        StringBuilder sb = new StringBuilder();

        if (this.tasks.isEmpty()) {
            sb.append("// ERROR: DATABANK EMPTY");
            throw new NexusException(sb.toString());

        } else if (index < 1 || index > this.tasks.size()) {
            sb.append("// ERROR: TASK NUMBER OUT OF BOUNDS\n");
            String numTasks = this.tasks.size() > 1 ? "TASKS" : "TASK";
            sb.append("// CURRENT_TOTAL: %d %s".formatted(this.tasks.size(), numTasks));
            throw new NexusException(sb.toString());
        }
    }
}
