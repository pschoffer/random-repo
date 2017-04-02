package airporter.service.impl;

import airporter.model.dao.AirportDAO;
import airporter.model.dao.CountryDAO;
import airporter.model.dao.RunwayDAO;
import airporter.model.dao.dto.CountryAirportCount;
import airporter.model.dao.dto.RunwaySurfaceCount;
import airporter.model.entity.Airport;
import airporter.model.entity.Country;
import airporter.service.QueryService;
import airporter.service.dto.CountryRunway;
import airporter.service.exception.CountryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by pavel on 1.4.17.
 */
@Component
public class QueryServiceImpl implements QueryService {
    private static final int MAX_COUNTRY_CODE_LENGTH = 2;
    @Autowired
    private CountryDAO countryDAO;
    @Autowired
    private AirportDAO airportDAO;
    @Autowired
    private RunwayDAO runwayDAO;

    @Override
    public Country getCountry(final String ident) throws CountryNotFoundException {
        final Country country;
        if (ident.length() <= MAX_COUNTRY_CODE_LENGTH) {
            country = countryDAO.getByCode(ident);
        } else {
            country = countryDAO.getByName(ident);
        }

        if (country == null) {
            final String msg = String.format("Country was not found (using \"%s\").", ident);
            throw new CountryNotFoundException(msg);
        }
        return country;
    }

    @Override
    public List<Airport> getCountryAirports(final String countryCode) throws CountryNotFoundException {
        return airportDAO.findByCountryCode(countryCode);
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

        final List<String> countryCodes = new ArrayList<>();
        final Map<String, List<RunwaySurfaceCount>> countryRunwaySurfaces = new HashMap<>();
        for (final CountryAirportCount countryCount : countryCounts) {
            final Country country = countryCount.getCountry();
            countryCodes.add(country.getCode());

            final List<RunwaySurfaceCount> runways = new ArrayList<>();
            countryRunwaySurfaces.put(country.getCode(), runways);

            final CountryRunway countryRunway = new CountryRunway(country, runways,
                    countryCount.getCount());
            countryRunways.add(countryRunway);
        }

        final List<RunwaySurfaceCount> runwaySurfaceCounts = runwayDAO.findRunwaySurfaceCounts(countryCodes);
        for (final RunwaySurfaceCount runwaySurfaceCount : runwaySurfaceCounts) {
            final String countryCode = runwaySurfaceCount.getCountryCode();
            final List<RunwaySurfaceCount> countrySpecificList = countryRunwaySurfaces.get(countryCode);
            countrySpecificList.add(runwaySurfaceCount);
        }

        return countryRunways;
    }

    @Override
    public long getTotalCountCountries() {
        return countryDAO.getTotalCount();
    }
}
