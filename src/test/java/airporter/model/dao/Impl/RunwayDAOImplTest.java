package airporter.model.dao.Impl;

import airporter.model.JPANamedQuery;
import airporter.model.dao.RunwayDAO;
import airporter.model.dao.dto.RunwayIdentCount;
import airporter.model.dao.dto.RunwaySurfaceCount;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by pavel on 2.4.17.
 */
public class RunwayDAOImplTest {
    private final static String COUNTRY_CODE = "CZ";
    private final static String SURFACE = "Grass";
    private static final Object IDENT = "H1";
    private final static int COUNT = 1;

    @Mock
    private EntityManager entityManager;
    @InjectMocks
    private RunwayDAO dao = new RunwayDAOImpl();

    @Mock
    private Query query;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(entityManager.createNamedQuery(JPANamedQuery.SELECT_RUNWAY_SURFACE_COUNT_PER_COUNTRY)).thenReturn(query);
        when(entityManager.createNamedQuery(JPANamedQuery.SELECT_RUNWAY_COMMON_IDENT)).thenReturn(query);
    }
    @Test
    public void whenFoundSurfaceCount_returnInDTO() throws Exception {
        // prepare
        final Object[] resultSet = {COUNTRY_CODE, SURFACE, COUNT};
        final List dbResult = Collections.singletonList(resultSet);
        when(query.getResultList()).thenReturn(dbResult);

        // execute
        final List<RunwaySurfaceCount> runwaySurfaceCounts = dao.findRunwaySurfaceCounts(Collections.singletonList(COUNTRY_CODE));

        // assertA

        Assert.assertEquals(runwaySurfaceCounts.size(), dbResult.size());
        final RunwaySurfaceCount runwaySurfaceCount = runwaySurfaceCounts.get(0);
        Assert.assertEquals(runwaySurfaceCount.getCount(), COUNT);
        Assert.assertEquals(runwaySurfaceCount.getCountryCode(), COUNTRY_CODE);
        Assert.assertEquals(runwaySurfaceCount.getSurface(), SURFACE);
    }

    @Test
    public void whenFoundIdentCount_returnInDTO() throws Exception {
        // prepare
        final Object[] resultSet = {IDENT, COUNT};
        final List dbResult = Collections.singletonList(resultSet);
        when(query.getResultList()).thenReturn(dbResult);

        // execute
        final List<RunwayIdentCount> runwayIdentCounts = dao.findRunwayIdentCounts(0);

        // assertA

        Assert.assertEquals(runwayIdentCounts.size(), dbResult.size());
        final RunwayIdentCount runwayIdentCount = runwayIdentCounts.get(0);
        Assert.assertEquals(runwayIdentCount.getCount(), COUNT);
        Assert.assertEquals(runwayIdentCount.getIdent(), IDENT);
    }

}