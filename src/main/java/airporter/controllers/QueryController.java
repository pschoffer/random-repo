package airporter.controllers;

import airporter.model.Country;
import airporter.model.CountryDAO;
import airporter.model.CountryDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

/**
 * Created by pavel on 31.3.17.
 */
@Controller
public class QueryController {

    final private static Logger log = Logger.getLogger(QueryController.class.toString());

    @Autowired
    private CountryDAO countryDAO;


    @RequestMapping("/query")
    public String query(Model model) {
//        session = HibernateUtil.getSessionFactory().openSession();
//        user =  (User) session.get(User.class, user_id);

        Country c =  countryDAO.get();
        log.info("Executing query");
        return "query";
    }
}
