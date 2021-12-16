package smarthome.statemachine;

import java.util.Map;

public class Message<EVENT> {
    private EVENT event;
    private Map<String,Object> data;

    public Message(EVENT event, Map<String, Object> data) {
        this.event = event;
        this.data = data;
    }

    public EVENT getEvent() {
        return event;
    }

    public Map<String, Object> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "event=" + event +
                ", data=" + data +
                '}';
    }
}
