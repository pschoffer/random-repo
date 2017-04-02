package airporter.service;

import airporter.service.dto.CountryAirports;
import airporter.service.dto.CountryRunway;
import airporter.service.exception.CountryNotFoundException;

import java.util.List;

/**
 * Created by pavel on 1.4.17.
 */
public interface QueryService {
    CountryAirports getCountryAirports(final String country) throws CountryNotFoundException;

    List<CountryRunway> findCountriesWithTheMostAirports(final int number);
}
