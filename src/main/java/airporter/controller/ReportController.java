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
public class ReportController {

    @Autowired
    private QueryService queryService;

    @GetMapping("/report")
    public String queryForm(Model model) {
        return "report";
    }
}
