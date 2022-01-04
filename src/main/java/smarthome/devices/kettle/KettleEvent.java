package smarthome.devices.kettle;

import smarthome.statemachine.SmEvent;

public enum KettleEvent implements SmEvent {
    TURN_ON,
    TURN_OFF,
    Timer_Mode,
    Temperature_Maintenance //поддержка температуры
}
