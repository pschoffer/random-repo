package airporter.model.dao.Impl;

import airporter.model.JPANamedQuery;
import airporter.model.dao.CountryDAO;
import airporter.model.entity.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by pavel on 1.4.17.
 */
@Repository
public class CountryDAOImpl implements CountryDAO {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Country getByCodeOrName(final String identification) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final TypedQuery<Country> namedQuery = entityManager.createNamedQuery(
                JPANamedQuery.SELECT_COUNTRY_BY_CODE_OR_NAME, Country.class);
        namedQuery.setParameter("code", identification);
        namedQuery.setParameter("name", identification);

        final List<Country> countries = namedQuery.getResultList();
        if (countries.size() != 1) {
            return null;
        }
        return countries.get(0);
    }

}
