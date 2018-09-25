package restapp;

import model.ExpenceSummary;

import java.util.List;

public interface ExpenceRepositoryCustom {
    public List<ExpenceSummary> getReportsFor();

}
