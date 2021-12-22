package smarthome.devices;

import java.util.Map;

public interface ConsumingState {
    Map<Resource, Double> consumption();
}
