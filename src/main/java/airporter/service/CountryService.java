package airporter.service;

import airporter.model.Country;
import airporter.service.exception.CountryNotFoundException;

/**
 * Created by pavel on 1.4.17.
 */
public interface CountryService {
    Country getCountryInformation(final String country) throws CountryNotFoundException;
}
