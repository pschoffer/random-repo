package airporter.controller;

import airporter.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by pavel on 31.3.17.
 */
@Controller
public class RunwaysController {

    private static final int NUMBER_OF_RUNWAYS = 10;
    private static final String IDENTS_KEY = "idents";

    @Autowired
    private QueryService queryService;

    @GetMapping("/runways")
    public String queryForm(Model model) {
        model.addAttribute(IDENTS_KEY, queryService.findCommonRunways(NUMBER_OF_RUNWAYS));
        return "runways";
    }
}
