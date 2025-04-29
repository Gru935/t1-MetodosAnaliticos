package models;

public class Event implements Comparable<Event> {
    private double time;
    private EventType type;
    private String queue_name;

    public Event(double time, EventType type, String queue_name) {
        this.time = time;
        this.type = type;
        this.queue_name = queue_name;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public double getTime() {
        return time;
    }

    public EventType getType() {
        return type;
    }

    @Override
    public int compareTo(Event other) {
        return Double.compare(this.time, other.time);
    }

    public String getQueue_name() {
        return queue_name;
    }

    public void setQueue_name(String queue_name) {
        this.queue_name = queue_name;
    }

}
