package smarthome.devices.kettle;

import smarthome.statemachine.SmEvent;

public enum KettleEvent implements SmEvent {
    TURN_ON,
    TURN_OFF,
    TIMER_MODE,
    TEMPERATURE_MAINTENANCE, //поддержка температуры
    HEAT
}
