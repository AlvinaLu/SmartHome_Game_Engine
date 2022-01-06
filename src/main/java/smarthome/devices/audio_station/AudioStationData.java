package smarthome.devices.audio_station;

public class AudioStationData {

    private int currentVolume = 10;
    private int electricityConsumption;
    private int electricityPerHour;
    private int resourceHours;
    private Audios audio;


    public int getCurrentVolume() {
        return currentVolume;
    }

    public void setCurrentVolume(int currentVolume) {
        this.currentVolume = currentVolume;
    }

    public int getElectricityConsumption() {
        return electricityConsumption;
    }

    public void setElectricityConsumption(int electricityConsumption) {
        this.electricityConsumption = electricityConsumption;
    }

    public int getElectricityPerHour() {
        return electricityPerHour;
    }

    public void setElectricityPerHour(int electricityPerHour) {
        this.electricityPerHour = electricityPerHour;
    }

    public int getResourceHours() {
        return resourceHours;
    }

    public void setResourceHours(int resourceHours) {
        this.resourceHours = resourceHours;
    }

    public Audios getAudio() {
        return audio;
    }

    public void setAudio(Audios audio) {
        this.audio = audio;
    }



}
