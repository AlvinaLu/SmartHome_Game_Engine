package smarthome.devices.tv;

import smarthome.statemachine.SmEvent;

public enum TVEvent implements SmEvent {
    TURN_OFF, TURN_ON, CHANGE_VOLUME, CHANGE_CHANNEL, BROKEN, TURN_UP, TURN_DOWN
}
