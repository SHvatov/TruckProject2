package com.ishvatov.model.dto;

import com.ishvatov.model.enums.CargoStatusType;
import com.ishvatov.validation.cargo.ExistingCargoChecks;
import com.ishvatov.validation.cargo.ExistingCargoId;
import com.ishvatov.validation.cargo.NoCargoOrderAssigned;
import com.ishvatov.validation.cargo.NoCargoOrderAssignedChecks;
import com.ishvatov.validation.common.DoubleInRange;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotNull(
        message = "{cargo.id.not_exists}",
        groups = ExistingCargoChecks.class
    )
    @ExistingCargoId(
        message = "{cargo.id.not_exists}",
        groups = ExistingCargoChecks.class
    )
    @NoCargoOrderAssigned(
        message = "{common.order_assigned}",
        groups = NoCargoOrderAssignedChecks.class
    )
    private Integer id;

    /**
     * Name of the cargo.
     */
    @NotBlank(
        message = "{common.not_blank}"
    )
    private String name;

    /**
     * Mass of the cargo.
     */
    @NotNull(
        message = "{common.not_blank}"
    )
    @DoubleInRange(
        min = 0.5, max = 1000,
        message = "{cargo.mass.incorrect}"
    )
    private Double mass;

    /**
     * Status of the cargo.
     */
    @NotBlank(
        message = "{common.not_blank}",
        groups = ExistingCargoChecks.class
    )
    private CargoStatusType status;

    /**
     * Latitude of the object on the map.
     */
    @NotNull(
        message = "{common.not_blank}"
    )
    private Double latitude;

    /**
     * Longitude of the object on the map.
     */
    @NotNull(
        message = "{common.not_blank}"
    )
    private Double longitude;

    /**
     * Set of ids of the waypoints,
     * this cargo is assigned to.
     */
    private Set<Integer> assignedWaypoints;
}
