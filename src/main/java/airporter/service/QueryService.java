package airporter.service;

import airporter.service.dto.CountryAirports;
import airporter.service.exception.CountryNotFoundException;

/**
 * Created by pavel on 1.4.17.
 */
public interface QueryService {
    CountryAirports getCountryAirports(final String country) throws CountryNotFoundException;
}
