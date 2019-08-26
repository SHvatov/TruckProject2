package com.ishvatov.model.dto;

import com.ishvatov.model.enums.CargoStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = true)
public class CargoDto extends AbstractDto<Integer> {

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
     * Set of ids of the waypoints,
     * this cargo is assigned to.
     */
    private Set<Integer> assignedWaypoints;
}
