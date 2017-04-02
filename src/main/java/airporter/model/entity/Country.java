package airporter.model.entity;

import airporter.model.JPANamedQuery;

import javax.persistence.*;

/**
 * Created by pavel on 31.3.17.
 */
@Entity
@Table(name = "countries")
@NamedQueries({
        @NamedQuery(
                name = JPANamedQuery.SELECT_COUNTRY_BY_CODE_OR_NAME,
                query = "from Country where lower(code) = lower(:code) or lower(name) = lower(:name)"
        )
})
@SqlResultSetMapping(
        name = "CountriesWithCount",
        entities = @EntityResult(
                entityClass = Country.class
        ),
        columns = @ColumnResult(
                name = "counter",
                type = Integer.class
        )
)
@NamedNativeQueries({
        @NamedNativeQuery(
                name = JPANamedQuery.SELECT_COUNTRIES_BY_AIRPORT_COUNT,
                query = "select country.*, airports.counter " +
                        "from countries as country, " +
                        "     ( select iso_country as country, count(airports.id) counter " +
                        "       from airports " +
                        "       group by iso_country) as airports " +
                        "where country.code = airports.country " +
                        "order by airports.counter DESC, country.name",
                resultSetMapping = "CountriesWithCount"
        ),
        @NamedNativeQuery(
                name = JPANamedQuery.SELECT_COUNTRIES_BY_LOW_AIRPORT_COUNT,
                query = "select country.*, airports.counter " +
                        "from countries as country LEFT JOIN " +
                        "     ( select iso_country as country, count(airports.id) counter " +
                        "       from airports " +
                        "       group by iso_country) as airports ON (country.code = airports.country) " +
                        "order by airports.counter, country.name DESC",
                resultSetMapping = "CountriesWithCount"
        )
})
public class Country {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}

