package com.ishvatov.model.dto;

import com.ishvatov.model.enums.DriverStatusType;
import com.ishvatov.validation.driver.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * Basic user DTO implementation.
 *
 * @author Sergey Khvatov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverDto implements AbstractDto {

    /**
     * Unique identificator of the object.
     */
    @NotBlank(
        message = "{common.not_blank}"
    )
    @Size(
        min = 5, max = 20,
        message = "{driver.id.incorrect}",
        groups = NotExistingDriverChecks.class
    )
    @NotExistingDriverId(
        message = "{driver.id.not_exists}",
        groups = NotExistingDriverId.class
    )
    @ExistingDriverId(
        message = "{driver.id.exists}",
        groups = ExistingDriverId.class
    )
    @NoDriverOrderAssigned(
        message = "{common.order_assigned}",
        groups = NoDriverOrderAssignedChecks.class
    )
    private String id;

    /**
     * Password of the driver. Used only
     * while adding new user.
     */
    @NotBlank(
        message = "{common.not_blank}",
        groups = {ExistingDriverChecks.class, NotExistingDriverChecks.class}
    )
    @Size(
        min = 5, max = 20,
        message = "{driver.password.incorrect}",
        groups = {ExistingDriverChecks.class, NotExistingDriverChecks.class}
    )
    private String password;

    /**
     * Name of the driver.
     */
    @NotBlank(
        message = "{common.not_blank}",
        groups = {ExistingDriverChecks.class, NotExistingDriverChecks.class}
    )
    private String name;

    /**
     * Surname of the driver.
     */
    @NotBlank(
        message = "{common.not_blank}",
        groups = {ExistingDriverChecks.class, NotExistingDriverChecks.class}
    )
    private String surname;

    /**
     * Number of hours driver has worked
     * in this month.
     */
    @NotNull(
        message = "{common.not_blank}",
        groups = {ExistingDriverChecks.class, NotExistingDriverChecks.class}
    )
    @PositiveOrZero(
        message = "{common.not_blank}",
        groups = {ExistingDriverChecks.class, NotExistingDriverChecks.class}
    )
    private Integer workedHours;

    /**
     * Date - last time order was updated.
     */
    @NotNull(
        message = "{common.not_blank}",
        groups = {ExistingDriverChecks.class, NotExistingDriverChecks.class}
    )
    private Timestamp lastUpdated;

    /**
     * Latitude of the object on the map.
     */
    @NotNull(
        message = "{common.not_blank}",
        groups = {ExistingDriverChecks.class, NotExistingDriverChecks.class}
    )
    private Double latitude;

    /**
     * Longitude of the object on the map.
     */
    @NotNull(
        message = "{common.not_blank}",
        groups = {ExistingDriverChecks.class, NotExistingDriverChecks.class}
    )
    private Double longitude;

    /**
     * Status of the driver.
     */
    @NotNull(
        message = "{common.not_blank}",
        groups = {ExistingDriverChecks.class, NotExistingDriverChecks.class}
    )
    private DriverStatusType status;

    /**
     * UID of the truck this driver is assigned to.
     */
    private String truckId;

    /**
     * UID of the order this driver is assigned to.
     */
    private Integer orderId;
}
