package smarthome.devices.heater;

import smarthome.statemachine.SmEvent;

public enum HeaterEvent implements SmEvent {
    TURN_OFF, TURN_ON, ECO, INTENSIVE, GLASS, BROKEN
}
