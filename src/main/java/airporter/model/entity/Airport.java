package airporter.model.entity;

import airporter.model.JPANamedQuery;

import javax.persistence.*;
import java.util.List;

/**
 * Created by pavel on 1.4.17.
 */
@Entity
@Table(name = "airports")
@NamedQueries({
        @NamedQuery(
                name = JPANamedQuery.SELECT_AIRPORT_BY_COUNTRY_CODE,
                query = "from Airport where iso_country = :code"
        )
})
public class Airport {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "ident")
    private String ident;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "airport", fetch = FetchType.EAGER)
    private List<Runway> runways;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(final String ident) {
        this.ident = ident;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public List<Runway> getRunways() {
        return runways;
    }

    public void setRunways(final List<Runway> runways) {
        this.runways = runways;
    }
}
