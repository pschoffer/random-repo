package airporter.controller;

import airporter.service.QueryService;
import airporter.service.dto.CountryRunway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Created by pavel on 31.3.17.
 */
@Controller
public class ReportController {

    private static final int NUMBER_OF_COUNTRIES = 10;
    private static final String TOP = "top";
    private static final String BOTTOM = "bottom";
    private static final String TOTAL = "total";

    @Autowired
    private QueryService queryService;

    @GetMapping("/report")
    public String queryForm(Model model) {
        final List<CountryRunway> countriesWithTheMostAirports =
                queryService.findCountriesHighestAirportCount(NUMBER_OF_COUNTRIES);
        model.addAttribute(TOP, countriesWithTheMostAirports);
        final List<CountryRunway> countriesWithTheLeastAirports =
                queryService.findCountriesLowestAirportCount(NUMBER_OF_COUNTRIES);
        model.addAttribute(BOTTOM, countriesWithTheLeastAirports);
        model.addAttribute(TOTAL, queryService.getTotalCountCountries());
        return "report";
    }
}
