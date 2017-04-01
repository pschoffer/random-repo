package airporter.service;

import airporter.model.dao.AirportDAO;
import airporter.model.dao.CountryDAO;
import airporter.model.entity.Airport;
import airporter.model.entity.Country;
import airporter.service.dto.CountryAirports;
import airporter.service.exception.CountryNotFoundException;
import airporter.service.impl.QueryServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by pavel on 1.4.17.
 */
public class QueryServiceImplTest {
    private static final String EXISTING_COUNTRY = "CZ";
    private static final String NON_EXITING_COUNTRY = "BLA";
    @Mock
    private CountryDAO countryDAO;
    @Mock
    private AirportDAO airportDAO;
    @InjectMocks
    private final QueryService service = new QueryServiceImpl();

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void whenExistingCountry_thenReturnInformation() throws Exception {
        // prepare
        final List<Airport> airports = Arrays.asList(new Airport());
        final Country expectedCountry = new Country();
        expectedCountry.setCode(EXISTING_COUNTRY);
        when(countryDAO.getByCodeOrName(anyString())).thenReturn(expectedCountry);
        when(airportDAO.findByCountryCode(anyString())).thenReturn(airports);

        // execute
        final CountryAirports countryAirports = service.getCountryAirports(EXISTING_COUNTRY);

        // assert
        final Country country = countryAirports.getCountry();
        Assert.assertNotNull(country);
        Assert.assertEquals(countryAirports.getAirports(), airports);
    }

    @Test(expectedExceptions = CountryNotFoundException.class,
            expectedExceptionsMessageRegExp = ".*not found.*" + NON_EXITING_COUNTRY + ".*")
    public void whenNonExistingCountry_thenThrowException() throws Exception {
        // execute
        service.getCountryAirports(NON_EXITING_COUNTRY);
    }
}