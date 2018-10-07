package model;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class Activity {

    @Id
    private String id;
    private LocalDateTime date;
    private String type;
    private int duration;
    private Double distance;
    private int pulseAve;
    private int pulseMax;
    private String location;
    private String notes;
    private String weather;
    private Double speedAve;
    private Double paceAve;

    private LocalDateTime created;
    private LocalDateTime modified;
    private String owner;

    @Override
    public String toString() {
        return String.format(
                "Activity[id=%s, date = '%s', type='%s', duration =%s , distance = %s, pulseAve = %s, pulseMax=%s,  location =%s, notes=%S , weather =%s, speedAve =%s, paceAve =%s, date='%s', created='%s', modified='%s', owner='%s']",
                id, date, type, date, duration,distance , pulseAve, pulseMax, location, notes, weather, speedAve, paceAve, created, modified, owner);
    }

    public Activity(){

    }

    public Activity(String id,
            LocalDateTime date,
            String type,
            int duration,
             Double distance,
             int pulseAve,
             int pulseMax,
             String location,
             String notes,
             String weather,
             Double speedAve,
             Double paceAve,

             LocalDateTime created,
             LocalDateTime modified,
             String owner)
    {
        this.id = id;
        this.date = date;
        this.duration = duration;
        this.type = type;
        this.distance = distance;
        this.pulseAve = pulseAve;
        this.pulseMax = pulseMax;
        this.location = location;
        this.notes = notes;
        this.weather= weather;
        this.speedAve = speedAve;
        this.paceAve = paceAve;

        this.created = created;
        this.modified = modified;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public int getPulseAve() {
        return pulseAve;
    }

    public void setPulseAve(int pulseAve) {
        this.pulseAve = pulseAve;
    }

    public int getPulseMax() {
        return pulseMax;
    }

    public void setPulseMax(int pulseMax) {
        this.pulseMax = pulseMax;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public Double getSpeedAve() {
        return speedAve;
    }

    public void setSpeedAve(Double speedAve) {
        this.speedAve = speedAve;
    }

    public Double getPaceAve() {
        return paceAve;
    }

    public void setPaceAve(Double paceAve) {
        this.paceAve = paceAve;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
