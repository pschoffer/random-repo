package airporter.model.dao.Impl;

import airporter.model.JPANamedQuery;
import airporter.model.dao.CountryDAO;
import airporter.model.dao.dto.CountryAirportCount;
import airporter.model.entity.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<CountryAirportCount> findByHighestAirportCount(final int number) {
        final Query namedQuery = entityManager.createNamedQuery(JPANamedQuery.SELECT_COUNTRIES_BY_AIRPORT_COUNT);
        namedQuery.setMaxResults(number);

        final List resultList = namedQuery.getResultList();
        return convertResultSet(resultList);
    }

    @Override
    public List<CountryAirportCount> findByLowestAirportCount(final int number) {
        final Query namedQuery = entityManager.createNamedQuery(JPANamedQuery.SELECT_COUNTRIES_BY_LOW_AIRPORT_COUNT);
        namedQuery.setMaxResults(number);

        final List resultList = namedQuery.getResultList();
        return convertResultSet(resultList);
    }

    private List<CountryAirportCount> convertResultSet(final List resultList) {
        final List<CountryAirportCount> countryAirportCounts = new ArrayList<>(resultList.size());
        for (final Object resultSet : resultList) {
            Object[] result = (Object[]) resultSet;
            final Country country = (Country) result[0];
            Optional<Integer> countOptional = Optional.ofNullable((Integer) result[1]);
            final int count = countOptional.orElse(0);
            countryAirportCounts.add(new CountryAirportCount(count,country));
        }
        return countryAirportCounts;
    }


}

