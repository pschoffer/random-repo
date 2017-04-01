package airporter.model.dao.Impl;

import airporter.model.JPANamedQuery;
import airporter.model.dao.AirportDAO;
import airporter.model.entity.Airport;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

/**
 * Created by pavel on 1.4.17.
 */
public class AirportDAOImplTest {

    private static final String EXISTING_COUNTRY = "CZ";
    private static final String NON_EXISTING_COUNTRY = "BLABLA";
    @Mock
    private EntityManager entityManager;
    @InjectMocks
    private final AirportDAO dao = new AirportDAOImpl();

    @Mock
    private TypedQuery<Airport> query;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(entityManager.createNamedQuery(JPANamedQuery.SELECT_AIRPORT_BY_COUNTRY_CODE, Airport.class))
                .thenReturn(query);
    }

    @Test
    public void whenExistingCountry_thenReturnIt() throws Exception {
        // prepare
        final List<Airport> airportsFromDB = Arrays.asList(new Airport());
        when(query.getResultList()).thenReturn(airportsFromDB);

        // execute
        final List<Airport> airports = dao.findByCountryCode(EXISTING_COUNTRY);

        // assert
        Assert.assertFalse(airports.isEmpty());
    }

    @Test
    public void whenNonExistingCountry_thenReturnNull() throws Exception {
        // prepare
        when(query.getResultList()).thenReturn(Arrays.asList());

        // execute
        final List<Airport> airports = dao.findByCountryCode(NON_EXISTING_COUNTRY);

        // assert
        Assert.assertTrue(airports.isEmpty());
    }

}