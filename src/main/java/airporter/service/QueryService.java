package airporter.service;

import airporter.model.dao.dto.RunwayIdentCount;
import airporter.model.entity.Airport;
import airporter.model.entity.Country;
import airporter.service.dto.CountryRunway;
import airporter.service.exception.CountryNotFoundException;

import java.util.List;

/**
 * Created by pavel on 1.4.17.
 */
public interface QueryService {
    List<Country> findCountries(final String ident) throws CountryNotFoundException;

    List<Airport> getCountryAirports(final String countryCode) throws CountryNotFoundException;

    List<CountryRunway> findCountriesHighestAirportCount(final int number);

    List<CountryRunway> findCountriesLowestAirportCount(final int number);

    long getTotalCountCountries();

    List<RunwayIdentCount> findCommonRunways(final int limit);
}
