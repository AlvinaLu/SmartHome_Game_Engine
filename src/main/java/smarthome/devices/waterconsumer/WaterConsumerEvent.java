package smarthome.devices.waterconsumer;

import smarthome.statemachine.SmEvent;

public enum WaterConsumerEvent implements SmEvent {
    TURN_OFF, TURN_ON_COLD_WATER, TURN_ON_WARM_WATER, TURN_ON_HOT_WATER, BROKEN,
}
