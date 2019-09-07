package com.ishvatov.model.entity;

import com.ishvatov.model.enums.TruckStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Describes a basic truck entity in the data base.
 *
 * @author Sergey Khvatov
 */
@Entity
@Table(name = "truck")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TruckEntity extends AbstractEntityWithStringId {

    /**
     * Name of the capacity column in the DB.
     */
    public static final String CAPACITY_FIELD = "capacity";

    /**
     * Name of the condition column in the DB.
     */
    public static final String STATUS_FIELD = "status";

    /**
     * Name of the driver shift size column in the DB.
     */
    public static final String SHIFT_FIELD = "shift_size";

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
     * Truck's capacity in tons.
     */
    @Column(name = CAPACITY_FIELD)
    private Double capacity;

    /**
     * DriverEntity shift size in hours.
     */
    @Column(name = SHIFT_FIELD)
    private Integer shiftSize;

    /**
     * Truck status.
     */
    @Column(name = STATUS_FIELD)
    @Enumerated(EnumType.STRING)
    private TruckStatusType status;

    /**
     * Set of drivers, who are assigned to this truck.
     */
    @OneToMany(mappedBy = "truck", fetch = FetchType.LAZY)
    private Set<DriverEntity> assignedDrivers = new HashSet<>();

    /**
     * Order, assigned to this truck.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = ORDER_ID_FIELD)
    private OrderEntity order;

    /**
     * Adds driver to the set of the assigned to this truck ones.
     *
     * @param driverEntity {@link DriverEntity} entity.
     */
    public void addDriver(DriverEntity driverEntity) {
        Optional.ofNullable(driverEntity).ifPresent(e -> {
            assignedDrivers.add(e);
            e.setTruck(this);
        });
    }

    /**
     * Removes driver from the set of the the assigned to this truck ones.
     *
     * @param driverEntity {@link DriverEntity} entity.
     */
    public void removeDriver(DriverEntity driverEntity) {
        Optional.ofNullable(driverEntity).ifPresent(e -> {
            assignedDrivers.remove(e);
            e.setTruck(null);
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TruckEntity)) return false;
        if (!super.equals(o)) return false;
        TruckEntity entity = (TruckEntity) o;
        return getLatitude().equals(entity.getLatitude()) &&
            getLongitude().equals(entity.getLongitude()) &&
            getCapacity().equals(entity.getCapacity()) &&
            getShiftSize().equals(entity.getShiftSize()) &&
            getStatus() == entity.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getLatitude(), getLongitude(),
            getCapacity(), getShiftSize(), getStatus());
    }
}
