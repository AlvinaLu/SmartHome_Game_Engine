package smarthome;

import smarthome.devices.Device;
import smarthome.location.Location;
import smarthome.sensors.Sensor;
import smarthome.skinbag.Skinbag;
import smarthome.statemachine.Message;
import smarthome.statemachine.StateMachine;

import java.util.*;

public class Dispatcher {
    private static Dispatcher instance;

    public static Dispatcher getInstance() {
        if (instance == null) {
            instance = new Dispatcher();
        }
        return instance;
    }

    private Set<? extends Location> locations = new HashSet<>();
    private Map<String, Location> mapLocation = new HashMap<>();
    private Map<String, Device> mapDevice = new HashMap<>();
    private Set<Skinbag> skinbags = new HashSet<>();


    private Dispatcher() {

    }

    public void init(Set<? extends Location> locations) {
        this.locations = locations;
        for (Location loc : locations) {
            mapLocation.put(loc.getId(), loc);
            recurseLocation(loc);

        }
        for(Location loc : mapLocation.values()){
            if (!loc.getSkinbags().isEmpty()) {
                loc.getSkinbags().forEach(it -> it.setLocation(loc));
                loc.getSkinbags().forEach(it -> skinbags.add(it));
            }
        }

        for (Device device : mapDevice.values()) {
            if (device instanceof StateMachine) {
                ((StateMachine<?, ?>) device).init();
            }
        }
    }

    private void recurseLocation(Location location) {
        for (Location loc : location.getLocations()) {
            mapLocation.put(loc.getId(), loc);
            recurseLocation(loc);
        }
        for (Device<?> dev : location.getDevices()) {
            mapDevice.put(dev.getId(), dev);
        }
    }

    public void sendMessage(Message<?> message) {
        if (message.getIdTo() != null) {
            Device<?> device = mapDevice.get(message.getIdTo());
            if (device != null) {
                if (device instanceof StateMachine<?, ?>) {
                    StateMachine<?, ?> sm = (StateMachine<?, ?>) device;
                    sm.onMessage((Message) message);
                } else {
                    System.out.println("Device doesn't accept messages");
                }
            }

        } else if (message.getToLocationId() != null) {
            Location location = mapLocation.get(message.getToLocationId());
            if (location != null) {
                recurseLocationMessage(location, message);
            } else {
                System.out.println("Location is not found");
            }
        } else {
            for (Location loc : locations) {
                recurseLocationMessage(loc, message);
            }
        }
    }

    private void recurseLocationMessage(Location location, Message<?> message) {
        for (Device<?> device : location.getDevices()) {
            if (device instanceof StateMachine) {
                StateMachine<?, ?> sm = (StateMachine<?, ?>) device;
                sm.onMessage((Message) message);
            }
        }
        for (Location loc : location.getLocations()) {
            recurseLocationMessage(loc, message);
        }
    }


    public Sensor getSensor(String id) {
        if (mapDevice.get(id) instanceof Sensor) {
            return (Sensor) mapDevice.get(id);
        } else if (mapDevice.get(id) == null) {
            throw new IllegalStateException("Device not found");
        } else {
            throw new IllegalStateException("Device is not a sensor");
        }
    }
    public Location getRandomLocation(){
        Object[] locations = mapLocation.values().toArray();
        return (Location) locations[new Random().nextInt(locations.length)];
    }

    public Map<String, Location> getMapLocation() {
        return mapLocation;
    }

    public Map<String, Device> getMapDevice() {
        return mapDevice;
    }

    public Set<Skinbag> getSkinbags() {
        return skinbags;
    }
}
