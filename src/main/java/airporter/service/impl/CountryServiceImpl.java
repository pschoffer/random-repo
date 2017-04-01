package airporter.service.impl;

import airporter.model.entity.Country;
import airporter.model.CountryDAO;
import airporter.service.CountryService;
import airporter.service.exception.CountryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by pavel on 1.4.17.
 */
@Component
public class CountryServiceImpl implements CountryService {
    @Autowired
    private CountryDAO countryDAO;

    @Override
    public Country getCountry(final String countryIdentification) throws CountryNotFoundException {
        final Country country = countryDAO.getByCodeOrName(countryIdentification);
        if (country == null) {
            final String msg = String.format("Country was not found (using \"%s\").", countryIdentification);
            throw new CountryNotFoundException(msg);
        }
        return country;
    }
}
