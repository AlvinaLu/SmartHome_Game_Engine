package smarthome.servises;

import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import smarthome.devices.Device;
import smarthome.location.Location;
import smarthome.serialization.DeviceDeserializer;
import smarthome.serialization.DeviceSerializer;

import java.io.File;
import java.util.Map;

public class LocationConfiguration {
    public static final String LOCATION = "location.json";
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
        objectMapper.registerModule(simpleModule);
        objectMapper.setInjectableValues(new InjectableValues.Std(Map.of("mapper", objectMapper)));

    }

    public void setLocation(Location location){
        this.location = location;

    }
    public void load(){
        try {
            location = objectMapper.readValue(new File(LOCATION), Location.class);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void save(){
        try {
            this.objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(LOCATION), location);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public Location getLocation() {
        return location;
    }

    public boolean isFileExist() {
        return new File(LOCATION).exists();
    }
}
