package smarthome.devices.coffee_machine;

import smarthome.statemachine.SmEvent;

public enum CoffeeMachineEvent implements SmEvent {
    TURN_ON,
    TURN_OFF,
    CHOOSING_A_VARIETY_OF_COFFEE
}
