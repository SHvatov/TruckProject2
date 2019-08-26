package com.ishvatov.model.repository;

import com.ishvatov.model.entity.CityEntity;
import org.springframework.stereotype.Repository;

/**
 * Data access interface, which is used to retrieve and process
 * {@link CityEntity} objects from the database.
 *
 * @author Sergey Khvatov
 */
@Repository
public interface CityRepository extends BaseCrudRepository<String, CityEntity> {
    // empty
}
