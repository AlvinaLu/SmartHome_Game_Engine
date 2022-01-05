package smarthome.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import smarthome.statemachine.SmEvent;

import java.io.IOException;

public class SmEventSerializer extends StdSerializer<SmEvent> {

    public SmEventSerializer() {
        super(SmEvent.class);
    }

    @Override
    public void serialize(SmEvent smEvent, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("eventClass", smEvent.getClass().getName());
        jsonGenerator.writeStringField("eventName", smEvent.toString());
        jsonGenerator.writeEndObject();
    }
}
