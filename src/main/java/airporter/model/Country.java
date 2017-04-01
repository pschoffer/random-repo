package airporter.model;

import javax.persistence.*;
import javax.persistence.NamedQuery;

/**
 * Created by pavel on 31.3.17.
 */
@Entity
@Table(name = "countries")
@NamedQueries({
        @NamedQuery(
                name = JPANamedQuery.SELECT_COUNTRY_BY_CODE_OR_NAME,
                query = "from Country where code = :code or name = :name"
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

