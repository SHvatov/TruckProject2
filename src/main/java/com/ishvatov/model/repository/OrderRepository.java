package com.ishvatov.model.repository;

import com.ishvatov.model.entity.OrderEntity;
import org.springframework.stereotype.Repository;

/**
 * Data access interface, which is used to retrieve and process
 * {@link OrderEntity} objects from the database.
 *
 * @author Sergey Khvatov
 */
@Repository
public interface OrderRepository extends BaseCrudRepository<Integer, OrderEntity> {
    // empty
}
