package com.ishvatov.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Defines a basic entity in the database, which stores the
 * information about city unit.
 *
 * @author Sergey Khvatov
 */
@Entity
@Table(name = "city")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CityEntity extends AbstractEntity<String> {

    /**
     * String representation of the 'latitude'
     * column name in the table.
     */
    public static final String LATITUDE_FIELD = "latitude";

    /**
     * String representation of the 'longitude'
     * column name in the table.
     */
    public static final String LONGITUDE_FIELD = "longitude";

    /**
     * Latitude of the object on the map.
     */
    @Column(name = LATITUDE_FIELD)
    private Double latitude;

    /**
     * Longitude of the object on the map.
     */
    @Column(name = LONGITUDE_FIELD)
    private Double longitude;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CityEntity)) return false;
        if (!super.equals(o)) return false;
        CityEntity that = (CityEntity) o;
        return getLatitude().equals(that.getLatitude()) &&
            getLongitude().equals(that.getLongitude());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getLatitude(), getLongitude());
    }
}
