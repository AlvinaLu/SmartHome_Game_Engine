package smarthome.devices.kettle;

import smarthome.Dispatcher;
import smarthome.devices.Device;
import smarthome.devices.refrigerator.RefrigeratorEvent;
import smarthome.servises.Scheduler;
import smarthome.statemachine.Message;
import smarthome.statemachine.StateMachine;
import smarthome.statemachine.Transition;

import java.util.concurrent.TimeUnit;

public class Kettle extends StateMachine<KettleState, KettleEvent> implements Device<KettleData> {

    private Scheduler scheduler =Scheduler.getInstance();
    private KettleData kettleData = new KettleData();
    private String id;

    public Kettle() {

        super(KettleState.OFF);


        addTransition(new Transition<>(KettleState.OFF, KettleState.ON, KettleEvent.TURN_ON));
        addTransition(new Transition<>(KettleState.ON, KettleState.OFF, KettleEvent.TURN_OFF));
        addTransition(new Transition<>(KettleState.TIMER_MODE, KettleState.OFF, KettleEvent.TURN_OFF));


        addTransition(new Transition<>(KettleState.ON, KettleState.TIMER_MODE, KettleEvent.TIMER_MODE));

        addTransition(new Transition<>(KettleState.TIMER_MODE, KettleState.HEAT, KettleEvent.HEAT));
        addTransition(new Transition<>(KettleState.ON, KettleState.HEAT, KettleEvent.HEAT));
        addTransition(new Transition<>(KettleState.HEAT, KettleState.ON, KettleEvent.TURN_ON));



        addTransition(new Transition<>(KettleState.ON, KettleState.TEMPERATURE_MAINTENANCE, KettleEvent.TEMPERATURE_MAINTENANCE));
        addTransition(new Transition<>(KettleState.TEMPERATURE_MAINTENANCE, KettleState.OFF, KettleEvent.TURN_OFF));
        addTransition(new Transition<>(KettleState.TEMPERATURE_MAINTENANCE, KettleState.ON, KettleEvent.TURN_ON));

    }

    @Override
    protected void onEnter(KettleState currentState) {

        if(currentState.equals(KettleState.HEAT) && getData().getCurrentTemperature()!=100){

            setCurrentTask(scheduler.schedule(() -> {
                this.kettleData.setCurrentTemperature(100);
                Dispatcher.getInstance().sendMessage(Message.toDevice(KettleEvent.TURN_ON,id));
                System.out.println("The kettle has gone to ashes to 100 degrees!");
                coolingDownTheKettle();
            }, 5, TimeUnit.MINUTES));

        }else if(currentState.equals(KettleState.HEAT) && getData().getCurrentTemperature()==100){
            System.out.println("The kettle is still hot!");
            Dispatcher.getInstance().sendMessage(Message.toDevice(KettleEvent.TURN_ON,id));

        }else if(currentState.equals(KettleState.TEMPERATURE_MAINTENANCE)){
            setCurrentTask(scheduler.schedule(() -> {
                this.kettleData.setCurrentTemperature(100);
                System.out.println("The kettle temperature is being maintained at 100 degrees!");
            }, 5, TimeUnit.MINUTES));

        }else if(currentState.equals(KettleState.TIMER_MODE)){
            int timer = (Integer) getMessageData().get("timer"); //in hours
            System.out.println("Turning on the kettle after " + timer +" hours");

            setCurrentTask(scheduler.schedule(() -> {
                Dispatcher.getInstance().sendMessage(Message.toDevice(KettleEvent.HEAT,id));
            }, timer, TimeUnit.HOURS));

        }

    }

    public void coolingDownTheKettle(){
        setCurrentTask(scheduler.schedule(() -> {
            this.kettleData.setCurrentTemperature(21);
            System.out.println("The kettle is cold!");
        }, 3, TimeUnit.HOURS));
    }

    @Override
    public String getId() { return this.id; }

    @Override
    public KettleEvent toEvent(String name) {
        return KettleEvent.valueOf(name);
    }

    @Override
    public KettleData getData() {
        return this.kettleData;
    }

    @Override
    public String getDoc() {
        return "Kettle";
    }

    @Override
    public void setId(String id) { this.id = id; }

    @Override
    public void setData(KettleData data) { this.kettleData = data; }

}