package airporter.model.entity;

import airporter.model.JPANamedQuery;

import javax.persistence.*;

/**
 * Created by pavel on 1.4.17.
 */
@Entity
@Table(name = "runways")
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "SurfaceCount",
                columns = {
                    @ColumnResult(name = "code", type = String.class),
                    @ColumnResult(name = "surface", type = String.class),
                    @ColumnResult(name = "counter", type = Integer.class)

                }
        )
})
@NamedNativeQueries({
        @NamedNativeQuery(
                name = JPANamedQuery.SELECT_RUNWAY_SURFACE_COUNT_PER_COUNTRY,
                query = "select countries.code, runways.surface, count(runways.id) as counter " +
                        "from runways, airports, countries " +
                        "where runways.airport_ref = airports.id AND " +
                        "       airports.iso_country = countries.code AND " +
                        "       countries.code in (:countryCodes) " +
                        "group by countries.code, runways.surface " +
                        "order by count(runways.id) DESC, runways.surface",
                resultSetMapping = "SurfaceCount"
        )
})
public class Runway {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "surface")
    private String surface;

    @Column(name = "length_ft")
    private int length;

    @Column(name = "width_ft")
    private int width;

    @ManyToOne
    @JoinColumn(name = "airport_ref")
    private Airport airport;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getSurface() {
        return surface;
    }

    public void setSurface(final String surface) {
        this.surface = surface;
    }

    public int getLength() {
        return length;
    }

    public void setLength(final int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(final Airport airport) {
        this.airport = airport;
    }
}
