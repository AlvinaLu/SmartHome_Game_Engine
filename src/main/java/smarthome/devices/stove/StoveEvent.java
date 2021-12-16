package smarthome.devices.stove;

import smarthome.statemachine.SmEvent;

public enum StoveEvent implements SmEvent {
    TURN_ON,
    TURN_OFF,
    LOCK,
    UNLOCK
}
