package smarthome.location;

import smarthome.devices.Device;
import smarthome.skinbag.Skinbag;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Location {
    private String id = UUID.randomUUID().toString();
    private Set<? extends Device> devices = new HashSet<>();
    private Set<? extends  Location> locations = new HashSet<>();
    private Set<Skinbag> skinbags = new HashSet<>();
    private boolean isRoom = true;

    public Location() {
    }

    public Location(String id) {
        this.id = id;
    }

    public Location(String id, boolean isRoom) {
        this.id = id;
        this.isRoom = isRoom;
    }

    public Location(String id, Set<? extends Device> devices) {
        this.id = id;
        this.devices = devices;
    }
    public Location(String id, Set<? extends Device> devices, Set<Skinbag> skinbags) {
        this.id = id;
        this.devices = devices;
        this.skinbags = skinbags;
    }
    public Location(String id, Set<? extends Device> devices, boolean isRoom) {
        this.id = id;
        this.devices = devices;
        this.isRoom = isRoom;
    }


    public Set<Skinbag> getSkinbags() {
        return skinbags;
    }

    public void setSkinbags(Set<Skinbag> skinbags) {
        this.skinbags = skinbags;
    }
    public void setSkinbag(Skinbag skinbag){
        this.skinbags.add(skinbag);
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

    public boolean isRoom() {
        return isRoom;
    }

    public void setRoom(boolean room) {
        isRoom = room;
    }
}
