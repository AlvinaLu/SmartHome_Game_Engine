package smarthome.devices.audio_station;

import smarthome.devices.ConsumingState;
import smarthome.devices.Resource;

import java.util.Collections;
import java.util.Map;

public enum AudioStationState implements ConsumingState {
    ON(Resource.ELECTRICITY, 1D), OFF(), TURN_UP(Resource.ELECTRICITY, 0.01D), TURN_DOWN(), PLAY(Resource.ELECTRICITY, 1D), PAUSE();

    AudioStationState() {
        this(Collections.emptyMap());
    }

    AudioStationState(Resource resource, Double amount) {
        this(Map.of(resource, amount));
    }

    AudioStationState(Map<Resource, Double> consumption) {
        this.consumption = consumption;
    }

    private  Map<Resource, Double> consumption;

    @Override
    public Map<Resource, Double> consumption() {
        return consumption;
    }

}
