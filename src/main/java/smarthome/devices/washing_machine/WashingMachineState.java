package smarthome.devices.washing_machine;

import smarthome.devices.ConsumingState;
import smarthome.devices.Resource;

import java.util.Collections;
import java.util.Map;

public enum WashingMachineState implements ConsumingState {
    ON(Resource.ELECTRICITY, 3D),
    OFF(),
    COTTON(Map.of(Resource.ELECTRICITY, 50D,Resource.WATER, 5D)),
    QUICK_WASH(Map.of(Resource.ELECTRICITY, 10D, Resource.WATER, 4D)),
    ANTI_ALLERGY(Map.of(Resource.ELECTRICITY, 30D, Resource.WATER, 4D)),
    WOOL(Map.of(Resource.ELECTRICITY, 35D, Resource.WATER, 8D));


    WashingMachineState() {
        this(Collections.emptyMap());
    }

    WashingMachineState(Resource resource, Double amount) {
        this(Map.of(resource, amount));
    }


    WashingMachineState(Map<Resource, Double> consumption) {
        this.consumption = consumption;
    }

    private  Map<Resource, Double> consumption;

    @Override
    public Map<Resource, Double> consumption() {
        return consumption;
    }
}
