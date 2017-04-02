package airporter.model.dao;

import airporter.model.dao.dto.CountryAirportCount;
import airporter.model.entity.Country;

import java.util.List;

/**
 * Created by pavel on 1.4.17.
 */
public interface CountryDAO {
    Country getByName(final String ident);

    Country getByCode(final String ident);

    List<CountryAirportCount> findByHighestAirportCount(int number);

    List<CountryAirportCount> findByLowestAirportCount(int number);

    long getTotalCount();
}
