package airporter.controller;

import airporter.form.QueryForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.logging.Logger;

/**
 * Created by pavel on 31.3.17.
 */
@Controller
public class QueryController {

    final private static Logger log = Logger.getLogger(QueryController.class.toString());

    @GetMapping("/query")
    public String queryForm(Model model) {
        model.addAttribute("form", new QueryForm());
        return "query";
    }

    @PostMapping("/query")
    public String querySubmit(Model model, @ModelAttribute QueryForm form) {
        model.addAttribute("form", form);
        return "query";
    }
}
