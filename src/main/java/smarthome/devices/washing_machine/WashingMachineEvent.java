package smarthome.devices.washing_machine;

import smarthome.statemachine.SmEvent;

import java.util.Map;

public enum WashingMachineEvent implements SmEvent {
    TURN_OFF, TURN_ON, ANTI_ALLERGY, QUICK_WASH,  WOOL, BROKEN,
}
