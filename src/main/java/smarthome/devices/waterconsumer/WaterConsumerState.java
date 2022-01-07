package smarthome.devices.waterconsumer;

import smarthome.devices.ConsumingState;
import smarthome.devices.Resource;
import java.util.Collections;
import java.util.Map;

public enum WaterConsumerState implements ConsumingState {
    COLD_WATER(Resource.WATER, 3D),
    OFF(),
    WARM_WATER(Map.of(Resource.ELECTRICITY, 5D,Resource.WATER, 3D)),
    HOT_WATER(Map.of(Resource.ELECTRICITY, 10D, Resource.WATER, 3D));

    WaterConsumerState() {
        this(Collections.emptyMap());
    }

    WaterConsumerState(Resource resource, Double amount) {
        this(Map.of(resource, amount));
    }


    WaterConsumerState(Map<Resource, Double> consumption) {
        this.consumption = consumption;
    }

    private  Map<Resource, Double> consumption;

    @Override
    public Map<Resource, Double> consumption() {
        return consumption;
    }
}
