package smarthome.servises;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeviceLog {
    private static DeviceLog instance;

    public static DeviceLog getInstance() {
        if (instance == null) {
            instance = new DeviceLog();
        }
        return instance;
    }
    private List<Entry> list = new ArrayList<>();

    public void addEntry(String id, Object state){
        list.add(new Entry(id, state, LocalDateTime.now()));
    }

    public void report(){
        System.out.println(list.stream().map(it->it.toString()).collect(Collectors.joining("\n")));
    }

    private static class Entry{
        private String deviceId;
        private Object state;
        private LocalDateTime time;

        public Entry(String deviceId, Object state, LocalDateTime time) {
            this.deviceId = deviceId;
            this.state = state;
            this.time = time;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "deviceId='" + deviceId + '\'' +
                    ", state=" + state +
                    ", time=" + time +
                    '}';
        }
    }
}
