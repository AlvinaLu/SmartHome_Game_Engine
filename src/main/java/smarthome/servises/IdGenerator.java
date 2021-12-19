package smarthome.servises;

import smarthome.Dispatcher;

import java.util.HashMap;
import java.util.Map;

public class IdGenerator {
    private static IdGenerator instance;

    public static IdGenerator getInstance() {
        if (instance == null) {
            instance = new IdGenerator();
        }
        return instance;
    }
    private Map<String, Integer> map = new HashMap<>();

    public String generateId(Class clazz){
        map.computeIfAbsent(clazz.getSimpleName(), it->0);
        Integer count = map.computeIfPresent(clazz.getSimpleName(), (ignored, c) -> ++c);
        return clazz.getSimpleName() +"#" + count;
    }
}
