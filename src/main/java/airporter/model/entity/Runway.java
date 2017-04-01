package airporter.model.entity;

import javax.persistence.*;

/**
 * Created by pavel on 1.4.17.
 */
@Entity
@Table(name = "runways")
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
