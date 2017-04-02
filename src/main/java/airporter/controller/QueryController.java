package airporter.controller;

import airporter.form.QueryForm;
import airporter.model.entity.Airport;
import airporter.model.entity.Country;
import airporter.service.QueryService;
import airporter.service.exception.CountryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Created by pavel on 31.3.17.
 */
@Controller
public class QueryController {
    private final static String ERROR_KEY = "error";
    private final static String FORM_KEY = "form";
    private final static String COUNTRY_KEY = "country";
    private final static String AIRPORTS_KEY = "airports";
    private static final String MULTIPLE_OPTIONS_KEY = "options";

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
                final Map<String, Object> attributes = getModelAttributes(form.getCountry());
                model.addAllAttributes(attributes);
            } catch (final CountryNotFoundException e) {
                model.addAttribute(ERROR_KEY, e.getMessage());
            }
        }

        return "query";
    }

    private Map<String, Object> getModelAttributes(final String countryIdent) throws CountryNotFoundException {
        final List<Country> countries = queryService.findCountries(countryIdent);
        final Map<String, Object> attributes = new HashMap<>();
        if (countries.size() > 1) {
            attributes.put(MULTIPLE_OPTIONS_KEY, countries);
        } else {
            final Country country = countries.get(0);
            final List<Airport> airports = queryService.getCountryAirports(country.getCode());
            attributes.put(COUNTRY_KEY, country);
            attributes.put(AIRPORTS_KEY, airports);
        }
        return attributes;
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
