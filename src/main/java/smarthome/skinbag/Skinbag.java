package smarthome.skinbag;

import jdk.jfr.Event;
import smarthome.devices.Device;
import smarthome.statemachine.SmEvent;

import java.util.HashSet;
import java.util.Set;

public class Skinbag {
    private Set<Event> permissions = new HashSet<>();
    public void createEvent(Device device, SmEvent event) {

    }

    public Skinbag(Set<Event> permissions) {
        this.permissions = permissions;
    }
}
