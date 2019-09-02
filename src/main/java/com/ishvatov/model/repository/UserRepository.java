package com.ishvatov.model.repository;

import com.ishvatov.model.dto.UserDto;
import com.ishvatov.model.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
