package restapp;


import model.ExpenceSummary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
        logger.info("requested getReports for month="+month+";year="+year+";type="+type+";");
        if (!type.equals("null")) return expenceRepository.getReportsForYearAndType(year,type);
        if (month>0 && month<13) return expenceRepository.getReportsForMonth(year, month);
        return expenceRepository.getReportsForYear(year);
    }


}
