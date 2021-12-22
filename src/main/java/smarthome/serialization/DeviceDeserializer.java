package smarthome.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import smarthome.devices.Device;
import smarthome.statemachine.StateMachine;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.stream.Stream;

public class DeviceDeserializer extends StdDeserializer<Device> {
    public DeviceDeserializer() {
        super(Device.class);
    }

    @Override
    public Device deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        try {
            JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
            Class<?> deviceClass = Class.forName(jsonNode.get("deviceClass").asText());
            Device device = (Device) deviceClass.getDeclaredConstructor().newInstance();
            device.setId(jsonNode.get("id").asText());

            ParameterizedType deviceInterface = (ParameterizedType) deviceClass.getGenericInterfaces()[0];
            Class dataClass = (Class) deviceInterface.getActualTypeArguments()[0];
            Object deviceData = jsonParser.getCodec().treeToValue(jsonNode.get("deviceData"), dataClass);
            device.setData(deviceData);

            if (StateMachine.class.isAssignableFrom(deviceClass)) {
                StateMachine sm = (StateMachine) device;
                ParameterizedType superClass = (ParameterizedType) deviceClass.getGenericSuperclass();
                Class stateClass = (Class) superClass.getActualTypeArguments()[0];

                String stateString = jsonNode.get("currentState").asText();

                Object state = Stream.of(stateClass.getEnumConstants()).filter(it -> it.toString().equals(stateString)).findAny().get();
                sm.setCurrentState(state);

                ObjectMapper objectMapper = (ObjectMapper) deserializationContext.findInjectableValue("mapper", null, null);
                Map<String, Object> messageData = objectMapper.convertValue(jsonNode.get("messageData"), new TypeReference<Map<String, Object>>() {
                });
                sm.setMessageData(messageData);

            }

            return device;
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
