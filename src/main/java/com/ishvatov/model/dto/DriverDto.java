package com.ishvatov.model.dto;

import com.ishvatov.model.enums.DriverStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    protected String id;

    /**
     * Password of the driver. Used only
     * while adding new user.
     */
    private String password;

    /**
     * Name of the driver.
     */
    private String name;

    /**
     * Surname of the driver.
     */
    private String surname;

    /**
     * Number of hours driver has worked
     * in this month.
     */
    private Integer workedHours;

    /**
     * Date - last time order was updated.
     */
    private Timestamp lastUpdated;

    /**
     * Latitude of the object on the map.
     */
    private Double latitude;

    /**
     * Longitude of the object on the map.
     */
    private Double longitude;

    /**
     * Status of the driver.
     */
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
