package smarthome.devices.refrigerator;

import smarthome.devices.ConsumingState;
import smarthome.devices.Resource;

import java.util.Collections;
import java.util.Map;

public enum RefrigeratorState implements ConsumingState {
    ON(Resource.ELECTRICITY, 3D), OFF(), COOLING(Resource.ELECTRICITY, 5D), HEATING(Resource.ELECTRICITY, 0.1D), UNFREEZE(), CHANGE_TEMPERATURE();

    RefrigeratorState() {
        this(Collections.emptyMap());
    }

    RefrigeratorState(Resource resource, Double amount) {
        this(Map.of(resource, amount));
    }


    RefrigeratorState(Map<Resource, Double> consumption) {
        this.consumption = consumption;
    }

    private  Map<Resource, Double> consumption;

    @Override
    public Map<Resource, Double> consumption() {
        return consumption;
    }
}

