package restapp.controller;

import model.Expence;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import restapp.service.UserDetailsServiceImp;
import restapp.repository.ExpenceRepository;
import restapp.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class RestappController {

    @Autowired
    private ExpenceRepository expenceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;

    private static final Logger logger = LogManager.getLogger(RestappController.class);


    @RequestMapping(method=RequestMethod.POST,value="/user")
   public User postNewUser(@RequestBody User user) {
       User userFromDb=userRepository.findByUsername(user.getUsername());
       String[] roles=user.getRoles();
       if ((Arrays.asList(roles).contains("ADMIN"))&&(!user.getUsername().equals("admin"))){
           logger.info("Create user:ADMIN role can't be assigned to your user..");
           return null;
       }
       if (userFromDb==null) {
           user.setCreated(LocalDateTime.now());
           userDetailsServiceImp.createUser(user);
           logger.info("Created user user=" + user);
           return user;
       }
       logger.info("Create user: User "+user.getUsername()+" is already created..");
       return null;
    }

    @RequestMapping(method=RequestMethod.POST,value="/userProfile")
    public User postUserProfile(@RequestBody User user) {
        UserDetails userAuthorised =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userFromDb=userRepository.findByUsername(userAuthorised.getUsername());
        logger.info("postUserProfile for userFromDb=" + userFromDb);
        if ((userFromDb!=null )&&(!user.getPassword().isEmpty())) {
            userFromDb.setModified(LocalDateTime.now());
            userFromDb.setPassword(user.getPassword());
            userDetailsServiceImp.createUser(userFromDb);
            logger.info("postUserProfile Updated user=" + userFromDb);
            logger.info("postUserProfile load user to context="+userDetailsServiceImp.loadUserByUsername(userFromDb.getUsername()));
            return userFromDb;
        }
        logger.info("postUserProfile user is not found at DB or empty password provided..");
        return null;
    }
///for migration from v1 to v1.2 only - to be removed later
    @RequestMapping(method = RequestMethod.GET, value = "/upgrade/setOwner")
    public List<Expence> setOwner(){
        logger.info("SetOwner triggered");
        List<Expence> expencesSetOwner =  expenceRepository.findByNullableOwner();
        for (Expence expence:expencesSetOwner){
            expence.setOwner("admin");
            expence.setModified(LocalDateTime.now());
            expenceRepository.save(expence);
            logger.info("SetOwner:[Updated]expence="+expence);
        }
        return expencesSetOwner;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/expenceTypes")
    public String[] getExpenceTypes(){
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String[] expenceTypes =  userRepository.findByUsername(user.getUsername()).getExpenceTypes();
        return expenceTypes;
    }

    @RequestMapping(method=RequestMethod.POST,value="/expenceTypes")
    public String[] postexpence(@RequestBody String[] expenceTypes) {
        logger.info("POST expenceTypes="+Arrays.toString(expenceTypes));
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userFromDB = userRepository.findByUsername(user.getUsername());
        userFromDB.setExpenceTypes(expenceTypes);
        userFromDB.setModified(LocalDateTime.now());
        userRepository.save(userFromDB);
        return expenceTypes;
    }

    @RequestMapping(method=RequestMethod.POST,value="/expence")
    public Expence postexpence(@RequestBody Expence expence) {
        logger.info("postExpence="+expence);
        if(expence.getDate()==null) {
            expence.setDate(LocalDateTime.now());
        } else {
            expence.setDate(expence.getDate().plusHours(4));
        }
        expence.setCreated(LocalDateTime.now());
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        expence.setOwner(user.getUsername());
        expenceRepository.save(expence);
        return expence;
    }

    //get all expenses for ADMIN only
    @RequestMapping(method = RequestMethod.GET, value = "/expence")
    public List<Expence> listAllExpence() {
        logger.info("ADMIN's method:listAllExpence");
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info("Your user's roles=" +user.getAuthorities().toString());
        if (user.getAuthorities().toString().contains("ROLE_ADMIN"))
            return expenceRepository.findAll();
        logger.info("You user is not authorised for listAllExpence call");
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/expenceForThisMonth/{month}/{year}")
    public List<Expence> expenceForParticularMonth(@PathVariable int month,@PathVariable int year) {
        if (month<1 || month>12) month = LocalDateTime.now().getMonthValue();
        if (year<2010 || year>2030) year = LocalDateTime.now().getYear();
        logger.info("expenceForParticularMonth: expenceForThisMonth for month="+month+";year="+year);
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info("expenceForParticularMonth:UserDetails getUsername="+user.getUsername()+";Roles="+user.getAuthorities());

        return expenceRepository.findforMonth(user.getUsername(),month,year);
    }


    @RequestMapping(method = RequestMethod.GET, value="/login")
    public String getLogin () {
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/userDetails")
    public UserDetails getUserDeatils(){
        UserDetails userDetails =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info("userDetails: getUsername="+userDetails.getUsername());
        logger.info("userDetails: getAuthorities="+userDetails.getAuthorities());
        return userDetails;
    }



    // get all expenceies for partucular type for this month
    @RequestMapping(method = RequestMethod.GET, value = "/expenceByType/{type}/{month}/{year}")
    public List<Expence> expenceByTypeForMonth(@PathVariable String type,@PathVariable int month,@PathVariable int year) {
        if (month<1 || month>12) month = LocalDateTime.now().getMonthValue();
        if (year<2010 || year>2030) year = LocalDateTime.now().getYear();

        logger.info("expenceByTypeForMonth for type="+type+" for month="+month+";year="+year);
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return expenceRepository.findByTypeForMonth(user.getUsername(),type,month,year);
    }


//modify expence's data
    @RequestMapping(method = RequestMethod.PUT, value = "/expence/{id}")
    public Expence updateExpence(@PathVariable String id, @RequestBody Expence expenceFromClient) {
        logger.info("Update expence/{id} expenceId="+id);
        Expence expenceFromDb = expenceRepository.findByid(id);
        if(expenceFromClient.getAmount()!=null) {
            expenceFromDb.setAmount(expenceFromClient.getAmount());
        }
        if(expenceFromClient.getType()!=null) {
            expenceFromDb.setType(expenceFromClient.getType());
        }
        if(expenceFromClient.getDate()!=null) {
            expenceFromDb.setDate(expenceFromClient.getDate().plusHours(4));
        }

        if(expenceFromClient.getNotes()!=null) {
            expenceFromDb.setNotes(expenceFromClient.getNotes());
        }

        expenceFromDb.setModified(LocalDateTime.now());

            expenceRepository.save(expenceFromDb);
            return expenceFromDb;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/expence/{id}")
    public Expence expencebyId(@PathVariable String id) {
        logger.info("expence/{id} ExpenceId="+id);
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return expenceRepository.findByidForUser(user.getUsername(),id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/expence/{id}")
    public String deleteExpence(@PathVariable String id){
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Expence expence = expenceRepository.findByidForUser(user.getUsername(),id);
        expenceRepository.delete(expence);
        logger.info("Expence with id="+id+" deleted");
        return null;
    }



}
