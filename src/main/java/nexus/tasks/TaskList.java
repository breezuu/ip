package nexus.tasks;

import java.util.ArrayList;

import nexus.exception.NexusException;

public class TaskList {
    private final ArrayList<Task> tasks;
    private final String[] taskTypes = new String[]{"Todo", "Deadline", "Event"};

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task deleteTask(int index) {
        return tasks.remove(index);
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public String[] getTaskTypes() {
        return this.taskTypes;
    }

    public int getSize() {
        return this.tasks.size();
    }

    public void validateIndex(int index) throws NexusException {
        if (this.tasks.isEmpty()) {
            throw new NexusException("// ERROR: DATABANK EMPTY");

        } else if (index < 1 || index > this.tasks.size()) {
            System.out.println("    // ERROR: TASK NUMBER OUT OF BOUNDS");
            String numTasks = this.tasks.size() > 1 ? " TASKS" : " TASK";
            throw new NexusException("// CURRENT_TOTAL: " + this.tasks.size() + numTasks);
        }
    }
}
