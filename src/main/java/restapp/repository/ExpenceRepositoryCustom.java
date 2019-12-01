package restapp.repository;

import model.ExpenceSummary;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface ExpenceRepositoryCustom {
    public List<ExpenceSummary> getReportsForYearAndType(ArrayList<String> owners, int year, String type);
    public List<ExpenceSummary> getReportsForYear(ArrayList<String> owners, int year);
//    public List<ExpenceSummary> getReportsForMonthAndType(int month, int year, String type);
    public List<ExpenceSummary> getReportsForMonth(ArrayList<String> owners,int year, int month);
 //   public List<ExpenceSummary> getReportsFromToDate(LocalDateTime dateFrom, LocalDateTime dateTo);
//    public List<ExpenceSummary> getReportsFromToDateAndType(LocalDateTime dateFrom, LocalDateTime dateTo, String type);
}
