package com.ishvatov.model.dto;

import com.ishvatov.model.enums.CargoActionType;
import com.ishvatov.model.enums.WaypointStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Basic user DTO implementation.
 *
 * @author Sergey Khvatov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WaypointDto extends AbstractDto<Integer> {

    /**
     * Defines whether cargo is being loaded or
     * unloaded in this city.
     */
    private CargoActionType action;

    /**
     * Defines the status of the waypoint (completed or not).
     */
    private WaypointStatusType status;

    /**
     * Latitude of the object on the map.
     */
    private Double latitude;

    /**
     * Longitude of the object on the map.
     */
    private Double longitude;

    /**
     * Order, this waypoint is assigned to.
     */
    private Integer orderId;

    /**
     * Cargo, that is assigned to this waypoint.
     */
    private Integer cargoId;
}
