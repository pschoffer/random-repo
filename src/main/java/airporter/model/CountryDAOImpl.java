package airporter.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by pavel on 1.4.17.
 */
@Repository
public class CountryDAOImpl implements CountryDAO {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    public Country get() {
        return entityManager.find(Country.class, 302672);
    }
}

