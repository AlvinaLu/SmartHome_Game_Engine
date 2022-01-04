package smarthome.devices.heater;

import smarthome.devices.ConsumingState;
import smarthome.devices.Resource;

import java.util.Collections;
import java.util.Map;

public enum HeaterState implements ConsumingState {
    ON(Resource.ELECTRICITY, 30D), OFF(), BROKEN();


    HeaterState() {
        this(Collections.emptyMap());
    }

    HeaterState(Resource resource, Double amount) {
        this(Map.of(resource, amount));
    }


    HeaterState(Map<Resource, Double> consumption) {
        this.consumption = consumption;
    }

    private  Map<Resource, Double> consumption;

    @Override
    public Map<Resource, Double> consumption() {
        return consumption;
    }
}
