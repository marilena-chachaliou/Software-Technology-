package inncontrol;

public class Event {
    private String eventName;
    private String location;
    private String date;
    private String organizer;

    public Event(String eventName, String location, String date, String organizer) {
        this.eventName = eventName;
        this.location = location;
        this.date = date;
        this.organizer = organizer;
    }

    public String getEventName() { return eventName; }
    public String getLocation() { return location; }
    public String getDate() { return date; }
    public String getOrganizer() { return organizer; }
}