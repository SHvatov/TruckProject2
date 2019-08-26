package com.ishvatov.model.repository;

import com.ishvatov.model.entity.CargoEntity;
import org.springframework.stereotype.Repository;

/**
 * Data access interface, which is used to retrieve and process
 * {@link CargoEntity} objects from the database.
 *
 * @author Sergey Khvatov
 */
@Repository
public interface CargoRepository extends BaseCrudRepository<Integer, CargoEntity> {
    // empty
}
