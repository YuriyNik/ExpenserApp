package model;

public class MigrateActivity {

    private String date;
    private String type;
    private String duration;
    private int durationHours;
    private int durationMins;
    private int durationSecs;
    private String distance;
    private int pulseAve;
    private int pulseMax;
    private String location;
    private String notes;
    private String weather;

    public MigrateActivity(){ }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public int getDurationHours() {
        return durationHours;
    }

    public int getDurationMins() {
        return durationMins;
    }

    public int getDurationSecs() {
        return durationSecs;
    }

    public String getDistance() {
        return distance;
    }

    public int getPulseAve() {
        return pulseAve;
    }

    public int getPulseMax() {
        return pulseMax;
    }

    public String getLocation() {
        return location;
    }

    public String getNotes() {
        return notes;
    }

    public String getWeather() {
        return weather;
    }
}
