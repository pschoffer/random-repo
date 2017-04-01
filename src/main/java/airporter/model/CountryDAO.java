package airporter.model;

/**
 * Created by pavel on 1.4.17.
 */
public interface CountryDAO {
    Country getByCodeOrName(final String identification);
}
