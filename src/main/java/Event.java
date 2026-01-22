public class Event extends Task {
    private final String startTime;
    private final String endTime;

    public Event(String desc, String start, String end) {
        super(desc);
        this.startTime = start;
        this.endTime = end;
    }

    @Override
    public String getType() {
        return "event";
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startTime + " to: " + endTime + ") ";
    }
}
