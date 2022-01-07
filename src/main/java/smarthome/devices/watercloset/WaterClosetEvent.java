package smarthome.devices.watercloset;

import smarthome.statemachine.SmEvent;

public enum WaterClosetEvent implements SmEvent {
    TURN_OFF, TURN_ON, BROKEN,
}
