package airporter.model.dao;

import airporter.model.entity.Country;

/**
 * Created by pavel on 1.4.17.
 */
public interface CountryDAO {
    Country getByCodeOrName(final String identification);
}
