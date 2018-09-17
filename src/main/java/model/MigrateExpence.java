package model;

import org.springframework.data.annotation.Id;

public class MigrateExpence {


    @Id
    private String id;

    private Double amount;
    private String type;
    private String notes;
    private String date;
    private String created;
    private String modified;
    private String reportid;
    private String owner;

    @Override
    public String toString() {
        return String.format(
                "Expence[id=%s, amount='%s', category='%s', date='%s', created='%s', modified='%s',notes='%s', reportId='%s', owner='%s']",
                id, amount, type, date, created, modified, notes,reportid,owner);
    }

    public MigrateExpence(){

    }

    public MigrateExpence(Double amount, String type, String date, String created, String modified,String notes ,String report_id, String owner) {
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.created = created;
        this.modified = modified;
        this.notes = notes;
        this.reportid = report_id;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String category) {
        this.type = category;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getReportid() {
        return reportid;
    }

    public void setReportid(String reportid) {
        this.reportid = reportid;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
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
