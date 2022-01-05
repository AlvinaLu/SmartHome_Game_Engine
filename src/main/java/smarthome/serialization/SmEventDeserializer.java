package smarthome.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import smarthome.statemachine.SmEvent;

import java.io.IOException;
import java.util.stream.Stream;

public class SmEventDeserializer extends StdDeserializer<SmEvent> {

    public SmEventDeserializer() {
        super(SmEvent.class);
    }

    @Override
    public SmEvent deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        try {
            JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
            Class<?> eventClass = Class.forName(jsonNode.get("eventClass").asText());
            Object event = Stream.of(eventClass.getEnumConstants()).filter(it -> it.toString().equals(jsonNode.get("eventName").asText())).findAny().get();
            return (SmEvent) event;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
