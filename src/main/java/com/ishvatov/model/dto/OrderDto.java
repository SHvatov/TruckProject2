package com.ishvatov.model.dto;

import com.ishvatov.model.enums.OrderStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Basic user DTO implementation.
 *
 * @author Sergey Khvatov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto implements AbstractDto {

    /**
     * Unique identificator of the object.
     */
    private Integer id;

    /**
     * Status of the order.
     */
    private OrderStatusType status;

    /**
     * Date - start of the order.
     */
    private Timestamp lastUpdated;

    /**
     * Truck, that is assigned to this order.
     */
    private String truckId;

    /**
     * Set of drivers, who are assigned to this order.
     */
    private Set<String> assignedDrivers = new HashSet<>();

    /**
     * Set of waypoints, that are located in the city.
     */
    private List<Integer> assignedWaypoints = new ArrayList<>();
}
