package airporter.service.dto;

import airporter.model.dao.dto.RunwaySurfaceCount;
import airporter.model.entity.Country;

import java.util.Collections;
import java.util.List;

/**
 * Created by pavel on 1.4.17.
 */
public class CountryRunway {
    private final Country country;
    private final List<RunwaySurfaceCount> runways;
    private final int airportCount;

    public CountryRunway(final Country country, final List<RunwaySurfaceCount> runways, final int airportCount) {
        this.country = country;
        this.runways = runways;
        this.airportCount = airportCount;
    }

    public Country getCountry() {
        return country;
    }

    public List<RunwaySurfaceCount> getRunways() {
        return Collections.unmodifiableList(runways);
    }

    public int getAirportCount() {
        return airportCount;
    }
}
