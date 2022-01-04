package smarthome.statemachine;

import smarthome.servises.DeviceLog;
import smarthome.servises.LocationConfiguration;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

public abstract class StateMachine<NAME, EVENT extends SmEvent> {
    private NAME currentState;
    private Future<?> currentTask;
    private Set<Transition<NAME,EVENT>> transitions = new HashSet<>();

    private Map<String,Object> messageData;//Save additional data from message, for stateMachine state processing;

    public StateMachine(NAME currentState) {
        this.currentState = currentState;
    }

    public void onMessage(Message<EVENT> message) {
        try {
            Transition<NAME, EVENT> transition = transitions.stream()
                    .filter(it -> it.getSource().equals(currentState) && it.getEvent().equals(message.getEvent()))
                    .findAny()
                    .orElse(null);

            if (transition==null) {
                //System.out.println(this+": transition not found from state "+currentState+" for event "+message.getEvent().getClass().getSimpleName()+"."+message.getEvent());
                return;
            }

            if (currentTask!=null){
                currentTask.cancel(true);
            }
            DeviceLog.getInstance().addEntry(getId(), transition.getTarget());
            System.out.println(this+": transitioning from "  + transition.getSource() + " to " + transition.getTarget());
            currentState = transition.getTarget();
            messageData = message.getData();
            LocationConfiguration.getInstance().save();
            onEnter(currentState);


        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public void init(){
        onEnter(currentState);
    }



    protected abstract void onEnter(NAME currentState);

    public void setCurrentTask(Future<?> currentTask) {
        this.currentTask = currentTask;
    }

    protected abstract String getId();

    public Map<String, Object> getMessageData() {
        return messageData;
    }

    public NAME getCurrentState() {
        return currentState;
    }

    public void addTransition(Transition<NAME,EVENT> transition) {
        transitions.add(transition);
    }

    public void setMessageData(Map<String, Object> messageData) {
        this.messageData = messageData;
    }

    public void setCurrentState(NAME currentState) {
        this.currentState = currentState;
    }

    public abstract EVENT toEvent(String name);
}
