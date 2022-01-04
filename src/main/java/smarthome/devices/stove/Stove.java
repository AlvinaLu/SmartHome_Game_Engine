package smarthome.devices.stove;

import smarthome.devices.Device;
import smarthome.statemachine.StateMachine;
import smarthome.statemachine.Transition;

public class Stove extends StateMachine<StoveState, StoveEvent> implements Device<StoveData> {
    private String id;

    public Stove(){
        super(StoveState.OFF);

        addTransition(new Transition<>(StoveState.OFF, StoveState.ON, StoveEvent.TURN_ON));
        addTransition(new Transition<>(StoveState.ON, StoveState.OFF, StoveEvent.TURN_OFF));

    }
    private StoveData stoveData = new StoveData();

    @Override
    public StoveData getData(){
        return stoveData;
    }

    @Override
    public String getDoc(){

        return "Stove!";
    }

    @Override
    protected void onEnter(StoveState currentState) {

    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public StoveEvent toEvent(String name) {
        return StoveEvent.valueOf(name);
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setData(StoveData data) {
        stoveData = data;
    }
}
