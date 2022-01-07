package smarthome.devices.air_conditioner;

import smarthome.statemachine.SmEvent;

public enum AirConditionerEvent implements SmEvent {
    TURN_OFF, TURN_ON, CHANGE_TEMPERATURE, BROKEN, COOL, HEAT, QUIET_MODE
}
