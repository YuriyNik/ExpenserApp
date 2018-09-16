package restapp;

import model.Report;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReportRepository extends MongoRepository<Report, String> {
    public Report findByid(String id);
    public List<Report> findAll();
}
