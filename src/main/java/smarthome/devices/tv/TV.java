package smarthome.devices.tv;

import smarthome.Dispatcher;
import smarthome.devices.Device;

import smarthome.devices.audio_station.AudioStationEvent;
import smarthome.servises.Scheduler;
import smarthome.statemachine.Message;
import smarthome.statemachine.StateMachine;
import smarthome.statemachine.Transition;

public class TV extends StateMachine<TVState, TVEvent> implements Device<TVData> {

    private Scheduler scheduler =Scheduler.getInstance();
    private String id;
    private TVData tvData = new TVData();

    public TV() {

        super(TVState.OFF);
        addTransition(new Transition<>(TVState.OFF, TVState.ON, TVEvent.TURN_ON));
        addTransition(new Transition<>(TVState.ON, TVState.OFF, TVEvent.TURN_OFF));

        addTransition(new Transition<>(TVState.ON, TVState.TURN_UP, TVEvent.TURN_UP));
        addTransition(new Transition<>(TVState.ON, TVState.TURN_DOWN, TVEvent.TURN_DOWN));

        addTransition(new Transition<>(TVState.TURN_UP, TVState.ON, TVEvent.TURN_ON));
        addTransition(new Transition<>(TVState.TURN_DOWN, TVState.ON, TVEvent.TURN_ON));

        addTransition(new Transition<>(TVState.ON, TVState.CHANGE_CHANNEL, TVEvent.CHANGE_CHANNEL));
        addTransition(new Transition<>(TVState.CHANGE_CHANNEL, TVState.ON, TVEvent.TURN_ON));


    }

    @Override
    protected void onEnter(TVState currentState) {

        if(currentState.equals(TVState.TURN_DOWN)){
            int volume = (int) getMessageData().get("volume");
            this.tvData.setCurrentVolume(this.getData().getCurrentVolume() - volume);
            Dispatcher.getInstance().sendMessage(Message.toDevice(TVEvent.TURN_ON, id));
        }else if(currentState.equals(TVState.TURN_UP)){
            int volume = (int) getMessageData().get("volume");
            this.tvData.setCurrentVolume(this.getData().getCurrentVolume() + volume);
            Dispatcher.getInstance().sendMessage(Message.toDevice(TVEvent.TURN_ON, id));
        }else if(currentState.equals(TVState.CHANGE_CHANNEL)){
            int channel = (int) getMessageData().get("channel");
            this.tvData.setCurrentСhannel(channel);
            Dispatcher.getInstance().sendMessage(Message.toDevice(TVEvent.TURN_ON, id));
        }

    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public TVEvent toEvent(String name) {
        return TVEvent.valueOf(name);
    }

    @Override
    public TVData getData() {
        return this.tvData;
    }

    @Override
    public String getDoc() {
        return "TV";
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setData(TVData data) {
        this.tvData = data;
    }
}
