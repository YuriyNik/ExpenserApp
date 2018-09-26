package restapp;

import model.ExpenceSummary;

import java.time.LocalDateTime;
import java.util.List;

public interface ExpenceRepositoryCustom {
    public List<ExpenceSummary> getReportsForYearAndType(String owner, int year, String type);
    public List<ExpenceSummary> getReportsForYear(String owner,int year);
//    public List<ExpenceSummary> getReportsForMonthAndType(int month, int year, String type);
    public List<ExpenceSummary> getReportsForMonth(String owner,int year, int month);
 //   public List<ExpenceSummary> getReportsFromToDate(LocalDateTime dateFrom, LocalDateTime dateTo);
//    public List<ExpenceSummary> getReportsFromToDateAndType(LocalDateTime dateFrom, LocalDateTime dateTo, String type);
}
