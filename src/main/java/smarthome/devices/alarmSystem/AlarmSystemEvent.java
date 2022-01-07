package smarthome.devices.alarmSystem;

import smarthome.statemachine.SmEvent;

public enum AlarmSystemEvent implements SmEvent {
    TURN_ON, TURN_OFF, TURN_ALARM, BROKEN
}
