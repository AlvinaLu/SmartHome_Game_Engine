package smarthome.skinbag;

import jdk.jfr.Event;
import smarthome.statemachine.SmEvent;

import java.util.Set;

public class Dad extends Skinbag{

    public Dad(Set<? extends SmEvent> permissions) {
        super(permissions);
    }
}
