package restapp.controller;

import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import restapp.service.UserDetailsServiceImp;
import restapp.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Arrays;

@RestController
@CrossOrigin(origins = "*")
public class RestappController {

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


    @RequestMapping(method = RequestMethod.GET, value = "/expenceTypes")
    public String[] getExpenceTypes(){
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String[] expenceTypes =  userRepository.findByUsername(user.getUsername()).getExpenceTypes();
        return expenceTypes;
    }

    @RequestMapping(method=RequestMethod.POST,value="/expenceTypes")
    public String[] postExpenceTypes(@RequestBody String[] expenceTypes) {
        logger.info("POST expenceTypes="+Arrays.toString(expenceTypes));
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userFromDB = userRepository.findByUsername(user.getUsername());
        userFromDB.setExpenceTypes(expenceTypes);
        userFromDB.setModified(LocalDateTime.now());
        userRepository.save(userFromDB);
        return expenceTypes;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/familyMembers")
    public String[] getFamilyMembers(){
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String[] familyMembers =  userRepository.findByUsername(user.getUsername()).getFamilyMembers();
        return familyMembers;
    }

    @RequestMapping(method=RequestMethod.POST,value="/familyMembers")
    public String[] postFamilyMembers(@RequestBody String[] familyMembers) {
        logger.info("POST familyMembers="+Arrays.toString(familyMembers));
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userFromDB = userRepository.findByUsername(user.getUsername());
        userFromDB.setFamilyMembers(familyMembers);
        userFromDB.setModified(LocalDateTime.now());
        userRepository.save(userFromDB);
        return familyMembers;
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




}
