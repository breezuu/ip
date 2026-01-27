class Deadline extends Task {
    private String deadline;

    public Deadline(String desc, String deadline, boolean isDone) {
        super(desc, isDone);
        this.deadline = deadline;
    }

    @Override
    public String getType() {
        return "D";
    }

    @Override
    public String saveString() {
        return "D" + " | " + (isDone ? "1" : "0") + " | " + description + " | " + deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadline + ")";
    }
}
