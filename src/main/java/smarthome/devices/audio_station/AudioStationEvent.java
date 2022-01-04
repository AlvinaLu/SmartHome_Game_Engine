package smarthome.devices.audio_station;

import smarthome.statemachine.SmEvent;

public enum AudioStationEvent implements SmEvent {
    TURN_OFF, TURN_ON, CHANGE_VOLUME, BROKEN, TURN_UP, TURN_DOWN
}
