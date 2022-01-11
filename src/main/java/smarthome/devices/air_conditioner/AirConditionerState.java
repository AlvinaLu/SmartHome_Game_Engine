package smarthome.devices.air_conditioner;

import smarthome.devices.ConsumingState;
import smarthome.devices.Resource;

import java.util.Collections;
import java.util.Map;

public enum AirConditionerState implements ConsumingState {
    ON(Resource.ELECTRICITY, 6D), OFF(), COOLING(Resource.ELECTRICITY, 5D), HEATING(Resource.ELECTRICITY, 5D), CHANGE_TEMPERATURE();

    AirConditionerState() {
        this(Collections.emptyMap());
    }

    AirConditionerState(Resource resource, Double amount) {
        this(Map.of(resource, amount));
    }


    AirConditionerState(Map<Resource, Double> consumption) {
        this.consumption = consumption;
    }

    private  Map<Resource, Double> consumption;

    @Override
    public Map<Resource, Double> consumption() {
        return consumption;
    }
}
