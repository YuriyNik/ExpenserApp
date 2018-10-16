package restapp;

import model.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TodosRepository extends MongoRepository<Todo, String> {
    public Todo findByid(String id);
    @Query("{ 'owner':'?0','id':'?1'}")
    public Todo findByidForUser(String owner, String id);
    @Query("{ 'owner':'?0'}")
    public List<Todo> findAllForUser(String owner);
    public List<Todo> findAll();

}
