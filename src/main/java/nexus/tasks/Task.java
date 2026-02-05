package nexus.tasks;

/**
 * Task class which represents a generic task with a description and completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task object with the specified description and completion status.
     * @param desc Description of the task.
     * @param isDone Completion status of the task.
     */
    public Task(String desc, boolean isDone) {
        this.description = desc;
        this.isDone = isDone;
    }

    /**
     * Returns the status of the task as a string.
     * @return Status of the task as "[X]" if done, "[ ]" if not done.
     */
    public String getStatus() {
        return (isDone ? "[X]" : "[ ]");
    }

    /**
     * Marks the task as done, setting its completion status to true.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Unmarks the task, setting its completion status to false.
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Returns the type of the task.
     * @return Type of the task as "Task".
     */
    public String getType() {
        return "Task";
    }

    /**
     * Returns the description of the task.
     * @return Description of the task.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the completion status of the task.
     * @return True if the task is done, false otherwise.
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Returns a string representation of the task for saving.
     * Format: "Task | 1/0 | description"
     * @return String representation of the task for saving.
     */
    public String saveString() {
        return "Task" + " | " + (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Returns true if the task is during the specified date.
     * @param date Date to check against.
     * @return True if the task is during the specified date, false otherwise.
     */
    public boolean isValidDateWindow(String date) {
        return false; // Tasks have no deadline/date by default
    }

    /**
     * Returns a string representation of the task.
     * Format: "[X]/[ ] description"
     * @return String representation of the task.
     */
    @Override
    public String toString() {
        return getStatus() + " " + this.description;
    }
}
