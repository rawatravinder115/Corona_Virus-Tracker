package ProjectCoronaVirusTracker.Corona_VirusTracker.Controllers;

import ProjectCoronaVirusTracker.Corona_VirusTracker.CoronaVirusTrackerApplication;
import ProjectCoronaVirusTracker.Corona_VirusTracker.Models.LocationStats;
import ProjectCoronaVirusTracker.Corona_VirusTracker.Services.CoronaVirusDataServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataServices coronaVirusDataServices;

    @GetMapping("/")
    public String home(Model model){
        List<LocationStats> allstats = coronaVirusDataServices.getAllStats();
        int totalReportedCases =allstats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        model.addAttribute("locationStats",allstats);
        model.addAttribute("totalReportedCases",totalReportedCases);

        return "home";
    }
}
