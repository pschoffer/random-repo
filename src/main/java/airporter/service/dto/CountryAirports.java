package airporter.service.dto;

import airporter.model.entity.Airport;
import airporter.model.entity.Country;

import java.util.Collections;
import java.util.List;

/**
 * Created by pavel on 1.4.17.
 */
public class CountryAirports {
    private final Country country;
    private final List<Airport> airports;

    public CountryAirports(final Country country, final List<Airport> airports) {
        this.country = country;
        this.airports = airports;
    }

    public Country getCountry() {
        return country;
    }

    public List<Airport> getAirports() {
        return Collections.unmodifiableList(airports);
    }
}
