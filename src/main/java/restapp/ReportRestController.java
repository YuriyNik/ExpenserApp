package restapp;


import model.ExpenceSummary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ReportRestController {

    private static final Logger logger = LogManager.getLogger(ReportRestController.class);

    @Autowired
    private ExpenceRepository expenceRepository;

    @RequestMapping(method=RequestMethod.GET,value="/reports/{month}/{year}/{type}")
    public List<ExpenceSummary> getReports(@PathVariable int month,@PathVariable int year,@PathVariable String type ) {
        UserDetails user =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info("expenceForParticularMonth:UserDetails getUsername="+user.getUsername()+";Roles="+user.getAuthorities());

        logger.info("requested getReports for month="+month+";year="+year+";type="+type+";UserDetails getUsername="+user.getUsername()+";Roles="+user.getAuthorities());
        if (!type.equals("null")) return expenceRepository.getReportsForYearAndType(user.getUsername(),year,type);
        if (month>0 && month<13) return expenceRepository.getReportsForMonth(user.getUsername(),year, month);
        return expenceRepository.getReportsForYear(user.getUsername(),year);
    }


}
