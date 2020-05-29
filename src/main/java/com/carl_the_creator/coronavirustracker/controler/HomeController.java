package com.carl_the_creator.coronavirustracker.controler;

import com.carl_the_creator.coronavirustracker.models.LocationGlobalDeaths;
import com.carl_the_creator.coronavirustracker.models.LocationStats;
import com.carl_the_creator.coronavirustracker.models.LocationUsDeaths;
import com.carl_the_creator.coronavirustracker.models.LocationUsStats;
import com.carl_the_creator.coronavirustracker.repo.UsRepository;
import com.carl_the_creator.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    UsRepository usrepo;

    @GetMapping("usa")
    public String usa(Model model ) {
        List<LocationUsStats> allUsStats = coronaVirusDataService.getUsStats();
        List<LocationUsDeaths> allUsDeaths = coronaVirusDataService.getUsDeathStats();
        int totalUsReportedCases = allUsStats.stream().mapToInt(stat -> stat.getLatestTotal()).sum();
        int totalUsReportedNewCases = allUsStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        int totalUsReportedDeaths = allUsDeaths.stream().mapToInt(stat -> stat.getUsDeaths()).sum();
        model.addAttribute("locationUsStats", allUsStats);
        model.addAttribute("locationUsDeaths", allUsDeaths);
        model.addAttribute("totalUsReportedCases", totalUsReportedCases);
        model.addAttribute("totalUsReportedNewCases", totalUsReportedNewCases);
        model.addAttribute("totalUsReportedDeaths", totalUsReportedDeaths);

        return "usa";
    }

    @GetMapping("global")
    public String global(Model model ) {
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        List<LocationGlobalDeaths> allGlobalDeaths = coronaVirusDataService.getGlobalDeathsStats();
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotal()).sum();
        int totalReportedNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        int totalReportedDeaths = allGlobalDeaths.stream().mapToInt(stat -> stat.getGlobalDeaths()).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalReportedNewCases", totalReportedNewCases);
        model.addAttribute("locationGlobalDeaths", allGlobalDeaths);
        model.addAttribute("totalReportedDeaths",totalReportedDeaths);
        return "global";
    }

    @GetMapping("home")
    public String findAll(@RequestParam String state,Model model){
        List<LocationUsStats> sRes = usrepo.findByName(state);
        model.addAttribute("state", sRes);

        return "home";
    }


}
