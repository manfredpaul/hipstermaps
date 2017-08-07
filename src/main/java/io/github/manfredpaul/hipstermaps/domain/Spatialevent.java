package io.github.manfredpaul.hipstermaps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.vividsolutions.jts.geom.Geometry;

/**
 * A Spatialevent.
 */
@Entity
@Table(name = "spatialevent")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Spatialevent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @Lob
    @Column(name = "location")
    private byte[] location;

    @Column(name = "location_content_type")
    private String locationContentType;

    @Column(name = "geom")
    private Geometry geometry;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Spatialevent title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Spatialevent date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public byte[] getLocation() {
        return location;
    }

    public Spatialevent location(byte[] location) {
        this.location = location;
        return this;
    }

    public void setLocation(byte[] location) {
        this.location = location;
    }

    public String getLocationContentType() {
        return locationContentType;
    }

    public Spatialevent locationContentType(String locationContentType) {
        this.locationContentType = locationContentType;
        return this;
    }

    public void setLocationContentType(String locationContentType) {
        this.locationContentType = locationContentType;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Spatialevent spatialevent = (Spatialevent) o;
        if (spatialevent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), spatialevent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Spatialevent{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", date='" + getDate() + "'" +
            ", location='" + getLocation() + "'" +
            ", locationContentType='" + locationContentType + "'" +
            "}";
    }
}
