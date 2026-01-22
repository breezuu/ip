public class Todo extends Task {
    public Todo(String desc) {
        super(desc);
    }

    @Override
    public String getType() {
        return "todo";
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
