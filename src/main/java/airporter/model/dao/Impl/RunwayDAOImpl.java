package airporter.model.dao.Impl;

import airporter.model.JPANamedQuery;
import airporter.model.dao.RunwayDAO;
import airporter.model.dao.dto.RunwaySurfaceCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pavel on 1.4.17.
 */
@Component
public class RunwayDAOImpl implements RunwayDAO {
    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RunwaySurfaceCount> findRunwaySurfaceCounts(final List<String> countryCodes) {
        final Query namedQuery = entityManager.createNamedQuery(JPANamedQuery.SELECT_RUNWAY_SURFACE_COUNT_PER_COUNTRY);
        namedQuery.setParameter("countryCodes", countryCodes);

        final List resultList = namedQuery.getResultList();
        return convertResultSet(resultList);
    }

    private List<RunwaySurfaceCount> convertResultSet(final List resultList) {
        final List<RunwaySurfaceCount> countryAirportCounts = new ArrayList<>(resultList.size());
        for (final Object resultSet : resultList) {
            final Object[] result = (Object[]) resultSet;
            final String countryCode = (String) result[0];
            final String surface = (String) result[1];
            final int count = (Integer) result[2];
            countryAirportCounts.add(new RunwaySurfaceCount(countryCode, surface, count));
        }
        return countryAirportCounts;
    }
}
