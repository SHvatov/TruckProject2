package com.ishvatov.model.entity;

import com.ishvatov.model.enums.CargoActionType;
import com.ishvatov.model.enums.WaypointStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

/**
 * Defines a basic entity in the database, that stores the information
 * about one way point in the order.
 *
 * @author Sergey Khvatov
 */
@Entity
@Table(name = "waypoint")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WaypointEntity extends AbstractEntity<Integer> {

    /**
     * String representation of the 'action'
     * column name in the table.
     */
    public static final String ACTION_FIELD = "action";

    /**
     * String representation of the 'status'
     * column name in the table.
     */
    public static final String STATUS_FIELD = "status";

    /**
     * String representation of the 'order_id'
     * column name in the table.
     */
    public static final String ORDER_ID_FIELD = "order_id";

    /**
     * String representation of the 'cargo_id'
     * column name in the table.
     */
    public static final String CARGO_ID_FIELD = "cargo_id";

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
     * Defines whether cargo is being loaded or
     * unloaded in this city.
     */
    @Column(name = ACTION_FIELD)
    private CargoActionType action;

    /**
     * Defines the status of the waypoint (completed or not).
     */
    @Column(name = STATUS_FIELD)
    private WaypointStatusType status;

    /**
     * Order, this waypoint is assigned to.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = ORDER_ID_FIELD)
    private OrderEntity order;

    /**
     * Cargo, that is assigned to this waypoint.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = CARGO_ID_FIELD)
    private CargoEntity cargo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WaypointEntity)) return false;
        if (!super.equals(o)) return false;
        WaypointEntity that = (WaypointEntity) o;
        return getLatitude().equals(that.getLatitude()) &&
            getLongitude().equals(that.getLongitude()) &&
            getAction() == that.getAction() &&
            getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getLatitude(),
            getLongitude(), getAction(), getStatus());
    }
}