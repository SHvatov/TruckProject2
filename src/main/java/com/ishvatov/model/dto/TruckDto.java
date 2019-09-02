package com.ishvatov.model.dto;

import com.ishvatov.model.enums.TruckStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Basic user DTO implementation.
 *
 * @author Sergey Khvatov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TruckDto implements AbstractDto {

    /**
     * Unique identificator of the object.
     */
    protected String id;

    /**
     * Truck's capacity in tons.
     */
    private Double capacity;

    /**
     * DriverEntity shift size in hours.
     */
    private Integer shiftSize;

    /**
     * Truck status.
     */
    private TruckStatusType status;

    /**
     * Latitude of the object on the map.
     */
    private Double latitude;

    /**
     * Longitude of the object on the map.
     */
    private Double longitude;

    /**
     * Drivers, who are using this truck.
     */
    private Set<String> assignedDrivers = new HashSet<>();

    /**
     * Orders, that is assigned to this truck.
     */
    private Integer orderId;
}
