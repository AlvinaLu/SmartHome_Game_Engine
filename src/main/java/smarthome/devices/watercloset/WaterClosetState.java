package smarthome.devices.watercloset;

import smarthome.devices.ConsumingState;
import smarthome.devices.Resource;

import java.util.Collections;
import java.util.Map;

public enum WaterClosetState implements ConsumingState {
    ON(Resource.WATER, 3D),
    OFF();

    WaterClosetState() {
        this(Collections.emptyMap());
    }

    WaterClosetState(Resource resource, Double amount) {
        this(Map.of(resource, amount));
    }


    WaterClosetState(Map<Resource, Double> consumption) {
        this.consumption = consumption;
    }

    private  Map<Resource, Double> consumption;

    @Override
    public Map<Resource, Double> consumption() {
        return consumption;
    }
}
