package smarthome.devices.lamp;

import smarthome.devices.ConsumingState;
import smarthome.devices.Resource;

import java.util.Collections;
import java.util.Map;

public enum LampState implements ConsumingState  {
    OFF(), ON(Resource.ELECTRICITY, 3D), BROKEN();

    LampState() {
        this(Collections.emptyMap());
    }

    LampState(Resource resource, Double amount) {
        this(Map.of(resource, amount));
    }


    LampState(Map<Resource, Double> consumption) {
        this.consumption = consumption;
    }

    private  Map<Resource, Double> consumption;

    @Override
    public Map<Resource, Double> consumption() {
        return consumption;
    }
}
