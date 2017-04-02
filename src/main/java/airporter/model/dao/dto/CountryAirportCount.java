package airporter.model.dao.dto;

import airporter.model.entity.Country;

/**
 * Created by pavel on 2.4.17.
 */
public class CountryAirportCount {
    private final int count;
    private final Country country;

    public CountryAirportCount(final int count, final Country country) {
        this.count = count;
        this.country = country;
    }

    public int getCount() {
        return count;
    }

    public Country getCountry() {
        return country;
    }
}
