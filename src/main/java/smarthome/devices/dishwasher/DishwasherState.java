package smarthome.devices.dishwasher;

import smarthome.devices.ConsumingState;
import smarthome.devices.Resource;

import java.util.Collections;
import java.util.Map;

public enum DishwasherState implements ConsumingState {
    ON(Resource.ELECTRICITY, 3D),
    OFF(),
    INTENSIVE(Map.of(Resource.ELECTRICITY, 50D, Resource.WATER, 5D)),
    ECO(Map.of(Resource.ELECTRICITY, 40D, Resource.WATER, 6D)),
    GLASS(Map.of(Resource.ELECTRICITY, 30D, Resource.WATER, 4D));


    DishwasherState() {
        this(Collections.emptyMap());
    }

    DishwasherState(Resource resource, Double amount) {
        this(Map.of(resource, amount));
    }


    DishwasherState(Map<Resource, Double> consumption) {
        this.consumption = consumption;
    }

    private  Map<Resource, Double> consumption;

    @Override
    public Map<Resource, Double> consumption() {
        return consumption;
    }
}

