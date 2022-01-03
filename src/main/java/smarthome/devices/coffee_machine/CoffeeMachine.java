package smarthome.devices.coffee_machine;

import smarthome.devices.Device;
import smarthome.statemachine.StateMachine;
import smarthome.statemachine.Transition;

public class CoffeeMachine extends StateMachine<CoffeeMachineState, CoffeeMachineEvent> implements Device<CoffeeMachineData> {

    private String id;
    private CoffeeMachineData coffeeMachineData = new CoffeeMachineData();

    public CoffeeMachine() {

        super(CoffeeMachineState.OFF);
        addTransition(new Transition<>(CoffeeMachineState.OFF, CoffeeMachineState.ON, CoffeeMachineEvent.TURN_ON));
        addTransition(new Transition<>(CoffeeMachineState.ON, CoffeeMachineState.OFF, CoffeeMachineEvent.TURN_OFF));
        addTransition(new Transition<>(CoffeeMachineState.OFF, CoffeeMachineState.CHOOSE_TYPE_OF_COFFEE, CoffeeMachineEvent.TURN_ON));
        addTransition(new Transition<>(CoffeeMachineState.ON, CoffeeMachineState.CHOOSE_TYPE_OF_COFFEE, CoffeeMachineEvent.CHOOSING_A_VARIETY_OF_COFFEE));

    }

    @Override
    protected void onEnter(CoffeeMachineState currentState) {

    }

    @Override
    public String getId() { return this.id; }

    @Override
    public CoffeeMachineData getData() { return this.coffeeMachineData; }

    @Override
    public String getDoc() { return "Coffe Machine"; }

    @Override
    public void setId(String id) { this.id = id; }

    @Override
    public void setData(CoffeeMachineData data) { this.coffeeMachineData = data; }

}
