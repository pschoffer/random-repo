package airporter.model.dao;

import airporter.model.entity.Airport;

import java.util.List;

/**
 * Created by pavel on 1.4.17.
 */
public interface AirportDAO {
    List<Airport> findByCountryCode(final String code);
}
