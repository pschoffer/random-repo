package airporter.service.impl;

import airporter.model.dao.AirportDAO;
import airporter.model.dao.CountryDAO;
import airporter.model.entity.Airport;
import airporter.model.entity.Country;
import airporter.service.QueryService;
import airporter.service.dto.CountryAirports;
import airporter.service.exception.CountryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
}
