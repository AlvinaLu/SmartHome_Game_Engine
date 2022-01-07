package smarthome.skinbag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.jfr.Event;
import smarthome.Dispatcher;
import smarthome.devices.Device;
import smarthome.devices.refrigerator.RefrigeratorEvent;
import smarthome.location.Location;
import smarthome.sensors.HumanSensor;
import smarthome.sensors.Sensor;
import smarthome.statemachine.Message;
import smarthome.statemachine.SmEvent;

import java.util.*;
import java.util.stream.Collectors;

public class Skinbag {
    private String id;
    private Set<? extends SmEvent> permissions = new HashSet<>();
    @JsonIgnore
    private Location location;
    @JsonIgnore
    private Location prevLocation;

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

    public void goToNewLocation() {
        Location randomLocation = Dispatcher.getInstance().getRandomLocation();
        if (randomLocation.isRoom() && !randomLocation.equals(location)) {
            List<HumanSensor> sensors = location.getDevicesByType(HumanSensor.class);
            if (sensors.size() == 1) {
                sensors.get(0).setValue(Optional.ofNullable(sensors.get(0).getData().getValue()).orElse(0D) - 1);
            }

            prevLocation = location;

            this.location = randomLocation;
            List<HumanSensor> sensorsNew = location.getDevicesByType(HumanSensor.class);
            if (sensorsNew.size() == 1) {
                sensorsNew.get(0).setValue(Optional.ofNullable(sensorsNew.get(0).getData().getValue()).orElse(1D) + 1);
            }
            System.out.println(id + " go from " + prevLocation.getId() + " to " + location.getId());
        }
    }

    public void goToNewLocation(Location newLocation) {
        List<HumanSensor> sensors = location.getDevicesByType(HumanSensor.class);
        if (sensors.size() == 1) {
            sensors.get(0).setValue(Optional.ofNullable(sensors.get(0).getData().getValue()).orElse(0D) - 1);
        }
        prevLocation = location;

        this.location = newLocation;
        List<HumanSensor> sensorsNew = location.getDevicesByType(HumanSensor.class);
        if (sensorsNew.size() == 1) {
            sensorsNew.get(0).setValue(Optional.ofNullable(sensorsNew.get(0).getData().getValue()).orElse(1D) + 1);
        }
        System.out.println(id + " go from " + prevLocation.getId() + " to " + location.getId());
    }

    public void doSomething() {
        Random generator = new Random();
        List<? extends SmEvent> values = permissions.stream().collect(Collectors.toList());
        if (values.size() > 0) {
            SmEvent randomValue = values.get(generator.nextInt(values.size()));
            Dispatcher.getInstance().sendMessage(Message.toLocation(randomValue, location.getId()));
        }


    }
}

