package smarthome.devices.audio_station;

import smarthome.devices.Device;

import smarthome.servises.Scheduler;
import smarthome.statemachine.StateMachine;
import smarthome.statemachine.Transition;

public class AudioStation extends StateMachine<AudioStationState, AudioStationEvent> implements Device<AudioStationData> {

    private Scheduler scheduler =Scheduler.getInstance();
    private String id;
    private AudioStationData audioStationData = new AudioStationData();

    public AudioStation(){

        super(AudioStationState.OFF);
        addTransition(new Transition<>(AudioStationState.OFF, AudioStationState.ON, AudioStationEvent.TURN_ON));
        addTransition(new Transition<>(AudioStationState.ON, AudioStationState.OFF, AudioStationEvent.TURN_OFF));
        addTransition(new Transition<>(AudioStationState.TURN_UP, AudioStationState.OFF, AudioStationEvent.TURN_OFF));
        addTransition(new Transition<>(AudioStationState.TURN_DOWN, AudioStationState.OFF, AudioStationEvent.TURN_OFF));
        addTransition(new Transition<>(AudioStationState.ON, AudioStationState.CHANGE_VOLUME, AudioStationEvent.CHANGE_VOLUME));
        addTransition(new Transition<>(AudioStationState.CHANGE_VOLUME, AudioStationState.TURN_UP, AudioStationEvent.TURN_UP));
        addTransition(new Transition<>(AudioStationState.CHANGE_VOLUME, AudioStationState.TURN_DOWN, AudioStationEvent.TURN_DOWN));
        addTransition(new Transition<>(AudioStationState.TURN_UP, AudioStationState.ON, AudioStationEvent.TURN_ON));
        addTransition(new Transition<>(AudioStationState.TURN_DOWN, AudioStationState.ON, AudioStationEvent.TURN_ON));

    }


    @Override
    protected void onEnter(AudioStationState currentState) {

    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public AudioStationData getData() {
        return this.audioStationData;
    }

    @Override
    public String getDoc() {
        return "Audio";
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setData(AudioStationData data) {
        this.audioStationData = data;
    }
}
