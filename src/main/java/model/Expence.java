package model;

import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

public class Expence {

    @Id
    private String id;

    private Double amount;
    private String currency;
    private String type;
    private String notes;
    private LocalDateTime date;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String owner;

    @Override
    public String toString() {
        return String.format(
                "Expence[id=%s, amount='%s',currency='%s' ,category='%s', date='%s', created='%s', modified='%s',notes='%s', owner='%s']",
                id, amount, currency, type, date, created, modified, notes,owner);
    }

    public Expence(){

    }

    public Expence(Double amount, String currency, String type, LocalDateTime date, LocalDateTime created, LocalDateTime modified,String notes , String owner) {
        this.amount = amount;
        this.currency = currency;
        this.type = type;
        this.date = date;
        this.created = created;
        this.modified = modified;
        this.notes = notes;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency(){ return  currency; }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String category) {
        this.type = category;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
