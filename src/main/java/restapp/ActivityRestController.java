package restapp;

import model.Activity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
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

    }
