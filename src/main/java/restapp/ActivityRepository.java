package restapp;

import model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ActivityRepository extends MongoRepository<Activity,String> {
    public Activity findByid(String id);
    public List<Activity> findAll();
}

