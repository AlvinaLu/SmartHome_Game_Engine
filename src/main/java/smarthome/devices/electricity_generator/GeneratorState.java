package smarthome.devices.electricity_generator;

import smarthome.devices.ConsumingState;
import smarthome.devices.Resource;

import java.util.Collections;
import java.util.Map;

public enum GeneratorState implements ConsumingState {
    ON(Map.of(Resource.ELECTRICITY, -5D, Resource.PETROL, 150D)), OFF(), BROKEN();
    private  Map<Resource, Double> consumption;

    GeneratorState() {
        this(Collections.emptyMap());
    }

    GeneratorState(Map<Resource, Double> consumption) {
        this.consumption = consumption;
    }
    @Override
    public Map<Resource, Double> consumption() {
        return consumption;
    }
}
