package airporter.service;

import airporter.model.entity.Country;
import airporter.service.exception.CountryNotFoundException;

/**
 * Created by pavel on 1.4.17.
 */
public interface CountryService {
    Country getCountry(final String country) throws CountryNotFoundException;
}
