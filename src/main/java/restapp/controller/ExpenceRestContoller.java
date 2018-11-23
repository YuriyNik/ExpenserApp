package restapp.controller;

import model.Expence;
import model.ExpenceSummary;
import model.MigrateExpence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import restapp.repository.ExpenceRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ExpenceRestContoller {

    private static final Logger logger = LogManager.getLogger(ExpenceRestContoller.class);


    @Autowired
    private ExpenceRepository expenceRepository;

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
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

    @RequestMapping(method= RequestMethod.POST,value="/oldExpence")
    public List<Expence> postExpence(@RequestBody List <MigrateExpence> migrateExpences) {
        logger.info("ADMIN's method:oldExpence");
        UserDetails user =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info("Your user's roles=" +user.getAuthorities().toString());

        if (user.getAuthorities().toString().contains("ROLE_ADMIN")){

            logger.info("List size="+migrateExpences.size());
            List<Expence> expences = new ArrayList<>();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            for (MigrateExpence migrateExpence:migrateExpences) {
                Expence expence = new Expence(
                        migrateExpence.getAmount(),
                        migrateExpence.getType(),
                        LocalDateTime.parse(migrateExpence.getDate(), formatter),
                        LocalDateTime.parse(migrateExpence.getCreated(), formatter),
                        LocalDateTime.parse(migrateExpence.getModified(), formatter),
                        migrateExpence.getNotes(),
                        user.getUsername()
                );
                expence.setId(migrateExpence.getId());
                expenceRepository.save(expence);
                logger.info("migrated expence="+expence);
                expences.add(expence);
            }

            return expences;

        }

        logger.info("You user is not authorised for oldExpence call");
        return null;
    }

    @RequestMapping(method=RequestMethod.GET,value="/reports/{month}/{year}/{type}")
    public List<ExpenceSummary> getReports(@PathVariable int month, @PathVariable int year, @PathVariable String type ) {
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info("expenceForParticularMonth:UserDetails getUsername="+user.getUsername()+";Roles="+user.getAuthorities());

        logger.info("requested getReports for month="+month+";year="+year+";type="+type+";UserDetails getUsername="+user.getUsername()+";Roles="+user.getAuthorities());
        if (!type.equals("null")) return expenceRepository.getReportsForYearAndType(user.getUsername(),year,type);
        if (month>0 && month<13) return expenceRepository.getReportsForMonth(user.getUsername(),year, month);
        return expenceRepository.getReportsForYear(user.getUsername(),year);
    }

}
