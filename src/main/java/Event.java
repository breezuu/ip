class Event extends Task {
    private String startTime;
    private String endTime;

    public Event(String desc, String start, String end, boolean isDone) {
        super(desc, isDone);
        this.startTime = start;
        this.endTime = end;
    }

    @Override
    public String getType() {
        return "E";
    }

    @Override
    public String saveString() {
        return "E" + " | " + (isDone ? "1" : "0") + " | " + description + " | " + startTime + " | " + endTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startTime + " to: " + endTime + ")";
    }
}
