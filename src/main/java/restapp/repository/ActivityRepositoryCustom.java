package restapp.repository;

import model.ActivitySummary;

import java.util.List;

public interface ActivityRepositoryCustom {

    public List<ActivitySummary> getActivitySummaryForYear(String owner, int year);
    public List<ActivitySummary> getActivitySummaryForYearByMonth(String owner, int year);
    public List<ActivitySummary> getActivitySummaryAll(String owner);


}
