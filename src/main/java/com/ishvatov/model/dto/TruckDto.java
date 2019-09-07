package com.ishvatov.model.dto;

import com.ishvatov.model.enums.TruckStatusType;
import com.ishvatov.validation.common.DoubleInRange;
import com.ishvatov.validation.truck.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    @NotBlank(message = "{common.not_blank}")
    @Size(
        min = 7, max = 7,
        message = "{truck.id.incorrect}",
        groups = NotExistingTruckChecks.class
    )
    @Pattern(
        regexp = "[a-zA-Z]{2}[0-9]{5}",
        message = "{truck.id.incorrect}",
        groups = NotExistingTruckChecks.class
    )
    @NotExistingTruckId(
        message = "{truck.id.exists}",
        groups = NotExistingTruckChecks.class
    )
    @ExistingTruckId(
        message = "{truck.id.not_exists}",
        groups = ExistingTruckChecks.class
    )
    @NoTruckOrderAssigned(
        message = "{common.order_assigned}",
        groups = NoTruckOrderAssignedChecks.class
    )
    private String id;

    /**
     * Truck's capacity in tons.
     */
    @NotNull(
        message = "{common.not_blank}",
        groups = {NotExistingTruckChecks.class, ExistingTruckChecks.class}
    )
    @DoubleInRange(
        min = 500.0, max = 10000.0,
        message = "{truck.capacity.incorrect}",
        groups = {NotExistingTruckChecks.class, ExistingTruckChecks.class}
    )
    private Double capacity;

    /**
     * DriverEntity shift size in hours.
     */
    @NotNull(
        message = "{common.not_blank}",
        groups = {NotExistingTruckChecks.class, ExistingTruckChecks.class}
    )
    @Range(
        min = 6, max = 12,
        message = "{truck.shift_size.incorrect}",
        groups = {NotExistingTruckChecks.class, ExistingTruckChecks.class}
    )
    private Integer shiftSize;

    /**
     * Truck status.
     */
    @NotNull(
        message = "{common.not_blank}",
        groups = {NotExistingTruckChecks.class, ExistingTruckChecks.class}
    )
    private TruckStatusType status;

    /**
     * Latitude of the object on the map.
     */
    @NotNull(
        message = "{common.not_blank}",
        groups = {NotExistingTruckChecks.class, ExistingTruckChecks.class}
    )
    private Double latitude;

    /**
     * Longitude of the object on the map.
     */
    @NotNull(
        message = "{common.not_blank}",
        groups = {NotExistingTruckChecks.class, ExistingTruckChecks.class}
    )
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
