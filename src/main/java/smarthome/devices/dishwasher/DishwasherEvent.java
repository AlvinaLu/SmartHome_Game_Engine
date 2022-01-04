package smarthome.devices.dishwasher;

import smarthome.statemachine.SmEvent;

public enum DishwasherEvent implements SmEvent {
    TURN_OFF, TURN_ON, ECO, INTENSIVE, GLASS, BROKEN
}
