package smarthome.devices.coffee_machine;

import smarthome.Dispatcher;
import smarthome.devices.Device;
import smarthome.servises.Scheduler;
import smarthome.statemachine.Message;
import smarthome.statemachine.StateMachine;
import smarthome.statemachine.Transition;

import java.util.concurrent.TimeUnit;

public class CoffeeMachine extends StateMachine<CoffeeMachineState, CoffeeMachineEvent> implements Device<CoffeeMachineData> {

    private Scheduler scheduler = Scheduler.getInstance();
    private String id;
    private CoffeeMachineData coffeeMachineData = new CoffeeMachineData();

    public CoffeeMachine() {

        super(CoffeeMachineState.OFF);
        addTransition(new Transition<>(CoffeeMachineState.OFF, CoffeeMachineState.ON, CoffeeMachineEvent.TURN_ON));
        addTransition(new Transition<>(CoffeeMachineState.ON, CoffeeMachineState.OFF, CoffeeMachineEvent.TURN_OFF));
        addTransition(new Transition<>(CoffeeMachineState.ON, CoffeeMachineState.POURS_COFFEE, CoffeeMachineEvent.POURS_COFFEE));
        addTransition(new Transition<>(CoffeeMachineState.POURS_COFFEE, CoffeeMachineState.ON, CoffeeMachineEvent.TURN_ON));
        addTransition(new Transition<>(CoffeeMachineState.POURS_COFFEE, CoffeeMachineState.OFF, CoffeeMachineEvent.TURN_OFF));

    }

    @Override
    protected void onEnter(CoffeeMachineState currentState) {
        if (currentState.equals(CoffeeMachineState.POURS_COFFEE)) {
            String targetCoffee = (String) getMessageData().get("coffee");
            coffeeMachineData.setCoffee(targetCoffee);
            if (targetCoffee.equals("Latte")){
                setCurrentTask(scheduler.schedule(() -> {
                    Dispatcher.getInstance().sendMessage(Message.toDevice(CoffeeMachineEvent.TURN_ON, id));
                    System.out.println("Coffee Latte is ready ");
                }, 5, TimeUnit.MINUTES));
            }
            if (targetCoffee.equals("Cappuccino")){
                setCurrentTask(scheduler.schedule(() -> {
                    Dispatcher.getInstance().sendMessage(Message.toDevice(CoffeeMachineEvent.TURN_ON, id));
                    System.out.println("Coffee Cappuccino is ready ");
                }, 6, TimeUnit.MINUTES));
            }
            if (targetCoffee.equals("Americano")){
                setCurrentTask(scheduler.schedule(() -> {
                    Dispatcher.getInstance().sendMessage(Message.toDevice(CoffeeMachineEvent.TURN_ON, id));
                    System.out.println("Coffee Americano is ready ");
                }, 4, TimeUnit.MINUTES));
            }

        }

    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public CoffeeMachineEvent toEvent(String name) {
        return CoffeeMachineEvent.valueOf(name);
    }

    @Override
    public CoffeeMachineData getData() {
        return this.coffeeMachineData;
    }

    @Override
    public String getDoc() {
        return "Coffee Machine";
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setData(CoffeeMachineData data) {
        this.coffeeMachineData = data;
    }


    @Override
    public String toString() {
        return "CoffeeMachine{" +
                "id='" + id + '\'' +
                '}';
    }
}
