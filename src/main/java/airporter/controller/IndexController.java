package airporter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by pavel on 31.3.17.
 */
@Controller
public class IndexController {


    @RequestMapping("/")
    public String query() {
        return "index";
    }
}
