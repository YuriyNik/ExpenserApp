package restapp;

import model.Activity;
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
public class ActivityRestController {
    
    @Autowired
    private ActivityRepository activityRepository;
    
    private static final Logger logger = LogManager.getLogger(ActivityRestController.class);

    @RequestMapping(method= RequestMethod.POST,value="/activity")
    public Activity postActivity(@RequestBody Activity  activity) {
        logger.info("POST activity call");

        if (activity.getDate() == null) {
            activity.setDate(LocalDateTime.now());
        } else {
            activity.setDate(activity.getDate().plusHours(4));
        }
        activity.setCreated(LocalDateTime.now());
        UserDetails user =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        activity.setOwner(user.getUsername());
        activityRepository.save(activity);
        return activity;
    }

    //get all expenses for ADMIN only
    @RequestMapping(method = RequestMethod.GET, value = "/activityAll")
    public List<Activity> getAllActivity() {
        logger.info("ADMIN's method:listAllActivity");
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info("Your user's roles=" +user.getAuthorities().toString());
        if (user.getAuthorities().toString().contains("ROLE_ADMIN"))
            return activityRepository.findAll();
        logger.info("You user is not authorised for listAllActivity call");
        return null;
    }

    //get all expenses for user
    @RequestMapping(method = RequestMethod.GET, value = "/activity")
    public List<Activity> getAllActivityForUser() {
        logger.info("getAllActivityForUser");
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return activityRepository.findAllForUser(user.getUsername());
    }

    //get all expenses for user by type
    @RequestMapping(method = RequestMethod.GET, value = "/activity/{type}")
    public List<Activity> getAllActivityForUserByType(@PathVariable String type) {
        logger.info("getAllActivityForUserByType");
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return activityRepository.findByTypeForUser(user.getUsername(),type);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/activity/{id}")
    public Activity updateactivity(@PathVariable String id, @RequestBody Activity activityFromClient) {
        logger.info("Update activity/{id} activityId="+id);
        logger.info("Activity to str:"+activityFromClient.toString());
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Activity activityFromDb = activityRepository.findByidForUser(user.getUsername(),id);
       /* LocalDateTime date,
        String type,
        int duration,
        Double distance,
        int pulseAve,
        int pulseMax,
        String location,
        String notes,
        String weather,
        Double speedAve,
        Double paceAve,*/

        if(activityFromClient.getDate()!=null) {
            activityFromDb.setDate(activityFromClient.getDate().plusHours(4));
        }


        if(activityFromClient.getDuration()!=0) {
            activityFromDb.setDuration(activityFromClient.getDuration());
        }

///

        if(activityFromClient.getType()!=null) {
            activityFromDb.setType(activityFromClient.getType());
        }

        if(activityFromClient.getDistance()!=null) {
            activityFromDb.setDistance(activityFromClient.getDistance());
        }

        if(activityFromClient.getLocation()!=null){
          activityFromClient.setLocation(activityFromClient.getLocation());
        }

        if(activityFromClient.getNotes()!=null){
            activityFromClient.setNotes(activityFromClient.getNotes());
        }

        if (activityFromClient.getWeather()!=null){
            activityFromClient.setWeather(activityFromClient.getWeather());
        }

        if (activityFromClient.getSpeedAve()!=null) {
            activityFromClient.setSpeedAve(activityFromClient.getSpeedAve());
        }

        if (activityFromClient.getPaceAve()!=null) {
            activityFromClient.setPaceAve(activityFromClient.getSpeedAve());
        }

        activityFromDb.setModified(LocalDateTime.now());

        activityRepository.save(activityFromDb);
        return activityFromDb;
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/activity/{id}")
    public String deleteActivity(@PathVariable String id){
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Activity activity = activityRepository.findByidForUser(user.getUsername(),id);
        activityRepository.delete(activity);
        logger.info("Activity with id="+id+" deleted");
        return null;
    }


}
