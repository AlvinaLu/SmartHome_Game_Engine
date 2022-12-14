package smarthome.servises;

import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import smarthome.SmartHome;
import smarthome.devices.Device;
import smarthome.location.Location;
import smarthome.serialization.DeviceDeserializer;
import smarthome.serialization.DeviceSerializer;
import smarthome.serialization.SmEventDeserializer;
import smarthome.serialization.SmEventSerializer;
import smarthome.statemachine.SmEvent;

import java.io.File;
import java.util.Map;
/**
 * Stores location configuration
 */
public class LocationConfiguration {
    public static final String LOCATION = SmartHome.name;
    private static LocationConfiguration instance;

    public static LocationConfiguration getInstance() {
        if (instance == null) {
            instance = new LocationConfiguration();
        }
        return instance;
    }

    private ObjectMapper objectMapper = new ObjectMapper();
    Location location;

    private LocationConfiguration(){
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Device.class, new DeviceSerializer());
        simpleModule.addDeserializer(Device.class, new DeviceDeserializer());
        simpleModule.addSerializer(SmEvent.class, new SmEventSerializer());
        simpleModule.addDeserializer(SmEvent.class, new SmEventDeserializer());
        objectMapper.registerModule(simpleModule);
        objectMapper.setInjectableValues(new InjectableValues.Std(Map.of("mapper", objectMapper)));
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

    }

    public void setLocation(Location location){
        this.location = location;

    }
    /**
     * Load locations to json file
     */
    public void load(){
        try {
            location = objectMapper.readValue(new File(LOCATION+".json"), Location.class);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    /**
     * Save locations to json file
     */
    public synchronized void save(){
        try {
            this.objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(LOCATION+".json"), location);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public Location getLocation() {
        return location;
    }

    public boolean isFileExist() {
        return new File(LOCATION+".json").exists();
    }

}
