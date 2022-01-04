package smarthome.location;

import smarthome.devices.Device;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Location {
    private String id = UUID.randomUUID().toString();
    private Set<? extends Device> devices = new HashSet<>();
    private Set<? extends  Location> locations = new HashSet<>();

    public Location() {
    }

    public Location(String id) {
        this.id = id;
    }

    public Location(String id, Set<? extends Device> devices) {
        this.id = id;
        this.devices = devices;
    }

    public Set<? extends Device> getDevices() {
        return devices;
    }

    public void setDevices(Set<? extends Device> devices) {
        this.devices = devices;
    }

    public Set<? extends Location> getLocations() {
        return locations;
    }

    public void setLocations(Set<? extends Location> locations) {
        this.locations = locations;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public <T extends Device<?>> List<T> getDevicesByType(Class<T> clazz) {
        return getDevices().stream().filter(it->clazz.isAssignableFrom(it.getClass())).map(it->(T)it).collect(Collectors.toList());
    }
}
