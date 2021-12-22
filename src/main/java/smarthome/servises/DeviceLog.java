package smarthome.servises;

import smarthome.devices.ConsumingState;
import smarthome.devices.Resource;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DeviceLog {
    long HOURS = 1000*60*60;
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
        Map<String, List<Entry>> entryMap = list.stream().collect(Collectors.groupingBy(it -> it.deviceId));

        List<DeviceReport> result = entryMap.entrySet().stream().map(it -> report(it.getValue())).collect(Collectors.toList());

        System.out.println(result);
    }

    private DeviceReport report(List<DeviceLog.Entry> entries) {
        long timeScale = Scheduler.getInstance().getTimeScale();
        Entry start = entries.get(0);
        DeviceReport report = new DeviceReport(start.deviceId);

        for (int i = 1; i< entries.size(); i++) {
            Entry entry = entries.get(i);
            if (entry.state instanceof ConsumingState) {
                ConsumingState consumingState = (ConsumingState) entry.state;
                report = report
                        .add(new DeviceReport(consumingState.consumption())
                                .multiply(((double) Duration.between(start.time, entry.time).multipliedBy(timeScale).toMillis())/HOURS));
            }
            start = entry;
        }
        return report;
    }

    private static class DeviceReport {
        private String id;
        private Map<Resource,Double> consumption = new HashMap<>();

        public DeviceReport(String id) {
            this.id = id;
        }

        public DeviceReport(Map<Resource, Double> consumption) {
            this.consumption = consumption;
        }

        public DeviceReport() {
        }

        public DeviceReport add(DeviceReport deviceReport) {
            DeviceReport result = new DeviceReport();
            result.id = this.id;
            result.consumption = this.consumption;

            deviceReport.consumption.forEach((key, value)->{
                result.consumption.computeIfAbsent(key, k->0D);
                result.consumption.computeIfPresent(key, (k,v)->v+value);
            });
            return result;
        }

        public DeviceReport multiply(Double amount) {
            DeviceReport result = new DeviceReport();
            result.id = this.id;
            result.consumption = new HashMap<>(this.consumption);

            this.consumption.forEach((key, value)->{
                result.consumption.computeIfPresent(key, (k,v)->v*amount);
            });
            return result;
        }

        @Override
        public String toString() {
            return "DeviceReport{" +
                    "id='" + id + '\'' +
                    ", consumption=" + consumption +
                    '}';
        }
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
