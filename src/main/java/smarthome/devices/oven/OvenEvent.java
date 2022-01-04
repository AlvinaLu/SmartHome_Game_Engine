package smarthome.devices.oven;

import smarthome.statemachine.SmEvent;

public enum OvenEvent implements SmEvent {

    TURN_OFF,
    TURN_ON,
    CHANGE_TEMPERATURE,
    SET_MODE
}
