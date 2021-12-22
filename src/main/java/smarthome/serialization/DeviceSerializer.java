package smarthome.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import smarthome.devices.Device;
import smarthome.statemachine.StateMachine;

import java.io.IOException;

public class DeviceSerializer extends StdSerializer<Device> {
    public DeviceSerializer() {
        super(Device.class);
    }

    @Override
    public void serialize(Device device, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("deviceClass", device.getClass().getName());
        jsonGenerator.writeObjectField("deviceData", device.getData());
        jsonGenerator.writeStringField("id", device.getId());
        if(StateMachine.class.isAssignableFrom(device.getClass())){
            jsonGenerator.writeObjectField("messageData", ((StateMachine<?, ?>) device).getMessageData());
            jsonGenerator.writeObjectField("currentState", ((StateMachine<?, ?>) device).getCurrentState());
        }
        jsonGenerator.writeEndObject();
    }
}
