package com.ishvatov.model.dto;

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
public class CityDto extends AbstractDto<String> {

    /**
     * Latitude of the object on the map.
     */
    private Double latitude;

    /**
     * Longitude of the object on the map.
     */
    private Double longitude;
}
