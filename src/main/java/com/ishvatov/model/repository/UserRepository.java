package com.ishvatov.model.repository;

import com.ishvatov.model.entity.UserEntity;
import org.springframework.stereotype.Repository;

/**
 * Data access interface, which is used to retrieve and process
 * {@link UserEntity} objects from the database.
 *
 * @author Sergey Khvatov
 */
@Repository
public interface UserRepository extends BaseCrudRepository<String, UserEntity> {
    // empty
}
