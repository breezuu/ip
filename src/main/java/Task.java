class Task {
    protected String description;
    protected boolean isDone;

    public Task(String desc, boolean isDone) {
        this.description = desc;
        this.isDone = isDone;
    }

    public String getStatus() {
        return (isDone ? "[X]" : "[ ]");
    }

    public void mark() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    public String getType() {
        return "Task";
    }

    public String saveString() {
        return "Task" + " | " + (isDone ? "1" : "0") + " | " + description;
    }

    public boolean isDuringDate(String date) {
        return false; // Tasks have no deadline/date by default
    }

    @Override
    public String toString() {
        return getStatus() + " " + this.description;
    }
}
