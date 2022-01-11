package smarthome.devices.washing_machine;

import smarthome.statemachine.SmEvent;

public enum WashingMachineEvent implements SmEvent {
    TURN_OFF, TURN_ON, ANTI_ALLERGY, QUICK_WASH,  WOOL, BROKEN,
}
