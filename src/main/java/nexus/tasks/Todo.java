package nexus.tasks;

public class Todo extends Task {
    public Todo(String desc, boolean isDone) {
        super(desc, isDone);
    }

    @Override
    public String getType() {
        return "T";
    }

    @Override
    public String saveString() {
        return "T" + " | " + (isDone ? "1" : "0") + " | " + description;
    }

    @Override
    public boolean isDuringDate(String date) {
        return false; // To-do tasks have no deadline/date
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
