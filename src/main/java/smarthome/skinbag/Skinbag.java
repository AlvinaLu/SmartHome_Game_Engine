package smarthome.skinbag;

import jdk.jfr.Event;
import smarthome.Dispatcher;
import smarthome.devices.Device;
import smarthome.location.Location;
import smarthome.sensors.Sensor;
import smarthome.statemachine.SmEvent;

import java.util.HashSet;
import java.util.Set;

public class Skinbag {
    private String id;
    private Set<? extends SmEvent> permissions = new HashSet<>();
    Location location;
    Location prevLocation;

    public Skinbag() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Skinbag(Set<? extends SmEvent> permissions) {
        this.permissions = permissions;
    }

    public Skinbag(String id, Set<? extends SmEvent> permissions) {
        this.id = id;
        this.permissions = permissions;
    }

    public Set<? extends SmEvent> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<? extends SmEvent> permissions) {
        this.permissions = permissions;
    }

    public Location getLocation() {
        return location;
    }


    public Location getPrevLocation() {
        return prevLocation;
    }

    public void setPrevLocation(Location prevLocation) {
        this.prevLocation = prevLocation;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}

