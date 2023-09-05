package model;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Arrays;

public class User {

    @Id
    private String id;
    private String username;
    private String password;
    private String[] roles;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String[] expenceTypes;
    private String[] currencyTypes;
    private String[] todoLabels;
    private String[] activityLabels;
    private String[] activityPlaces;

    public User(){

    }

    public User(String username, String password, String... roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setExpenceTypes(String[] expenceTypes) {
        this.expenceTypes = expenceTypes;
    }

    public String[] getExpenceTypes() {
        return expenceTypes;
    }

    public void setCurrencyTypes(String[] currencyTypes) {
        this.currencyTypes = currencyTypes;
    }

    public String[] getCurrencyTypes() {
        return currencyTypes;
    }

    public void setTodoLabels(String[] todoLabels) {
        this.todoLabels = todoLabels;
    }

    public String[] getTodoLabels() {
        return todoLabels;
    }

    public void setActivityLabels(String[] activityLabels) {
        this.activityLabels = activityLabels;
    }

    public String[] getActivityLabels() {
        return activityLabels;
    }

    public void setActivityPlaces(String[] activityPlaces) { this.activityPlaces = activityPlaces; }

    public String[] getActivityPlaces() { return activityPlaces; }

    @Override
    public String toString() {
        return String.format(
                "{id=%s, username='%s', roles='%s',expenceTypes='%s', currencyTypes='%s',activityLabels='%s',todoLabels='%s',activityPlaces='%s'}",
                id, username, Arrays.toString(roles),Arrays.toString(expenceTypes),Arrays.toString(currencyTypes),Arrays.toString(activityLabels),Arrays.toString(todoLabels),Arrays.toString(activityPlaces));
    }

}
