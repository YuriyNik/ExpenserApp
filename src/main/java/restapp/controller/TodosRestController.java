package restapp.controller;


import model.Todo;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import restapp.repository.TodosRepository;
import restapp.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class TodosRestController {
    private static final Logger logger = LogManager.getLogger(TodosRestController.class);

    @Autowired
    private TodosRepository todosRepository;

    @Autowired
    private UserRepository userRepository;

    //modify todos data
    @RequestMapping(method = RequestMethod.PUT, value = "/todo/{id}")
    public Todo updateTodo(@PathVariable String id, @RequestBody Todo todoFromClient) {
        logger.info("Update Todo/{id} TodoId="+id);
        Todo todoFromDb = todosRepository.findByid(id);
        if(todoFromClient.getDescription()!=null) {
            todoFromDb.setDescription(todoFromClient.getDescription());
        }
        if(todoFromClient.getLabel()!=null) {
            todoFromDb.setLabel(todoFromClient.getLabel());
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
        if(todo.getDate()!=null) {
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

    @RequestMapping(method = RequestMethod.GET, value = "/todoAll")
    public List<Todo> listAllTodo() {
        logger.info("method:listAllTodo");
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return todosRepository.findAllForUser(user.getUsername());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/todoAll/completed")
    public List<Todo> listAllTodoCompleted() {
        logger.info("method:listAllTodo_completed");
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return todosRepository.findAllCompleted(user.getUsername());
    }


    @RequestMapping(method = RequestMethod.GET, value = "/todo")
    public List<Todo> listAllNotDoneTodo() {
        logger.info("method:listAllNotDoneTodo");
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return todosRepository.findAllNotCompletedForUser(user.getUsername());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/todo/{id}")
    public Todo getTodobyId(@PathVariable String id) {
        logger.info("Todo/{id} TodoId="+id);
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return todosRepository.findByidForUser(user.getUsername(),id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/todoByLabel/{label}")
    public List<Todo> getTodobyLabel(@PathVariable String label) {
        logger.info("Todo/{label} Todolabel="+label);
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return todosRepository.findByLabelNotCompleted(user.getUsername(),label);
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

    @RequestMapping(method = RequestMethod.GET, value = "/todoLabels")
    public String[] getTodoLabels(){
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String[] todoLabels =  userRepository.findByUsername(user.getUsername()).getTodoLabels();
        return todoLabels;
    }

    @RequestMapping(method=RequestMethod.POST,value="/todoLabels")
    public String[] postTodoLabels(@RequestBody String[] todoLabels) {
        logger.info("postTodoLabels="+ Arrays.toString(todoLabels));
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userFromDB = userRepository.findByUsername(user.getUsername());
        userFromDB.setTodoLabels(todoLabels);
        userFromDB.setModified(LocalDateTime.now());
        userRepository.save(userFromDB);
        return todoLabels;
    }

}
