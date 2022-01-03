package smarthome.sensors;

import smarthome.devices.Device;

import java.util.EventListener;
import java.util.HashSet;
import java.util.Set;

public abstract class Sensor implements Device<SensorData>{
    Set<Listener> listenerSet = new HashSet<>();

    public void setValue(Double value){
        getData().setValue(value);
        listenerSet.forEach(it-> it.onValueChanged(value));
    }

    public void addListener(Listener listener){
        listenerSet.add(listener);
    }

    @FunctionalInterface
    public interface Listener{
        void onValueChanged(Double value);
    }
}