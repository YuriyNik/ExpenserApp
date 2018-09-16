package model;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class Report {

    @Id
    private String id;
    private String name;
    private LocalDateTime created;

    public Report(){}

    public Report( String name, LocalDateTime created ){
        this.name=name;
        this.created=created;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public LocalDateTime getCreated(){return created;}

    public void setName(String name) {
        this.name = name;
    }
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return String.format(
                "{id=%s, name='%s', created='%s'}",
                id, name, created);
    }
}
