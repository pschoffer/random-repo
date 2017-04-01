package airporter.service;

import airporter.dto.CountryAirports;

/**
 * Created by pavel on 1.4.17.
 */
public interface CountryService {
    CountryAirports getCountryInformation(final String country);
}
