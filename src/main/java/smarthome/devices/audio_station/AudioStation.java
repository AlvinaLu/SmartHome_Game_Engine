package smarthome.devices.audio_station;

import smarthome.Dispatcher;
import smarthome.devices.Device;

import smarthome.devices.coffee_machine.CoffeeMachineEvent;
import smarthome.servises.Scheduler;
import smarthome.statemachine.Message;
import smarthome.statemachine.StateMachine;
import smarthome.statemachine.Transition;

import java.util.concurrent.TimeUnit;

public class AudioStation extends StateMachine<AudioStationState, AudioStationEvent> implements Device<AudioStationData> {

    private Scheduler scheduler =Scheduler.getInstance();
    private String id;
    private AudioStationData audioStationData = new AudioStationData();

    public AudioStation(){

        super(AudioStationState.OFF);
        addTransition(new Transition<>(AudioStationState.OFF, AudioStationState.ON, AudioStationEvent.TURN_ON));
        addTransition(new Transition<>(AudioStationState.ON, AudioStationState.OFF, AudioStationEvent.TURN_OFF));

        addTransition(new Transition<>(AudioStationState.ON, AudioStationState.TURN_UP, AudioStationEvent.TURN_UP));
        addTransition(new Transition<>(AudioStationState.ON, AudioStationState.TURN_DOWN, AudioStationEvent.TURN_DOWN));

        addTransition(new Transition<>(AudioStationState.TURN_UP, AudioStationState.ON, AudioStationEvent.TURN_ON));
        addTransition(new Transition<>(AudioStationState.TURN_DOWN, AudioStationState.ON, AudioStationEvent.TURN_ON));

        addTransition(new Transition<>(AudioStationState.ON, AudioStationState.PLAY, AudioStationEvent.PLAY));
        addTransition(new Transition<>(AudioStationState.PLAY, AudioStationState.PAUSE, AudioStationEvent.PAUSE));
        addTransition(new Transition<>(AudioStationState.PLAY, AudioStationState.ON, AudioStationEvent.TURN_ON));
        addTransition(new Transition<>(AudioStationState.PAUSE, AudioStationState.PLAY, AudioStationEvent.PLAY));

        addTransition(new Transition<>(AudioStationState.PAUSE, AudioStationState.OFF, AudioStationEvent.TURN_OFF));
        addTransition(new Transition<>(AudioStationState.PLAY, AudioStationState.OFF, AudioStationEvent.TURN_OFF));


    }


    @Override
    protected void onEnter(AudioStationState currentState) {

        if (currentState.equals(AudioStationState.PLAY)){

            String audio = (String) getMessageData().get("audio");

            for (Audios a: Audios.values()){
                if (audio.equals(a.name())) {
                    System.out.println("Playing " + audio);
                    this.audioStationData.setAudio(a);

                    setCurrentTask(scheduler.schedule(() -> {
                        Dispatcher.getInstance().sendMessage(Message.toDevice(AudioStationEvent.TURN_ON, id));
                    }, 3, TimeUnit.MINUTES));
                }
            }

        } else if(currentState.equals(AudioStationState.TURN_DOWN)){
            int volume = (int) getMessageData().get("volume");
            this.audioStationData.setCurrentVolume(this.getData().getCurrentVolume() - volume);
            Dispatcher.getInstance().sendMessage(Message.toDevice(AudioStationEvent.TURN_ON, id));
        }else if(currentState.equals(AudioStationState.TURN_UP)){
            int volume = (int) getMessageData().get("volume");
            this.audioStationData.setCurrentVolume(this.getData().getCurrentVolume() + volume);
            Dispatcher.getInstance().sendMessage(Message.toDevice(AudioStationEvent.TURN_ON, id));
        }

    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public AudioStationEvent toEvent(String name) {
        return AudioStationEvent.valueOf(name);
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
