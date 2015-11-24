package models;

/**
 * Created by yellowstar on 11/23/15.
 */
public class UserEventWithStatus {
    private Event event;
    private int Status;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
