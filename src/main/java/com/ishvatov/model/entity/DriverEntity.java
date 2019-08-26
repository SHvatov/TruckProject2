package com.ishvatov.model.entity;

import com.ishvatov.model.enums.DriverStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Defines basic driver entity in the database.
 *
 * @author Sergey Khvatov
 */
@Entity
@Table(name = "driver")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverEntity extends AbstractEntity<String> {

    /**
     * String representation of the 'name'
     * column name in the table.
     */
    public static final String NAME_FIELD = "name";

    /**
     * String representation of the 'surname'
     * column name in the table.
     */
    public static final String SURNAME_FIELD = "surname";

    /**
     * String representation of the 'worked_hours'
     * column name in the table.
     */
    public static final String WORKED_HOURS_FIELD = "worked_hours";

    /**
     * String representation of the 'last_updated'
     * column name in the table.
     */
    public static final String LAST_UPDATED_FIELD = "last_updated";

    /**
     * String representation of the 'status'
     * column name in the table.
     */
    public static final String STATE_FIELD = "status";

    /**
     * String representation of the 'truck_id'
     * column name in the table.
     */
    public static final String TRUCK_ID_FIELD = "truck_id";

    /**
     * String representation of the 'order_id'
     * column name in the table.
     */
    public static final String ORDER_ID_FIELD = "order_id";

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

    /**
     * Name of the driver.
     */
    @Column(name = NAME_FIELD)
    private String name;

    /**
     * Surname of the driver.
     */
    @Column(name = SURNAME_FIELD)
    private String surname;

    /**
     * Number of hours driver has worked
     * in this month.
     */
    @Column(name = WORKED_HOURS_FIELD)
    private Integer workedHours;

    /**
     * Date - last time order was updated.
     */
    @Column(name = LAST_UPDATED_FIELD)
    private Timestamp lastUpdated;

    /**
     * Status of the driver.
     */
    @Column(name = STATE_FIELD)
    @Enumerated(EnumType.STRING)
    private DriverStatusType status;

    /**
     * Truck, this driver is assigned to.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = TRUCK_ID_FIELD)
    private TruckEntity truck;

    /**
     * Order, this driver is assigned to.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = ORDER_ID_FIELD)
    private OrderEntity order;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DriverEntity)) return false;
        if (!super.equals(o)) return false;
        DriverEntity that = (DriverEntity) o;
        return getLatitude().equals(that.getLatitude()) &&
            getLongitude().equals(that.getLongitude()) &&
            getName().equals(that.getName()) &&
            getSurname().equals(that.getSurname()) &&
            getWorkedHours().equals(that.getWorkedHours()) &&
            getLastUpdated().equals(that.getLastUpdated()) &&
            getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getLatitude(), getLongitude(),
            getName(), getSurname(), getWorkedHours(), getLastUpdated(), getStatus());
    }
}
