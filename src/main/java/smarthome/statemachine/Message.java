package smarthome.statemachine;

import java.util.Collections;
import java.util.Map;

public class Message<EVENT extends SmEvent> {
    public static <EVENT extends SmEvent> Message<EVENT> toLocation(EVENT event, String toLocationId, Map<String,Object> data){
        Message<EVENT> message = new Message<>();
        message.setEvent(event);
        message.setToLocationId(toLocationId);
        message.setData(data);
        return message;
    }

    public static <EVENT extends SmEvent> Message<EVENT> toLocation(EVENT event, String toLocationId){
        Message<EVENT> message = new Message<>();
        message.setEvent(event);
        message.setToLocationId(toLocationId);
        return message;
    }

    public static <EVENT extends SmEvent> Message<EVENT> toDevice(EVENT event, String deviceId, Map<String,Object> data){
        Message<EVENT> message = new Message<>();
        message.setEvent(event);
        message.setIdTo(deviceId);
        message.setData(data);
        return message;
    }

    public static <EVENT extends SmEvent> Message<EVENT> toDevice(EVENT event, String deviceId){
        Message<EVENT> message = new Message<>();
        message.setEvent(event);
        message.setIdTo(deviceId);
        return message;
    }

    private EVENT event;
    private Map<String,Object> data = Collections.emptyMap();
    private String idFrom;
    private String idTo;
    private String toLocationId;





    public EVENT getEvent() {
        return event;
    }

    public void setEvent(EVENT event) {
        this.event = event;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getIdFrom() {
        return idFrom;
    }

    public void setIdFrom(String idFrom) {
        this.idFrom = idFrom;
    }

    public String getIdTo() {
        return idTo;
    }

    public void setIdTo(String idTo) {
        this.idTo = idTo;
    }

    public String getToLocationId() {
        return toLocationId;
    }

    public void setToLocationId(String toLocationId) {
        this.toLocationId = toLocationId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "event=" + event +
                ", data=" + data +
                '}';
    }


}
