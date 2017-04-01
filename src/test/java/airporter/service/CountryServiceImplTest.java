package airporter.service;

import airporter.model.Country;
import airporter.model.CountryDAO;
import airporter.service.exception.CountryNotFoundException;
import airporter.service.impl.CountryServiceImpl;
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
public class CountryServiceImplTest {
    private static final String EXISTING_COUNTRY = "CZ";
    private static final String NON_EXITING_COUNTRY = "BLA";
    @Mock
    private CountryDAO countryDAO;
    @InjectMocks
    private final CountryService service = new CountryServiceImpl();

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void whenExistingCountry_thenReturnInformation() throws Exception {
        // prepare
        when(countryDAO.getByCodeOrName(EXISTING_COUNTRY)).thenReturn(new Country());

        // execute
        final Country country = service.getCountryInformation(EXISTING_COUNTRY);

        // assert
        Assert.assertNotNull(country);
    }

    @Test(expectedExceptions = CountryNotFoundException.class,
            expectedExceptionsMessageRegExp = ".*not found.*" + NON_EXITING_COUNTRY + ".*")
    public void whenNonExistingCountry_thenThrowException() throws Exception {
        // execute
        service.getCountryInformation(NON_EXITING_COUNTRY);
    }
}