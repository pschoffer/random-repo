package airporter.service.impl;

import airporter.model.dao.AirportDAO;
import airporter.model.dao.CountryDAO;
import airporter.model.dao.dto.CountryAirportCount;
import airporter.model.entity.Airport;
import airporter.model.entity.Country;
import airporter.model.entity.Runway;
import airporter.service.QueryService;
import airporter.service.dto.CountryAirports;
import airporter.service.dto.CountryRunway;
import airporter.service.exception.CountryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by pavel on 1.4.17.
 */
@Component
public class QueryServiceImpl implements QueryService {
    @Autowired
    private CountryDAO countryDAO;
    @Autowired
    private AirportDAO airportDAO;

    @Override
    public CountryAirports getCountryAirports(final String countryIdentification) throws CountryNotFoundException {
        final Country country = countryDAO.getByCodeOrName(countryIdentification);
        if (country == null) {
            final String msg = String.format("Country was not found (using \"%s\").", countryIdentification);
            throw new CountryNotFoundException(msg);
        }

        final List<Airport> airports = airportDAO.findByCountryCode(country.getCode());
        return new CountryAirports(country, airports);
    }

    @Override
    public List<CountryRunway> findCountriesHighestAirportCount(final int number) {
        final List<CountryAirportCount> countryCounts = countryDAO.findByHighestAirportCount(number);
        return findCountryRunways(countryCounts);
    }

    @Override
    public List<CountryRunway> findCountriesLowestAirportCount(final int number) {
        final List<CountryAirportCount> countryCounts = countryDAO.findByLowestAirportCount(number);
        Collections.reverse(countryCounts);
        return findCountryRunways(countryCounts);
    }

    private List<CountryRunway> findCountryRunways(final List<CountryAirportCount> countryCounts) {
        final List<CountryRunway> countryRunways = new ArrayList<>();

        for (final CountryAirportCount countryCount : countryCounts) {
            final List<Runway> runways = new ArrayList<>();
            final CountryRunway countryRunway = new CountryRunway(countryCount.getCountry(), runways,
                    countryCount.getCount());
            countryRunways.add(countryRunway);
        }

        return countryRunways;
    }
}
