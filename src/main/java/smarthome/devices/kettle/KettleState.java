package smarthome.devices.kettle;

import smarthome.devices.ConsumingState;
import smarthome.devices.Resource;

import java.util.Collections;
import java.util.Map;

public enum KettleState implements ConsumingState {
    ON(), OFF(), TIMER_MODE(), TEMPERATURE_MAINTENANCE(Resource.ELECTRICITY, 7D), HEAT(Resource.ELECTRICITY, 10D);

    KettleState() {
        this(Collections.emptyMap());
    }

    KettleState(Resource resource, Double amount) {
        this(Map.of(resource, amount));
    }


    KettleState(Map<Resource, Double> consumption) {
        this.consumption = consumption;
    }

    private  Map<Resource, Double> consumption;

    @Override
    public Map<Resource, Double> consumption() {
        return consumption;
    }

}
