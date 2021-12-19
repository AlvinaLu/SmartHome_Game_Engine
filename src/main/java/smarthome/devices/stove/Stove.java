package smarthome.devices.stove;

import smarthome.devices.Device;
import smarthome.devices.lamp.Lamp;
import smarthome.devices.refrigerator.RefrigeratorData;
import smarthome.devices.refrigerator.RefrigeratorEvent;
import smarthome.devices.refrigerator.RefrigeratorState;
import smarthome.servises.IdGenerator;
import smarthome.statemachine.Message;
import smarthome.statemachine.State;
import smarthome.statemachine.StateMachine;
import smarthome.statemachine.Transition;

import java.util.UUID;

public class Stove extends StateMachine<StoveState, StoveEvent> implements Device<StoveData> {
    private String id;

    public Stove(){
        super(new State<>(StoveState.OFF));

        addTransition(new Transition<>(StoveState.OFF, StoveState.ON, StoveEvent.TURN_ON));
        addTransition(new Transition<>(StoveState.ON, StoveState.OFF, StoveEvent.TURN_OFF));
        addTransition(new Transition<>(StoveState.ON, StoveState.LOCK, StoveEvent.LOCK));
        addTransition(new Transition<>(StoveState.LOCK, StoveState.ON, StoveEvent.UNLOCK));

    }
    private StoveData stoveData = new StoveData();

    @Override
    public StoveData getData(){
        return stoveData;
    }

    @Override
    public String getDoc(){

        return "huj";
    }
    @Override
    protected void onTransition(Transition<StoveState, StoveEvent> transition, Message<StoveEvent> message){

    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
