package smarthome.devices.audio_station;

import smarthome.statemachine.SmEvent;

public enum AudioStationEvent implements SmEvent {
    TURN_OFF, TURN_ON, PLAY, BROKEN, TURN_UP, TURN_DOWN, PAUSE
}
