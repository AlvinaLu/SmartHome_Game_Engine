package smarthome.devices.refrigerator;

import smarthome.statemachine.SmEvent;

public enum RefrigeratorEvent implements SmEvent {
    TURN_OFF, TURN_ON, CHANGE_TEMPERATURE, BROKEN
}
