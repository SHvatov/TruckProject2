package com.ishvatov.model.dto;

import com.ishvatov.model.enums.CargoStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Basic user DTO implementation.
 *
 * @author Sergey Khvatov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoDto implements AbstractDto {

    /**
     * Unique identificator of the object.
     */
    protected Integer id;

    /**
     * Name of the cargo.
     */
    private String name;

    /**
     * Mass of the cargo.
     */
    private Double mass;

    /**
     * Status of the cargo.
     */
    private CargoStatusType status;

    /**
     * Latitude of the object on the map.
     */
    private Double latitude;

    /**
     * Longitude of the object on the map.
     */
    private Double longitude;

    /**
     * Set of ids of the waypoints,
     * this cargo is assigned to.
     */
    private Set<Integer> assignedWaypoints;
}
