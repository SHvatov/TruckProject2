package com.ishvatov.model.entity;

import com.ishvatov.model.enums.CargoStatusType;
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
 * Defines a basic entity in the database, which stores the
 * information about cargo unit.
 *
 * @author Sergey Khvatov
 */
@Entity
@Table(name = "cargo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CargoEntity extends AbstractEntity<Integer> {

    /**
     * String representation of the 'mass'
     * column name in the table.
     */
    public static final String MASS_FIELD = "mass";

    /**
     * String representation of the 'cargo_status'
     * column name in the table.
     */
    public static final String STATE_FIELD = "status";

    /**
     * String representation of the 'name'
     * column name in the table.
     */
    public static final String NAME_FIELD = "name";

    /**
     * Name of the cargo.
     */
    @Column(name = NAME_FIELD)
    private String name;

    /**
     * Mass of the cargo.
     */
    @Column(name = MASS_FIELD)
    private Double mass;

    /**
     * Status of the cargo.
     */
    @Column(name = STATE_FIELD)
    @Enumerated(EnumType.STRING)
    private CargoStatusType status;

    /**
     * Set of waypoints, that are located in the city.
     */
    @OneToMany(mappedBy = "cargo", fetch = FetchType.LAZY)
    private Set<WaypointEntity> assignedWaypoints = new HashSet<>();

    /**
     * Adds a waypoint to the set of the waypoints assigned to this cargo.
     *
     * @param waypointEntity {@link WaypointEntity} entity.
     */
    public void addWaypoint(WaypointEntity waypointEntity) {
        Optional.ofNullable(waypointEntity).ifPresent(e -> {
            assignedWaypoints.add(e);
            e.setCargo(this);
        });
    }

    /**
     * Removes waypoint from the set of the waypoints assigned to this cargo.
     *
     * @param waypointEntity {@link WaypointEntity} entity.
     */
    public void removeWaypoint(WaypointEntity waypointEntity) {
        Optional.ofNullable(waypointEntity).ifPresent(e -> {
            assignedWaypoints.remove(e);
            e.setCargo(null);
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CargoEntity)) return false;
        if (!super.equals(o)) return false;
        CargoEntity that = (CargoEntity) o;
        return getName().equals(that.getName())
            && getMass().equals(that.getMass())
            && getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName(), getMass(), getStatus());
    }
}
