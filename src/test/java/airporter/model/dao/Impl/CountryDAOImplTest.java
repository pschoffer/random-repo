package airporter.model.dao.Impl;

import airporter.model.JPANamedQuery;
import airporter.model.dao.CountryDAO;
import airporter.model.dao.dto.CountryAirportCount;
import airporter.model.entity.Country;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by pavel on 1.4.17.
 */
public class CountryDAOImplTest {

    private static final String EXISTING_COUNTRY = "CZ";
    private static final String NON_EXISTING_COUNTRY = "BLABLA";
    private static final int LIMIT = 5;
    @Mock
    private EntityManager entityManager;
    @InjectMocks
    private final CountryDAO dao = new CountryDAOImpl();

    @Mock
    private TypedQuery<Country> typedQuery;
    @Mock
    private Query query;

    private Country country;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(entityManager.createNamedQuery(JPANamedQuery.SELECT_COUNTRY_BY_CODE_OR_NAME, Country.class))
                .thenReturn(typedQuery);
        when(entityManager.createNamedQuery(JPANamedQuery.SELECT_COUNTRIES_BY_AIRPORT_COUNT)).thenReturn(query);

        country = new Country();
    }

    @Test
    public void whenExistingCountry_thenReturnIt() throws Exception {
        // prepare
        when(typedQuery.getResultList()).thenReturn(Arrays.asList(country));

        // execute
        final Country country = dao.getByCodeOrName(EXISTING_COUNTRY);

        // assert
        Assert.assertNotNull(country);
    }

    @Test
    public void whenNonExistingCountry_thenReturnNull() throws Exception {
        // prepare
        when(typedQuery.getResultList()).thenReturn(Arrays.asList());

        // execute
        final Country country = dao.getByCodeOrName(NON_EXISTING_COUNTRY);

        // assert
        Assert.assertNull(country);
    }

    @Test
    public void whenFoundCount_returnInDTO() throws Exception {
        // prepare
        final int expectedCount = 5;
        final Country expectedCountry = new Country();
        final Object[] resultSet = {expectedCountry, expectedCount};
        final List dbResult = Collections.singletonList(resultSet);
        when(query.getResultList()).thenReturn(dbResult);

        // execute
        final List<CountryAirportCount> airportCounts = dao.findByHighestAirportCount(LIMIT);

        // assert
        Assert.assertEquals(airportCounts.size(), dbResult.size());
        final CountryAirportCount countryAirportCount = airportCounts.get(0);
        Assert.assertEquals(countryAirportCount.getCount(), expectedCount);
        Assert.assertEquals(countryAirportCount.getCountry(), expectedCountry);
    }
}