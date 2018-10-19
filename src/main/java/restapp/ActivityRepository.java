package restapp;

import model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ActivityRepository extends MongoRepository<Activity,String> {
    public Activity findByid(String id);
    public List<Activity> findAll();
    @Query("{ 'owner':'?0','id':'?1'}")
    public Activity findByidForUser(String owner, String id);
    @Query("{ 'owner':'?0'}")
    public List<Activity> findAllForUser(String owner);
    @Query("{ 'owner':'?0','type':'?1'}")
    public List<Activity> findByTypeForUser(String owner, String type);
}

