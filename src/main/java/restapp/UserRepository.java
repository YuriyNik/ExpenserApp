package restapp;

import model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    public User findByid(String id);
    public User findByUsername(String username);
    public List<User> findAll();
}
