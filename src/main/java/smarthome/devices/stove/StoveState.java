package smarthome.devices.stove;

import smarthome.devices.ConsumingState;
import smarthome.devices.Resource;

import java.util.Collections;
import java.util.Map;

public enum StoveState implements ConsumingState {

    ON(Resource.ELECTRICITY, 30D), OFF(), BROKEN();

    StoveState() {
        this(Collections.emptyMap());
    }

    StoveState(Resource resource, Double amount) {
        this(Map.of(resource, amount));
    }


    StoveState(Map<Resource, Double> consumption) {
        this.consumption = consumption;
    }

    private  Map<Resource, Double> consumption;


    @Override
    public Map<Resource, Double> consumption() {
        return consumption;
    }
}
