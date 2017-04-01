package airporter.model.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by pavel on 1.4.17.
 */
@Entity
@Table(name = "airports")
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
}
