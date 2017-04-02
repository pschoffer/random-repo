package airporter.service;

import airporter.model.dao.AirportDAO;
import airporter.model.dao.CountryDAO;
import airporter.model.dao.RunwayDAO;
import airporter.model.dao.dto.CountryAirportCount;
import airporter.model.dao.dto.RunwaySurfaceCount;
import airporter.model.entity.Airport;
import airporter.model.entity.Country;
import airporter.service.dto.CountryRunway;
import airporter.service.exception.CountryNotFoundException;
import airporter.service.impl.QueryServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by pavel on 1.4.17.
 */
public class QueryServiceImplTest {
    private static final String EXISTING_COUNTRY_CODE = "CZ";
    private static final String EXISTING_COUNTRY_NAME = "Czech Republic";
    private static final String ONE_RUNWAY_COUNTRY = "CZ";
    private static final String TWO_RUNWAY_COUNTRY = "US";
    private static final int LIMIT = 5;
    @Mock
    private CountryDAO countryDAO;
    @Mock
    private AirportDAO airportDAO;
    @Mock
    private RunwayDAO runwayDAO;
    @InjectMocks
    private final QueryService service = new QueryServiceImpl();

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    @Test(expectedExceptions = CountryNotFoundException.class,
            expectedExceptionsMessageRegExp = ".*not found.*" + EXISTING_COUNTRY_CODE + ".*")
    public void whenShortIdent_thenJustTryCode() throws Exception {
        // prepare
        when(countryDAO.getByName(anyString())).thenReturn(new Country());

        // execute
        service.getCountry(EXISTING_COUNTRY_CODE);
    }

    @Test
    public void whenLongIdent_thenJustTryName() throws Exception {
        // prepare
        final Country expectedCountry = new Country();
        when(countryDAO.getByName(anyString())).thenReturn(expectedCountry);

        // execute
        final Country resultCountry = service.getCountry(EXISTING_COUNTRY_NAME);

        // assert
        Assert.assertEquals(resultCountry, expectedCountry);
        verify(countryDAO, never()).getByCode(EXISTING_COUNTRY_NAME);
    }

    @Test
    public void whenExistingCountry_thenReturnInformation() throws Exception {
        // prepare
        final List<Airport> expectedAirports = Arrays.asList(new Airport());
        when(airportDAO.findByCountryCode(anyString())).thenReturn(expectedAirports);

        // execute
        final List<Airport> airports = service.getCountryAirports(EXISTING_COUNTRY_CODE);

        // assert
        Assert.assertEquals(airports, expectedAirports);
    }

    @Test
    public void whenLookingForCount_thenTransformResultCorrectly() throws Exception {
        // prepare
        final int expectedCount = 2;
        final Country expectedCountry = new Country();
        final CountryAirportCount countryCount = new CountryAirportCount(expectedCount, expectedCountry);
        final List<CountryAirportCount> countryCounts = Collections.singletonList(countryCount);
        when(countryDAO.findByHighestAirportCount(LIMIT)).thenReturn(countryCounts);

        // execute
        final List<CountryRunway> countriesWithTheMostAirports = service.findCountriesHighestAirportCount(LIMIT);

        // assert
        Assert.assertEquals(countriesWithTheMostAirports.size(), countryCounts.size());
        final CountryRunway countryRunway = countriesWithTheMostAirports.get(0);
        Assert.assertEquals(countryRunway.getAirportCount(), expectedCount);
        Assert.assertEquals(countryRunway.getCountry(), expectedCountry);
    }

    @Test
    public void whenWhenLookingForCountries_thenRunwaysAreMappedCorrectly() throws Exception {
        // prepare
        final Country oneRunwayCountry = new Country();
        final Country twoRunwayCountry = new Country();
        oneRunwayCountry.setCode(ONE_RUNWAY_COUNTRY);
        twoRunwayCountry.setCode(TWO_RUNWAY_COUNTRY);
        final List<CountryAirportCount> countryCounts = Arrays.asList(
                new CountryAirportCount(0, oneRunwayCountry),
                new CountryAirportCount(0, twoRunwayCountry)
        );
        when(countryDAO.findByHighestAirportCount(LIMIT)).thenReturn(countryCounts);
        when(runwayDAO.findRunwaySurfaceCounts(Arrays.asList(ONE_RUNWAY_COUNTRY, TWO_RUNWAY_COUNTRY))).thenReturn(
                Arrays.asList(
                        new RunwaySurfaceCount(ONE_RUNWAY_COUNTRY, "A", 1),
                        new RunwaySurfaceCount(TWO_RUNWAY_COUNTRY, "A", 1),
                        new RunwaySurfaceCount(TWO_RUNWAY_COUNTRY, "B", 1)
                )
        );

        // execute
        final List<CountryRunway> countriesWithTheMostAirports = service.findCountriesHighestAirportCount(LIMIT);

        // assert
        final CountryRunway oneCountryRunway = countriesWithTheMostAirports.get(0);
        final CountryRunway twoCountryRunway = countriesWithTheMostAirports.get(1);
        Assert.assertEquals(oneCountryRunway.getRunways().size(), 1);
        Assert.assertEquals(twoCountryRunway.getRunways().size(), 2);
    }

    @Test
    public void whenGettingLowest_OrderShouldBeReversed() throws Exception {
        // prepare
        final Country firstCountry = new Country();
        final Country secondCountry = new Country();
        final CountryAirportCount firstCountryCount = new CountryAirportCount(1, firstCountry);
        final CountryAirportCount secondCountryCount = new CountryAirportCount(2, secondCountry);
        final List<CountryAirportCount> countryCounts = Arrays.asList(firstCountryCount, secondCountryCount);
        when(countryDAO.findByLowestAirportCount(LIMIT)).thenReturn(countryCounts);

        // execute
        final List<CountryRunway> countriesWithTheLowestAirports = service.findCountriesLowestAirportCount(LIMIT);

        // assert
        Assert.assertEquals(countriesWithTheLowestAirports.size(), countryCounts.size());
        final CountryRunway firstReversedRunway = countriesWithTheLowestAirports.get(0);
        Assert.assertEquals(firstReversedRunway.getCountry(), secondCountry);
    }
}