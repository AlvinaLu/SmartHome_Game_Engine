package smarthome.devices.coffee_machine;

import smarthome.devices.ConsumingState;
import smarthome.devices.Resource;

import java.util.Collections;
import java.util.Map;

public enum CoffeeMachineState implements ConsumingState {
    OFF(),
    ON(Resource.ELECTRICITY, 1D),
    POURS_COFFEE(Map.of(Resource.WATER, 0.5D, Resource.ELECTRICITY, 2D));

    private  Map<Resource, Double> consumption;

    CoffeeMachineState() { this(Collections.emptyMap()); }

    CoffeeMachineState(Resource resource, Double amount){this(Map.of(resource, amount));}

    CoffeeMachineState(Map<Resource, Double> consumption) {this.consumption = consumption;}

    @Override
    public Map<Resource, Double> consumption() { return this.consumption; }
}
