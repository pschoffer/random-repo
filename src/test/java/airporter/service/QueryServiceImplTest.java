package airporter.service;

import airporter.model.entity.Country;
import airporter.model.dao.CountryDAO;
import airporter.service.dto.CountryAirports;
import airporter.service.exception.CountryNotFoundException;
import airporter.service.impl.QueryServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;

/**
 * Created by pavel on 1.4.17.
 */
public class QueryServiceImplTest {
    private static final String EXISTING_COUNTRY = "CZ";
    private static final String NON_EXITING_COUNTRY = "BLA";
    @Mock
    private CountryDAO countryDAO;
    @InjectMocks
    private final QueryService service = new QueryServiceImpl();

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void whenExistingCountry_thenReturnInformation() throws Exception {
        // prepare
        when(countryDAO.getByCodeOrName(EXISTING_COUNTRY)).thenReturn(new Country());

        // execute
        final CountryAirports countryAirports = service.getCountryAirports(EXISTING_COUNTRY);

        // assert
        final Country country = countryAirports.getCountry();
        Assert.assertNotNull(country);
    }

    @Test(expectedExceptions = CountryNotFoundException.class,
            expectedExceptionsMessageRegExp = ".*not found.*" + NON_EXITING_COUNTRY + ".*")
    public void whenNonExistingCountry_thenThrowException() throws Exception {
        // execute
        service.getCountryAirports(NON_EXITING_COUNTRY);
    }
}