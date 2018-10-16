package model;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Todo {

    @Id
    private String id;
    private String description;
    private String label;
    private LocalDateTime date;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String owner;
    private boolean completed;

    public Todo(){

    }

    public Todo(String description, String label, LocalDateTime date, LocalDateTime created, LocalDateTime modified, String owner, boolean completed){
        this.description=description;
        this.label=label;
        this.date=date;
        this.created=created;
        this.modified=modified;
        this.owner=owner;
        this.completed=completed;

    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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



    @Override
    public String toString() {
        return String.format(
                "{id=%s, description='%s',label='%s', date='%s',created='%s',modified='%s', owner='%s',completed='%s' }",
                id, description,label,date,created,modified,owner,completed );
    }
}
