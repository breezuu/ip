package nexus.tasks;

/**
 * Todo class which represents a task with no deadline or event date.
 */
public class Todo extends Task {
    /**
     * Constructor for Todo task.
     * @param desc Description of the task.
     * @param isDone Completion status of the task.
     */
    public Todo(String desc, boolean isDone) {
        super(desc, isDone);
    }

    /**
     * Returns the type of the task.
     * @return Type of the task, which is "T" for Todo.
     */
    @Override
    public String getType() {
        return "T";
    }

    /**
     * Returns a string representation of the task for saving.
     * Format: "T | 1/0 | description"
     * @return String representation of the task for saving.
     */
    @Override
    public String saveString() {
        return "T" + " | " + (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Returns false as Todo tasks have no deadline/date.
     * @param date Date to check against.
     * @return False, as Todo tasks have no deadline/date.
     */
    @Override
    public boolean isValidDateWindow(String date) {
        return false; // To-do tasks have no deadline/date
    }

    /**
     * Returns a string representation of the task.
     * Format: "[T]/[ ] description"
     * @return String representation of the task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
