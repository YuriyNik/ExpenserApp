package model;

public class ExpenceSummary {
    private String type;
    private int month;
    private int year;
    private Double totalAmount;
    private int count;

    public ExpenceSummary(){}

    public ExpenceSummary( String type, int month, int year, Double totalAmount, int count){
        this.type=type;
        this.month=month;
        this.year=year;
        this.totalAmount=totalAmount;
        this.count=count;
    }


    public void setType(String type) {
        this.type = type;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setCount(int count) {
        this.count = count;
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

    public Double getTotalAmount() {
        return totalAmount;
    }

    public int getCount() {
        return count;
    }


    @Override
    public String toString() {
        return String.format(
                "{type=%s, month='%s', year='%s',totalAmount=%s, count='%s'}",
                type, month, year, totalAmount, count);
    }
}
