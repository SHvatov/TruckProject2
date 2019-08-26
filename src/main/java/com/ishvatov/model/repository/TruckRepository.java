package com.ishvatov.model.repository;

import com.ishvatov.model.entity.TruckEntity;
import org.springframework.stereotype.Repository;

/**
 * Data access interface, which is used to retrieve and process
 * {@link TruckEntity} objects from the database.
 *
 * @author Sergey Khvatov
 */
@Repository
public interface TruckRepository extends BaseCrudRepository<String, TruckEntity> {
    // empty
}
