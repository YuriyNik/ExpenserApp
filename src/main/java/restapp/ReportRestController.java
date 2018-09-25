package restapp;


import model.ExpenceSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
public class ReportRestController {

    @Autowired
    private ExpenceRepository expenceRepository;

    @RequestMapping(method=RequestMethod.GET,value="/reports")
    public List<ExpenceSummary> getReports() {
        return expenceRepository.getReportsFor();
    }


}
