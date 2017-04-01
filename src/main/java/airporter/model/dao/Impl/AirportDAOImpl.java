package airporter.model.dao.Impl;

import airporter.model.JPANamedQuery;
import airporter.model.dao.AirportDAO;
import airporter.model.entity.Airport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by pavel on 1.4.17.
 */
@Component
public class AirportDAOImpl implements AirportDAO{
    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Airport> findByCountryCode(final String code) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final TypedQuery<Airport> namedQuery = entityManager.createNamedQuery(
                JPANamedQuery.SELECT_AIRPORT_BY_COUNTRY_CODE, Airport.class);
        namedQuery.setParameter("code", code);

        return namedQuery.getResultList();
    }
}
