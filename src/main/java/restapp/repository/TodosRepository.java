package restapp.repository;

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
    @Query("{ 'owner':'?0','label':'?1','completed':false}")
    public List<Todo> findByLabelNotCompleted(String owner, String label);
    @Query("{ 'owner':'?0','completed':true}")
    public List<Todo> findAllCompleted(String owner);
    @Query("{ 'owner':'?0','completed':false}")
    public List<Todo> findAllNotCompletedForUser(String owner);
    public List<Todo> findAll();

}
