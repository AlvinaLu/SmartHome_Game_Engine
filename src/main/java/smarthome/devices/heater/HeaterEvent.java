package smarthome.devices.heater;

import smarthome.statemachine.SmEvent;

public enum HeaterEvent implements SmEvent {
    TURN_ON, TURN_OFF, BROKE, CHANGE_TEMPERATURE
}
