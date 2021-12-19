package smarthome.statemachine;

import smarthome.servises.DeviceLog;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;

public abstract class StateMachine<NAME, EVENT extends SmEvent> {
    private State<NAME, EVENT> currentState;
    private Future<?> currentTask;
    private Set<Transition<NAME,EVENT>> transitions = new HashSet<>();

    public StateMachine(State<NAME, EVENT> currentState) {
        this.currentState = currentState;
    }

    public void onMessage(Message<EVENT> message) {
        try {
            Transition<NAME, EVENT> transition = transitions.stream()
                    .filter(it -> it.getSource().equals(currentState.getName()) && it.getEvent().equals(message.getEvent()))
                    .findAny()
                    .orElse(null);

            if (transition==null) {
                System.out.println(this+": transition not found for event "+message.getEvent().getClass().getSimpleName()+"."+message.getEvent());
                return;
            }

            if (currentTask!=null){
                currentTask.cancel(true);
            }
            DeviceLog.getInstance().addEntry(getId(), transition.getTarget());
            System.out.println(this+": transitioning from "  + transition.getSource() + " to " + transition.getTarget());
            currentState = new State<>(transition.getTarget());
            onTransition(transition, message);


        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public void setCurrentTask(Future<?> currentTask) {
        this.currentTask = currentTask;
    }

    protected abstract void onTransition(Transition<NAME, EVENT> transition, Message<EVENT> message);

    protected abstract String getId();

    public void addTransition(Transition<NAME,EVENT> transition) {
        transitions.add(transition);
    }
}
