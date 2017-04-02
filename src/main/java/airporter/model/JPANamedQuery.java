package airporter.model;

/**
 * Created by pavel on 1.4.17.
 */
public class JPANamedQuery {
    public final static String SELECT_COUNTRY_BY_CODE = "SELECT_COUNTRY_BY_CODE";
    public final static String SELECT_COUNTRY_BY_NAME = "SELECT_COUNTRY_BY_NAME";
    public final static String SELECT_AIRPORTS_BY_COUNTRY_CODE = "SELECT_AIRPORTS_BY_COUNTRY_CODE";
    public static final String SELECT_COUNTRIES_BY_AIRPORT_COUNT = "SELECT_COUNTRIES_BY_AIRPORT_COUNT";
    public static final String SELECT_COUNTRIES_BY_LOW_AIRPORT_COUNT = "SELECT_COUNTRIES_BY_LOW_AIRPORT_COUNT";
    public static final String SELECT_RUNWAY_SURFACE_COUNT_PER_COUNTRY = "SELECT_RUNWAY_SURFACE_COUNT_PER_COUNTRY";
}
