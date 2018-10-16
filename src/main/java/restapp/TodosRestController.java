package restapp;


import model.Todo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class TodosRestController {
    private static final Logger logger = LogManager.getLogger(TodosRestController.class);

    @Autowired
    private TodosRepository todosRepository;

    //modify todos data
    @RequestMapping(method = RequestMethod.PUT, value = "/todo/{id}")
    public Todo updateTodo(@PathVariable String id, @RequestBody Todo todoFromClient) {
        logger.info("Update Todo/{id} TodoId="+id);
        Todo todoFromDb = todosRepository.findByid(id);
        if(todoFromClient.getDescription()!=null) {
            todoFromDb.setDescription(todoFromClient.getDescription());
        }
        if(todoFromClient.getDate()!=null) {
            todoFromDb.setDate(todoFromClient.getDate().plusHours(4));
        }

        if((todoFromClient.isCompleted()==true)||(todoFromClient.isCompleted()==false )) {
            todoFromDb.setCompleted(todoFromClient.isCompleted());
        }

        todoFromDb.setModified(LocalDateTime.now());

        todosRepository.save(todoFromDb);
        return todoFromDb;
    }

    @RequestMapping(method= RequestMethod.POST,value="/todo")
    public Todo postTodo(@RequestBody Todo todo) {
        logger.info("postTodo="+todo);
        if(todo.getDate()==null) {
            todo.setDate(LocalDateTime.now());
        } else {
            todo.setDate(todo.getDate().plusHours(4));
        }
        todo.setCreated(LocalDateTime.now());
        todo.setCompleted(false);
        UserDetails user =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        todo.setOwner(user.getUsername());
        todosRepository.save(todo);
        return todo;
    }

    //get all expenses for ADMIN only
    @RequestMapping(method = RequestMethod.GET, value = "/todo")
    public List<Todo> listAllTodo() {
        logger.info("ADMIN's method:listAllTodo");
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info("Your user's roles=" +user.getAuthorities().toString());
        if (user.getAuthorities().toString().contains("ROLE_ADMIN"))
            return todosRepository.findAll();
        logger.info("You user is not authorised for listAllTodo call");
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/todo/{id}")
    public Todo getTodobyId(@PathVariable String id) {
        logger.info("Todo/{id} TodoId="+id);
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return todosRepository.findByidForUser(user.getUsername(),id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/todo/{id}")
    public String deleteTodo(@PathVariable String id){
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Todo todo = todosRepository.findByidForUser(user.getUsername(),id);
        todosRepository.delete(todo);
        logger.info("Todo with id="+id+" deleted");
        return null;
    }

}
