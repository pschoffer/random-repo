package airporter.controller;

import airporter.form.QueryForm;
import airporter.service.QueryService;
import airporter.service.dto.CountryAirports;
import airporter.service.exception.CountryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by pavel on 31.3.17.
 */
@Controller
public class QueryController {
    private final static String ERROR_KEY = "error";
    private final static String FORM_KEY = "form";
    private final static String COUNTRY_INFO_KEY = "country_info";

    @Autowired
    private QueryService queryService;

    @GetMapping("/query")
    public String queryForm(Model model) {
        model.addAttribute(FORM_KEY, new QueryForm());
        return "query";
    }

    @PostMapping("/query")
    public String querySubmit(Model model, @Valid QueryForm form, BindingResult bindingResult) {
        model.addAttribute(FORM_KEY, form);

        if (bindingResult.hasErrors()) {
            final String errorMsg = getErrorMsg(bindingResult);
            model.addAttribute(ERROR_KEY, errorMsg);
        } else {
            try {
                final CountryAirports countryAirports = queryService.getCountryAirports(form.getCountry());
                model.addAttribute(COUNTRY_INFO_KEY, countryAirports);
            } catch (final CountryNotFoundException e) {
                model.addAttribute(ERROR_KEY, e.getMessage());
            }
        }

        return "query";
    }

    private String getErrorMsg(final BindingResult bindingResult) {
        final List<ObjectError> allErrors = bindingResult.getAllErrors();
        final StringJoiner errorJoiner = new StringJoiner(", ");
        for (final ObjectError error : allErrors) {
            errorJoiner.add(error.getDefaultMessage());
        }
        return "Country: " + errorJoiner.toString();
    }
}
