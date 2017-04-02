package airporter.service.dto;

import airporter.model.entity.Airport;
import airporter.model.entity.Country;
import airporter.model.entity.Runway;

import java.util.Collections;
import java.util.List;

/**
 * Created by pavel on 1.4.17.
 */
public class CountryRunway {
    private final Country country;
    private final List<Runway> runways;
    private final int airportCount;

    public CountryRunway(final Country country, final List<Runway> runways, final int airportCount) {
        this.country = country;
        this.runways = runways;
        this.airportCount = airportCount;
    }

    public Country getCountry() {
        return country;
    }

    public List<Runway> getRunways() {
        return Collections.unmodifiableList(runways);
    }

    public int getAirportCount() {
        return airportCount;
    }
}
