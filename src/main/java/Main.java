import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import smarthome.Dispatcher;
import smarthome.devices.Device;
import smarthome.devices.DeviceFactory;
import smarthome.devices.refrigerator.Refrigerator;
import smarthome.devices.refrigerator.RefrigeratorEvent;
import smarthome.location.Location;
import smarthome.serialization.DeviceDeserializer;
import smarthome.serialization.DeviceSerializer;
import smarthome.servises.DeviceLog;
import smarthome.servises.Scheduler;
import smarthome.statemachine.Message;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Device.class, new DeviceSerializer());
        simpleModule.addDeserializer(Device.class, new DeviceDeserializer());
        objectMapper.registerModule(simpleModule);
        objectMapper.setInjectableValues(new InjectableValues.Std(Map.of("mapper", objectMapper)));

        DeviceFactory df = DeviceFactory.getInstance();
        Scheduler.getInstance().setTimeScale(1000);

        Scheduler.getInstance().schedule(()-> DeviceLog.getInstance().report(),100, TimeUnit.MINUTES);

        Location house = new Location("House");
        Refrigerator refrigerator = df.createDevice(Refrigerator.class);
        house.setDevices(Set.of(refrigerator));


        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("test.json"), house);
        house = objectMapper.readValue(new File("test.json"), Location.class);

        Dispatcher dispatcher = Dispatcher.getInstance();
        dispatcher.init(Set.of(house));


        dispatcher.sendMessage(Message.toLocation(RefrigeratorEvent.TURN_ON, "House"));
        dispatcher.sendMessage(Message.toDevice(RefrigeratorEvent.CHANGE_TEMPERATURE, "Refrigerator#1", Map.of("target", 4)));





    }
}
