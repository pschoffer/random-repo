package airporter.model.entity;

import airporter.model.JPANamedQuery;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

/**
 * Created by pavel on 1.4.17.
 */
@Entity
@Table(name = "airports")
@NamedQueries({
        @NamedQuery(
                name = JPANamedQuery.SELECT_AIRPORTS_BY_COUNTRY_CODE,
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

    @Column(name = "iso_country")
    private String country;

    @OneToMany(mappedBy = "airport", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
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
