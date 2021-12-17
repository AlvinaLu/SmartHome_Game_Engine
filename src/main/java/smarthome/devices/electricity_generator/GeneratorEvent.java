package smarthome.devices.electricity_generator;

import smarthome.statemachine.SmEvent;

public enum GeneratorEvent implements SmEvent {
    TURN_ON, TURN_OFF, BROKEN
}
