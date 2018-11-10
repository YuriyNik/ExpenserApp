package model;

public class ActivitySummary {

    private String type;
    private int month;
    private int year;
    private Double totalDistance;
    private int count;

    public ActivitySummary(){

    }

    public String getType() {
        return type;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public Double getTotalDistance() {
        return totalDistance;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return String.format(
                "{type=%s, month='%s', year='%s',totalAmount=%s, count='%s'}",
                type, month, year, totalDistance, count);
    }
}
