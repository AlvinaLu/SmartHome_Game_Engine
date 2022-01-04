package smarthome.devices.tv;

import smarthome.devices.ConsumingState;
import smarthome.devices.Resource;

import java.util.Collections;
import java.util.Map;

public enum TVState implements ConsumingState {
    ON(Resource.ELECTRICITY, 2D), OFF(), TURN_UP(), TURN_DOWN(), CHANGE_VOLUME(), CHANGE_CHANNEL();

    TVState() {
        this(Collections.emptyMap());
    }

    TVState(Resource resource, Double amount) {
        this(Map.of(resource, amount));
    }

    TVState(Map<Resource, Double> consumption) {
        this.consumption = consumption;
    }

    private  Map<Resource, Double> consumption;

    @Override
    public Map<Resource, Double> consumption() {
        return consumption;
    }
}
