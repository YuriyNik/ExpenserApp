package restapp;

import model.Expence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ExpenceRepository extends MongoRepository<Expence,String> {
    public Expence findByid(String id);
    @Query("{ 'owner':'?0','id':'?1'}")
    public Expence findByidForUser(String owner, String id);
    public List<Expence> findAll();
    @Query("{ 'owner':'?0'}")
    public List<Expence> findAllForUser(String owner);
    @Query("{$and:[{'owner':'?0'},{'$expr': { '$eq': [{ '$month': '$date' }, ?1 ] }},{'$expr': { '$eq': [{ '$year': '$date' }, ?2 ] }}]}")
    public List<Expence> findforMonth(String owner, int month, int year);
    @Query("{ 'owner':'?0'}")
    public List<Expence> findByType(String owner,String type);
    @Query("{$and:[{'owner':'?0'},{'type':'?1'},{'$expr': { '$eq': [{ '$month': '$date' }, ?2 ] }},{'$expr': { '$eq': [{ '$year': '$date' }, ?3 ] }}]}")
    public List<Expence> findByTypeForMonth(String owner,String type,int month,int year);
    @Query("{ 'owner':null}")
    public List<Expence> findByNullableOwner();

}
