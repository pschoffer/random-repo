package airport.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by pavel on 31.3.17.
 */
@Controller
public class QueryController {

    @RequestMapping("/query")
    public String query(Model model) {
        return "query";
    }
}
