package restapp;

import model.Expence;
import model.MigrateExpence;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MigrationRestController {

    @Autowired
    private ExpenceRepository expenceRepository;

    private static final Logger logger = LogManager.getLogger(MigrationRestController.class);

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



}
