package com.ishvatov.model.repository;

import com.ishvatov.model.entity.DriverEntity;
import org.springframework.stereotype.Repository;

/**
 * Data access interface, which is used to retrieve and process
 * {@link DriverEntity} objects from the database.
 *
 * @author Sergey Khvatov
 */
@Repository
public interface DriverRepository extends BaseCrudRepository<String, DriverEntity> {
    // empty
}