package airporter.model;

import airporter.model.entity.Country;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pavel on 1.4.17.
 */
public class CountryDAOImplTest {

    private static final String EXISTING_COUNTRY = "CZ";
    private static final String NON_EXISTING_COUNTRY = "BLABLA";
    @Mock
    private EntityManager entityManager;
    @InjectMocks
    private final CountryDAO dao = new CountryDAOImpl();

    @Mock
    private TypedQuery<Country> query;

    private Country country;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(entityManager.createNamedQuery(JPANamedQuery.SELECT_COUNTRY_BY_CODE_OR_NAME, Country.class))
                .thenReturn(query);

        country = new Country();
    }

    @Test
    public void whenExistingCountry_thenReturnIt() throws Exception {
        // prepare
        when(query.getResultList()).thenReturn(Arrays.asList(country));

        // execute
        final Country country = dao.getByCodeOrName(EXISTING_COUNTRY);

        // assert
        Assert.assertNotNull(country);
    }

    @Test
    public void whenNonExistingCountry_thenReturnNull() throws Exception {
        // prepare
        when(query.getResultList()).thenReturn(Arrays.asList());

        // execute
        final Country country = dao.getByCodeOrName(NON_EXISTING_COUNTRY);

        // assert
        Assert.assertNull(country);
    }
}