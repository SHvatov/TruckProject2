package com.ishvatov.model.entity;

import com.ishvatov.model.enums.OrderStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

/**
 * Defines a basic order in the system.
 *
 * @author Sergey Khvatov
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity extends AbstractEntity<Integer> {

    /**
     * String representation of the 'status'
     * column name in the table.
     */
    public static final String STATUS_FIELD = "status";

    /**
     * String representation of the 'last_updated'
     * column name in the table.
     */
    public static final String LAST_UPDATED_FIELD = "last_updated";

    /**
     * Status of the order.
     */
    @Column(name = STATUS_FIELD)
    @Enumerated(EnumType.STRING)
    private OrderStatusType status;

    /**
     * Date - last time order was updated.
     */
    @Column(name = LAST_UPDATED_FIELD)
    private Timestamp lastUpdated;

    /**
     * Truck, that is assigned to this order.
     */
    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY)
    private TruckEntity assignedTruck;

    /**
     * Set of drivers, who are assigned to this order.
     */
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private Set<DriverEntity> assignedDrivers = new HashSet<>();

    /**
     * Set of waypoints, that are located in the order.
     */
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<WaypointEntity> assignedWaypoints = new ArrayList<>();

    /**
     * Adds driver to the set of the assigned to this order ones.
     *
     * @param driverEntity {@link DriverEntity} entity.
     */
    public void addDriver(DriverEntity driverEntity) {
        Optional.ofNullable(driverEntity).ifPresent(e -> {
            assignedDrivers.add(e);
            e.setOrder(this);
        });
    }

    /**
     * Removes driver from the set of the the assigned to this order ones.
     *
     * @param driverEntity {@link DriverEntity} entity.
     */
    public void removeDriver(DriverEntity driverEntity) {
        Optional.ofNullable(driverEntity).ifPresent(e -> {
            assignedDrivers.remove(e);
            e.setOrder(null);
        });
    }

    /**
     * Adds waypoint to the set of the assigned to this order ones.
     *
     * @param waypointEntity {@link WaypointEntity} entity.
     */
    public void addWaypoint(WaypointEntity waypointEntity) {
        Optional.ofNullable(waypointEntity).ifPresent(e -> {
            assignedWaypoints.add(e);
            e.setOrder(this);
        });
    }

    /**
     * Removes waypoint from the set of the located in this order ones.
     *
     * @param waypointEntity {@link WaypointEntity} entity.
     */
    public void removeWaypoint(WaypointEntity waypointEntity) {
        Optional.ofNullable(waypointEntity).ifPresent(e -> {
            assignedWaypoints.remove(e);
            e.setOrder(null);
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderEntity)) return false;
        if (!super.equals(o)) return false;
        OrderEntity that = (OrderEntity) o;
        return getStatus() == that.getStatus() && getLastUpdated().equals(that.getLastUpdated());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getStatus(), getLastUpdated());
    }
}
