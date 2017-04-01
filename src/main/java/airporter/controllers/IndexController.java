package airporter.controllers;

import airporter.model.Country;
import airporter.model.CountryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

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
