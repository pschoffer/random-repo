package airporter.service;

import airporter.dto.CountryAirports;
import airporter.model.Country;
import airporter.model.CountryDAO;
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
    public CountryAirports getCountryInformation(final String countryIdentification) {
        final Country country = countryDAO.getByCodeOrName(countryIdentification);
        return null;
    }
}
