package smarthome.location;

import smarthome.devices.Device;

import java.util.HashSet;
import java.util.Set;

public class Location {
    private Set<? extends Device> setDevices = new HashSet<>();
    private Set<? extends  Location> setLocations = new HashSet<>();

    public Set<? extends Device> getSetDevices() {
        return setDevices;
    }

    public void setSetDevices(Set<? extends Device> setDevices) {
        this.setDevices = setDevices;
    }

    public Set<? extends Location> getSetLocations() {
        return setLocations;
    }

    public void setSetLocations(Set<? extends Location> setLocations) {
        this.setLocations = setLocations;
    }

}
