package restapp.repository;

import model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ActivityRepository extends MongoRepository<Activity,String> , ActivityRepositoryCustom{
    public Activity findByid(String id);
    public List<Activity> findAll();
    @Query("{ 'owner':'?0','id':'?1'}")
    public Activity findByidForUser(String owner, String id);
    @Query("{ 'owner':'?0'}")
    public List<Activity> findAllForUser(String owner);
    @Query("{ 'owner':'?0','type':'?1'}")
    public List<Activity> findByTypeForUser(String owner, String type);
    @Query("{ $and:[{'owner':'?0'},{'$expr':{'$eq':[{ '$year':'$date' },?1 ]}}]}")
    public List<Activity> findforYear(String owner, int year);
    @Query("{ $and:[{'owner':'?0'},{'type':'?2'},{'$expr':{'$eq':[{ '$year':'$date' },?1 ]}}]}")
    public List<Activity> findforYearAndType(String owner, int year, String type);
    @Query("{$and:[{'owner':'?0'},{'$expr': { '$eq': [{ '$month': '$date' }, ?1 ] }},{'$expr': { '$eq': [{ '$year': '$date' }, ?2 ] }}]}")
    public List<Activity> findforYearAndMonth(String owner, int month, int year);
    @Query("{$and:[{'owner':'?0'},{'type':'?1'},{'$expr': { '$eq': [{ '$month': '$date' }, ?2 ] }},{'$expr': { '$eq': [{ '$year': '$date' }, ?3 ] }}]}")
    public List<Activity> findforYearAndMonthAndType(String owner, String type, int month, int year);
}

