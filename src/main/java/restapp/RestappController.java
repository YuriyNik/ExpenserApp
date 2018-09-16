package restapp;

import model.Expence;
import model.Report;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class RestappController {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ExpenceRepository expenceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;

    private static final Logger logger = LogManager.getLogger(RestappController.class);


    @RequestMapping(method=RequestMethod.POST,value="/user")
   public User postUser(@RequestBody User user) {
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
///for migration from v1 to v1.2 only - to be removed later
    @RequestMapping(method = RequestMethod.GET, value = "/upgrade/setOwner")
    public List<Expence> setOwner(){
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
        //TODO fix it to work for multiple roles
        if (user.getAuthorities().toString().equals("[ROLE_ADMIN]"))
            return expenceRepository.findAll();
        logger.info("You user is not authorised for listAllExpence call");
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/expenceForThisMonth/{month}")
    public List<Expence> expenceForParticularMonth(@PathVariable int month) {
        if (month<1 || month>12) month = LocalDateTime.now().getMonthValue();
        logger.info("expenceForParticularMonth: expenceForThisMonth for month="+month);
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info("expenceForParticularMonth:UserDetails getUsername="+user.getUsername()+";Roles="+user.getAuthorities());

        return expenceRepository.findforMonth(user.getUsername(),month);
    }


    @RequestMapping(method = RequestMethod.GET, value="/logoutPage")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        logger.info("logout procedure started");
        if (auth != null){
            logger.info("logout procedure");
            new SecurityContextLogoutHandler().logout(request, response, auth);
        } else {
            logger.info("Authentication is null");

        }
        return "redirect:/";
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
    @RequestMapping(method = RequestMethod.GET, value = "/expenceByType/{type}/{month}")
    public List<Expence> expenceByTypeForMonth(@PathVariable String type,@PathVariable int month) {
        if (month<1 || month>12) month = LocalDateTime.now().getMonthValue();
        logger.info("expenceByTypeForMonth for month="+month);
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return expenceRepository.findByTypeForMonth(user.getUsername(),type,month);
    }

    //get all expenceies for the Report by ReportId
    @RequestMapping(method = RequestMethod.GET, value = "/expence/byReportid/{id}")
    public List<Expence> listExpenceByReport(@PathVariable String id) {
        logger.info("Reportid="+id);
        return expenceRepository.findByReportid(id);
    }

//modify expence's data
    @RequestMapping(method = RequestMethod.PUT, value = "/expence/{id}")
    public String updateExpence(@PathVariable String id, @RequestBody Expence expenceFromClient) {
        logger.info("Update expence/{id} expenceId="+id);
        Expence expenceFromDb = expenceRepository.findByid(id);
        if(expenceFromClient.getAmount()!=null) {
            expenceFromDb.setAmount(expenceFromClient.getAmount());
        }
        if(expenceFromClient.getType()!=null) {
            expenceFromDb.setType(expenceFromClient.getType());
        }
        if(expenceFromClient.getDate()!=null) {
            expenceFromDb.setDate(expenceFromClient.getDate());
        }
        if(expenceFromClient.getCreated()!=null) {
            expenceFromDb.setCreated(expenceFromClient.getCreated());
        }

        expenceFromDb.setModified(LocalDateTime.now());

        if(expenceFromClient.getReportid()!=null) {
            expenceFromDb.setReportid(expenceFromClient.getReportid());
        }
            expenceRepository.save(expenceFromDb);
            return expenceFromDb.toString();
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

//Routes for reports
    @RequestMapping(method=RequestMethod.POST,value="/report")
    public Report postReport(@RequestBody Report report) {
        logger.info("postReport="+report);
        report.setCreated(LocalDateTime.now());
        reportRepository.save(report);
        return report;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/report/{id}")
    public String updateReport(@PathVariable String id, @RequestBody Report reportFromClient) {
        logger.info("Update report/{id} reportId="+id);
        Report reportFromDb = reportRepository.findByid(id);
        if(reportFromClient.getName()==null) {
            logger.info("Report name is empty");
            return "Nothing to Update";
        } else {
            reportFromDb.setName(reportFromClient.getName());
            reportRepository.save(reportFromDb);
            logger.info("report/{id} reportId="+id+" is updated with name "+reportFromClient.getName());
            return reportFromDb.toString();
        }

    }

    @RequestMapping(method=RequestMethod.GET,value="/report")
    public List<Report> getAllReports() {
        logger.info("getAllReports");
        return reportRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/report/{id}")
    public Report reportbyId(@PathVariable String id) {
        logger.info("report/{id} reportId="+id);
        return reportRepository.findByid(id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/report/{id}")
    public String deleteReport(@PathVariable String id){
        Report report = reportRepository.findByid(id);
        reportRepository.delete(report);
        logger.info("Report "+id+" is deleted");
        return null;
    }

}
