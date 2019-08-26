package com.ishvatov.model.repository;

import com.ishvatov.model.entity.WaypointEntity;
import org.springframework.stereotype.Repository;

/**
 * Data access interface, which is used to retrieve and process
 * {@link WaypointEntity} objects from the database.
 *
 * @author Sergey Khvatov
 */
@Repository
public interface WaypointRepository extends BaseCrudRepository<Integer, WaypointEntity> {
    // empty
}
